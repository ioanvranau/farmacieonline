package com.utcn.dao;

import org.springframework.data.repository.CrudRepository;
import com.utcn.model.Medicament;

// No need to implement this. Spring does this automatically
public interface MedicamentRepository extends CrudRepository<Medicament, Long> {

}
