package com.example.cosmos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return ResponseEntity.status(201).body(employeeService.create(employee));
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getById(@PathVariable String id) {
        return ResponseEntity.status(200).body(employeeService.getById(id));
    }

    @GetMapping("/employees")
    public ResponseEntity<Iterable<Employee>> findAll() {
        return ResponseEntity.status(200).body(employeeService.findAll());
    }
}
