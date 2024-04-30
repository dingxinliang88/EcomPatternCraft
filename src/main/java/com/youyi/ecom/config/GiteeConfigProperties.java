package com.youyi.ecom.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "gitee")
public class GiteeConfigProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String state;
    private String userPrefix;
    private String codeUrl;
    private String tokenUrl;
    private String infoUrl;
}
