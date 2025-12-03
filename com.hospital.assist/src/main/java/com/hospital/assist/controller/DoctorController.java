package com.hospital.assist.controller;

import com.hospital.assist.model.Doctor;
import com.hospital.assist.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Doctor> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> get(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Doctor> create(@Valid @RequestBody Doctor doctor) {
        return ResponseEntity.ok(service.save(doctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> update(@PathVariable Long id, @Valid @RequestBody Doctor doctor) {
        try {
            return ResponseEntity.ok(service.update(id, doctor));
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
