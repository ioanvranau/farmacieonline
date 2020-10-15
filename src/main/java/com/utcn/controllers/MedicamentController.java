package com.utcn.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.utcn.model.Medicament;
import com.utcn.services.MedicamentService;

@RestController
public class MedicamentController {


    @Autowired
    private MedicamentService medicamentService;

    @RequestMapping("/medicament")
    public
    @ResponseBody
    List<Medicament> getAllMedicaments() {
        return medicamentService.getAllMedicaments();
    }

    @RequestMapping(value = "/medicament", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Medicament> addMedicament(
            @RequestBody Medicament medicament) {
        if (medicament != null) {
            Medicament addedMedicament;
            addedMedicament = medicamentService.save(medicament);
            return new ResponseEntity<Medicament>(addedMedicament, HttpStatus.OK);
        } else {
            return new ResponseEntity<Medicament>(new Medicament(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/medicament", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity<Medicament> deleteMedicament(@RequestBody String id) {
        medicamentService.delete(Long.parseLong(id));
        return new ResponseEntity<Medicament>(new Medicament(), HttpStatus.OK);
    }
}
