package com.example.cosmos.config;

import org.springframework.stereotype.Component;
import org.testcontainers.containers.CosmosDBEmulatorContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class CosmosDatabaseContainer implements AutoCloseable {
    public static final String IMAGE_NAME = "mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator";
    private CosmosDBEmulatorContainer cosmosDBEmulatorContainer;
    private static AtomicBoolean initialized = new AtomicBoolean();
    private static CosmosDatabaseContainer thisContainer;

    private CosmosDatabaseContainer() {
        if (initialized.compareAndSet(false, true)) {
            this.cosmosDBEmulatorContainer =
                    new CosmosDBEmulatorContainer(DockerImageName.parse(IMAGE_NAME));

            cosmosDBEmulatorContainer.setEnv(List.of("AZURE_COSMOS_EMULATOR_PARTITION_COUNT=5"));
            cosmosDBEmulatorContainer.start();

            configureKeyStore();
        }
    }

    public static CosmosDatabaseContainer getInstance() {
        if (thisContainer == null) {
            thisContainer = new CosmosDatabaseContainer();
        }
        return thisContainer;
    }

    @Override
    public void close() throws Exception {
        cosmosDBEmulatorContainer.stop();
    }

    private void configureKeyStore() {
        try {
            Path keyStoreFile = Files.createTempFile("azure-cosmos-emulator", ".keystore");

            KeyStore keyStore = cosmosDBEmulatorContainer.buildNewKeyStore();
            keyStore.store(new FileOutputStream(keyStoreFile.toFile()), cosmosDBEmulatorContainer.getEmulatorKey().toCharArray());

            System.setProperty("javax.net.ssl.trustStore", keyStoreFile.toString());
            System.setProperty("javax.net.ssl.trustStorePassword", cosmosDBEmulatorContainer.getEmulatorKey());
            System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            cosmosDBEmulatorContainer.stop();
            throw new RuntimeException(e);
        }
    }

    public String getEmulatorKey() {
        return this.cosmosDBEmulatorContainer.getEmulatorKey();
    }

    public String getEmulatorEndpoint() {
        return this.cosmosDBEmulatorContainer.getEmulatorEndpoint();
    }

    public String getDatabaseName() {
        return "testDB";
    }
}
