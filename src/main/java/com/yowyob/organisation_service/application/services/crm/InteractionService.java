package com.yowyob.organisation_service.application.services.crm;

import com.yowyob.organisation_service.application.dtos.InteractionDTO;
import com.yowyob.organisation_service.application.mappers.InteractionMapper;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.entities.core.Interaction;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.InteractionRepository;
import com.yowyob.organisation_service.infrastructure.adapters.outbound.persistence.repositories.ProspectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class InteractionService {

    private final InteractionRepository interactionRepository;
    private final ProspectRepository prospectRepository;
    private final InteractionMapper mapper;

    // Le producer Kafka configuré
    private final ReactiveKafkaProducerTemplate<String, Object> kafkaTemplate;

    // Injection du nom du topic depuis application.yml
    @Value("${application.kafka.topics.crm-interactions}")
    private String interactionTopic;

    @Transactional
    public Mono<InteractionDTO.Response> addInteraction(InteractionDTO.Request request) {
        return prospectRepository.existsById(request.prospectId())
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new ResponseStatusException(NOT_FOUND, "Prospect inconnu")))
                .flatMap(exists -> {
                    Interaction entity = mapper.toEntity(request);
                    if (entity.getInteractionDate() == null) {
                        entity.setInteractionDate(LocalDateTime.now());
                    }
                    entity.setCreatedAt(LocalDateTime.now());
                    return interactionRepository.save(entity);
                })
                .flatMap(saved -> {
                    // --- ENVOI KAFKA ---
                    // On utilise l'ID du prospect comme Clé (pour garantir l'ordre dans les
                    // partitions Kafka)
                    String key = saved.getProspectId().toString();

                    // Payload de l'événement
                    Map<String, Object> event = Map.of(
                            "eventType", "NEW_INTERACTION",
                            "prospectId", saved.getProspectId(),
                            "interactionId", saved.getId(),
                            "noteExtract",
                            saved.getNotes() != null
                                    ? saved.getNotes().substring(0, Math.min(saved.getNotes().length(), 20))
                                    : "",
                            "timestamp", LocalDateTime.now().toString());

                    log.info("Tentative d'envoi Kafka vers le topic : {}", interactionTopic);

                    return kafkaTemplate.send(interactionTopic, key, event)
                            .doOnSuccess(
                                    result -> log.info("✅ Succès Kafka : Offset {}", result.recordMetadata().offset()))
                            .doOnError(e -> log.error("❌ Erreur Kafka", e))
                            // On continue même si Kafka échoue pour ne pas bloquer l'API (ou on peut
                            // laisser l'erreur remonter)
                            .thenReturn(mapper.toResponse(saved));
                });
    }

    public Flux<InteractionDTO.Response> getHistory(UUID prospectId) {
        return interactionRepository.findByProspectId(prospectId)
                .map(mapper::toResponse);
    }
}