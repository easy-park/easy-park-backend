package com.oocl.easyparkbackend.Configuration;

import com.itmuch.lightsecurity.enums.HttpMethod;
import com.itmuch.lightsecurity.spec.SpecRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LightSecurityConfigurtion {
    @Bean
    public SpecRegistry specRegistry() {
        return new SpecRegistry()
                .add(HttpMethod.ANY, "/**", "anon()");
    }
}
