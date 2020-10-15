package com.utcn.controllers;

import java.net.UnknownHostException;
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
import com.utcn.model.Employee;
import com.utcn.services.EmployeeService;
import com.utcn.services.PrescriptionService;

@RestController
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PrescriptionService prescriptionService;

    @RequestMapping("/employee")
    public
    @ResponseBody
    List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Employee> addEmployee(
            @RequestBody Employee employee) throws UnknownHostException {
        if (employee != null) {
            Employee addedEmployee;
            addedEmployee = employeeService.save(employee);
            return new ResponseEntity<Employee>(addedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<Employee>(new Employee(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/employee", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity<Employee> deleteEmployee(@RequestBody String id) {
        prescriptionService.clearPrescriptions(id);
        employeeService.delete(Long.parseLong(id));
        return new ResponseEntity<Employee>(new Employee(), HttpStatus.OK);
    }
}
