package com.utcn.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import com.utcn.dao.EmployeeRepository;
import com.utcn.model.Employee;


@Component
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository; // DAO

    public List<Employee> getAllEmployees() {
        return Lists.newArrayList(employeeRepository.findAll());
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    public void delete(Long id) {
        employeeRepository.delete(id);
    }

    public Employee getByName(String name) {
        return employeeRepository.findByName(name);
    }
}
