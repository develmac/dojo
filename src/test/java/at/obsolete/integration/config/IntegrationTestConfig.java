package at.obsolete.integration.config;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class IntegrationTestConfig {

    @Bean
    public RestTemplateBuilder testRestTemplateBuilder() {
        return new RestTemplateBuilder();
    }
}
