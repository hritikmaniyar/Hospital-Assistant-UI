package com.hospital.assist.service;

import com.hospital.assist.model.Appointment;
import com.hospital.assist.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository repo;
    public AppointmentService(AppointmentRepository repo){ this.repo = repo; }

    public Appointment book(Appointment a){ a.setStatus("Booked"); return repo.save(a); }
    public Appointment reschedule(Long id, String newDatetime){
        return repo.findById(id).map(a -> { a.setDatetime(newDatetime); a.setStatus("Rescheduled"); return repo.save(a); })
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
    public Appointment cancel(Long id){
        return repo.findById(id).map(a -> { a.setStatus("Cancelled"); return repo.save(a); })
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }
    public List<Appointment> findAll(){ return repo.findAll(); }
    public List<Appointment> findByPatient(Long patientId){ return repo.findByPatientId(patientId); }

    // inside AppointmentService
    public Appointment assignDoctor(Long appointmentId, Long doctorId, String doctorName) {
        return repo.findById(appointmentId).map(a -> {
            a.setDoctorId(doctorId);
            a.setDoctorName(doctorName);
            return repo.save(a);
        }).orElseThrow(() -> new RuntimeException("Appointment not found"));
    }


}
