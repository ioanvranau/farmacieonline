package com.utcn.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import com.utcn.dao.MedicamentRepository;
import com.utcn.model.Medicament;


@Component
public class MedicamentService {

    @Autowired
    private MedicamentRepository medicamentRepository; // DAO

    public List<Medicament> getAllMedicaments() {
        return Lists.newArrayList(medicamentRepository.findAll());
    }

    public Medicament findById(Long id) {
        return medicamentRepository.findOne(id);
    }

    public Medicament save(Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    public void delete(Medicament medicament) {
        medicamentRepository.delete(medicament);
    }

    public void delete(Long id) {
        final Medicament one = medicamentRepository.findOne(id);
        if(one != null) {
            one.setQuantity(0);
        }
        medicamentRepository.save(one);
    }
}
