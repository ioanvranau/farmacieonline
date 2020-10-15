package com.utcn.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.utcn.model.Employee;

// No need to implement this. Spring does this automatically
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Employee findByName(String name);

    List<Employee> findBySurname(String lastName); // Doar se scrie numele campului. Ex findByCnp
}
