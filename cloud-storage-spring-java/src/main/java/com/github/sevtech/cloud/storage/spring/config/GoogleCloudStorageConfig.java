package com.github.sevtech.cloud.storage.spring.config;

import com.github.sevtech.cloud.storage.spring.property.GoogleCloudStorageProperties;
import com.github.sevtech.cloud.storage.spring.service.StorageService;
import com.github.sevtech.cloud.storage.spring.service.impl.GoogleCloudStorageService;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
@ConditionalOnCloudStorageProperty(value = "gcp.storage.enabled")
public class GoogleCloudStorageConfig {
    @Bean
    public GoogleCloudStorageProperties googleCloudStorageProperties(Environment env) {
        return new GoogleCloudStorageProperties(env);
    }

    @Bean
    public Storage storageClient(GoogleCloudStorageProperties googleCloudStorageProperties) throws IOException {
        log.info("Registering Google Storage client");
        return StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(googleCloudStorageProperties.getKeyFileLocation())))
                .build()
                .getService();
    }

    @Bean
    public StorageService googleCloudStorageService(Storage storageClient) {
        return new GoogleCloudStorageService(storageClient);
    }

}
