package com.hospital.assist.service;

import com.hospital.assist.model.Doctor;
import com.hospital.assist.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository repo;
    public DoctorService(DoctorRepository repo){ this.repo = repo; }

    public Doctor save(Doctor p){ return repo.save(p); }
    public Doctor update(Long id, Doctor p){
        return repo.findById(id).map(existing -> {
            existing.setName(p.getName());
            existing.setYears(p.getYears());
            existing.setMonths(p.getMonths());
            existing.setEmail(p.getEmail());
            existing.setPhone(p.getPhone());
            existing.setSpecialization(p.getSpecialization());
            return repo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Doctor not found"));
    }
    public void delete(Long id){ repo.deleteById(id); }
    public List<Doctor> findAll(){ return repo.findAll(); }
    public Doctor findById(Long id){ return repo.findById(id).orElse(null); }
}
