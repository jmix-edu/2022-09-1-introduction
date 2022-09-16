package com.company.tm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

@Configuration
@Profile("vault")
public class VaultDbConfig {

    @Autowired
    private VaultTemplate vaultTemplate;

    @Value("${vault.path}")
    private String vaultPath;

    @Bean("dataSourceProperties")
    DataSourceProperties dataSourcePropertiesVault() {
        DataSourceProperties properties = new DataSourceProperties();

        VaultResponse vaultResponse = vaultTemplate.read(vaultPath);
        DbConfig creds = new ObjectMapper().convertValue(
                vaultResponse.getData().get("data"),
                DbConfig.class);

        properties.setUrl(creds.getUrl());
        properties.setPassword(creds.getPassword());
        properties.setUsername(creds.getUsername());
        return properties;
    }


}