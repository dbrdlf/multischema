package com.yukil.multischema.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("database")
@Component
@Getter
@Setter
public class AppProperties {
    private String setting;
    private String logging;

}
