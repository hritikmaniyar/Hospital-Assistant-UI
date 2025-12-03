package com.hospital.assist.controller;

import com.hospital.assist.model.Patient;
import com.hospital.assist.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Patient> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> get(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patient> create(@Valid @RequestBody Patient patient) {
        return ResponseEntity.ok(service.save(patient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Long id, @Valid @RequestBody Patient patient) {
        try {
            return ResponseEntity.ok(service.update(id, patient));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
