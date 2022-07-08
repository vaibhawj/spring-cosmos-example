package com.example.cosmos;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class EmployeeService {
    private EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public Employee getById(String id) {
        return repository.findById(id).get();
    }

    public Iterable<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(employees::add);
        return employees;
    }
}
