package com.activiti.modaler.config;

import com.activiti.modaler.api.ActApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@ConditionalOnProperty(name = "act-modeler", havingValue = "true")
@Import(SecurityConfig.class)
public class ActAutoConfiguration {
    @Bean
    public ActApi actApi() {
        return new ActApi();
    }
}
