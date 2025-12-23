package com.yowyob.organisation_service.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class RedisConfig {

        @Bean
        @Primary
        public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(
                        ReactiveRedisConnectionFactory factory) {

                // 1. Configuration Jackson identique au Template (Support Dates & Records)
                ObjectMapper mapper = new ObjectMapper()
                                .registerModule(new ParameterNamesModule())
                                .registerModule(new JavaTimeModule()); // Indispensable pour LocalDateTime

                // 2. Sérialiseur JSON générique
                Jackson2JsonRedisSerializer<Object> jsonSerializer = new Jackson2JsonRedisSerializer<>(mapper,
                                Object.class);

                // 3. Contexte de sérialisation (Clés en String, Valeurs en JSON)
                RedisSerializationContext<String, Object> context = RedisSerializationContext
                                .<String, Object>newSerializationContext(new StringRedisSerializer())
                                .value(jsonSerializer)
                                .hashValue(jsonSerializer)
                                .build();

                return new ReactiveRedisTemplate<>(factory, context);
        }
}