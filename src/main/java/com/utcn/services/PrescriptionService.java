package com.utcn.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import com.utcn.dao.PrescriptionRepository;
import com.utcn.model.Employee;
import com.utcn.model.Prescription;


@Component
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository; // DAO

    public List<Prescription> getAllPrescriptions() {
        return Lists.newArrayList(prescriptionRepository.findAll());
    }

    public Prescription save(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public void delete(Prescription prescription) {
        prescriptionRepository.delete(prescription);
    }

    public void delete(Long id) {
        prescriptionRepository.delete(id);
    }

    public void clearPrescriptions(String id) {
        final Iterable<Prescription> all = prescriptionRepository.findAll();
        for (Prescription prescription : all) {
            final Employee employee = prescription.getEmployee();
            if(employee != null && employee.getId() != null && employee.getId().toString().equalsIgnoreCase(id)) {
                prescription.setEmployee(null);
            }
        }
    }
}
