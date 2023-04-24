package io.github.mroncatto.itflow.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
public class Auditor {

    @Bean
    public org.springframework.data.domain.AuditorAware<String> auditorAware() {
        return new SpringSecurityAuditorAware();
    }

}
