package com.example.cosmos.config;

import com.example.cosmos.EmployeeServiceApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {EmployeeServiceApplication.class, CosmosTestConfg.class})
@AutoConfigureMockMvc
public abstract class DatabaseAndContextConfiguration {
}
