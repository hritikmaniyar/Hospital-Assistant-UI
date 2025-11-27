package com.hospital.assist.controller;

import com.hospital.assist.model.Appointment;
import com.hospital.assist.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {
    private final AppointmentService service;
    public AppointmentController(AppointmentService service){ this.service = service; }

    @GetMapping
    public List<Appointment> all(){ return service.findAll(); }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> byPatient(@PathVariable Long patientId){ return service.findByPatient(patientId); }

    @PostMapping
    public ResponseEntity<Appointment> book(@Valid @RequestBody Appointment a){
        return ResponseEntity.ok(service.book(a));
    }

    @PutMapping("/{id}/reschedule")
    public ResponseEntity<Appointment> reschedule(@PathVariable Long id, @RequestBody String newDatetime){
        try { return ResponseEntity.ok(service.reschedule(id, newDatetime)); }
        catch(RuntimeException ex){ return ResponseEntity.notFound().build(); }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Appointment> cancel(@PathVariable Long id){
        try { return ResponseEntity.ok(service.cancel(id)); }
        catch(RuntimeException ex){ return ResponseEntity.notFound().build(); }
    }

    // inside AppointmentController
    @PutMapping("/{id}/assign")
    public ResponseEntity<Appointment> assignDoctor(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Long doctorId = body.get("doctorId") == null ? null : Long.valueOf(String.valueOf(body.get("doctorId")));
        String doctorName = body.get("doctorName") == null ? null : String.valueOf(body.get("doctorName"));
        try {
            Appointment updated = service.assignDoctor(id, doctorId, doctorName);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
