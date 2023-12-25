package com.reddit.microservices.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//@Data
@Configuration
@ConfigurationProperties(prefix = "reddit-to-kafka-service")
public class RedditToKafkaConfigData {

  private List<String> redditTopics;
  private String welcomeMessage;

  public String getWelcomeMessage() {
    return welcomeMessage;
  }

  public List<String> getRedditTopics() {
    return redditTopics;
  }

  public void setRedditTopics(List<String> redditTopics) {
    this.redditTopics = redditTopics;
  }

  public void setWelcomeMessage(String welcomeMessage) {
    this.welcomeMessage = welcomeMessage;
  }
}
