package com.example.cosmos.config;

import com.example.cosmos.EmployeeServiceApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {EmployeeServiceApplication.class, CosmosTestConfg.class})
public abstract class DatabaseAndContextConfiguration {
}
