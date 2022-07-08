package com.example.cosmos.config;

import com.azure.cosmos.CosmosClientBuilder;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class CosmosTestConfg extends AbstractCosmosConfiguration {

    CosmosDatabaseContainer databaseContainer = CosmosDatabaseContainer.getInstance();

    @Primary
    @Bean
    public CosmosClientBuilder getCosmosClientBuilder() {
        return new CosmosClientBuilder()
                .gatewayMode()
                .endpointDiscoveryEnabled(false)
                .endpoint(databaseContainer.getEmulatorEndpoint())
                .key(databaseContainer.getEmulatorKey());
    }

    @Override
    protected String getDatabaseName() {
        return this.databaseContainer.getDatabaseName();
    }
}
