package com.utcn.dao;

import org.springframework.data.repository.CrudRepository;
import com.utcn.model.Prescription;

// No need to implement this. Spring does this automatically
public interface PrescriptionRepository extends CrudRepository<Prescription, Long> {

}
