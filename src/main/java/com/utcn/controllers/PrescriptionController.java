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
import com.utcn.model.Prescription;
import com.utcn.services.PrescriptionService;

@RestController
public class PrescriptionController {


    @Autowired
    private PrescriptionService prescriptionService;

    @RequestMapping("/prescription")
    public
    @ResponseBody
    List<Prescription> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    @RequestMapping(value = "/prescription", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Prescription> addPrescription(
            @RequestBody Prescription prescription) {
        if (prescription != null) {
            Prescription addedPrescription;
            addedPrescription = prescriptionService.save(prescription);
            return new ResponseEntity<Prescription>(addedPrescription, HttpStatus.OK);
        } else {
            return new ResponseEntity<Prescription>(new Prescription(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/prescription", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity<Prescription> deletePrescription(@RequestBody String id) {
        prescriptionService.delete(Long.parseLong(id));
        return new ResponseEntity<Prescription>(new Prescription(), HttpStatus.OK);
    }
}
