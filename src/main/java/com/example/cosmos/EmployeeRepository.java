package com.example.cosmos;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CosmosRepository<Employee, String> {
}
