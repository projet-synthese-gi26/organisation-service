package com.yowyob.organisation_service.infrastructure.adapters.inbound.web;

import com.yowyob.organisation_service.application.dtos.EmployeeDTO;
import com.yowyob.organisation_service.application.services.hr.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Tag(name = "Gestion des Employés", description = "Gestion RH")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un employé", description = "Crée un employé et le lie à une organisation.")
    public Mono<ResponseEntity<EmployeeDTO.Response>> create(@Valid @RequestBody EmployeeDTO.Request request) {
        return employeeService.createEmployee(request)
                .map(res -> ResponseEntity.status(HttpStatus.CREATED).body(res));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EmployeeDTO.Response>> getById(@PathVariable UUID id) {
        return employeeService.getEmployeeById(id).map(ResponseEntity::ok);
    }

    @GetMapping("/organization/{orgId}")
    @Operation(summary = "Lister par organisation")
    public Flux<EmployeeDTO.Response> getByOrg(@PathVariable UUID orgId) {
        return employeeService.getEmployeesByOrganization(orgId);
    }
    
    // Ajoutez PUT et DELETE selon le modèle déjà vu
    @PutMapping("/{id}")
    public Mono<ResponseEntity<EmployeeDTO.Response>> update(@PathVariable UUID id, @Valid @RequestBody EmployeeDTO.Request request) {
        return employeeService.updateEmployee(id, request).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return employeeService.deleteEmployee(id).map(res -> ResponseEntity.noContent().build());
    }
    
}