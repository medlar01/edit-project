package com.edit.lib.config;

import com.edit.lib.api.EditApi;
import com.edit.lib.bus.EditBus;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "edit", name = "enabled", havingValue = "true")
@MapperScan("com.edit.lib.bus.mapper")
public class EditAutoConfiguration {
    @Bean
    public EditApi editApi() {
        return new EditApi();
    }

    @Bean
    public EditBus editBus() {
        return new EditBus();
    }

    @Bean
    public BuildCliRunner buildCliRunner() {
        return new BuildCliRunner();
    }
}
