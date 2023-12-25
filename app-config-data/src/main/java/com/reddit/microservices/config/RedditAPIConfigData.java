package com.reddit.microservices.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "api")
public class RedditAPIConfigData {

  private String username;
  private String password;
  private String clientId;
  private String secret;

  public String getUsername() {
    return username;
  }
}
