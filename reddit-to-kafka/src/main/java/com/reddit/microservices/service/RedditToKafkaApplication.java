package com.reddit.microservices.service;

import com.reddit.microservices.config.RedditAPIConfigData;
import com.reddit.microservices.config.RedditToKafkaConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import java.util.Arrays;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.reddit.microservices")
public class RedditToKafkaApplication implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(RedditToKafkaApplication.class);
  private RedditToKafkaConfigData redditToKafkaConfigData;
  private RedditAPIConfigData redditAPIConfigData;
  private RedditAPIService redditAPIService;

  public RedditToKafkaApplication(RedditToKafkaConfigData redditToKafkaConfigData, RedditAPIConfigData redditAPIConfigData,
                                  RedditAPIService redditAPIService) {
    this.redditToKafkaConfigData = redditToKafkaConfigData;
    this.redditAPIConfigData = redditAPIConfigData;
    this.redditAPIService = redditAPIService;
  }

  public static void main(String[] args) {
    SpringApplication.run(RedditToKafkaApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    LOG.info("Starting Reddit to Kafka service");
    LOG.info("Config obj : " + redditToKafkaConfigData);
    LOG.info("Welcome message " + redditToKafkaConfigData.getWelcomeMessage());
    LOG.info(Arrays.toString(redditToKafkaConfigData.getRedditTopics().toArray(new String[] {})));
    LOG.info("username : " + redditAPIConfigData);
    redditAPIService.submitSubRedditDataToKafka(redditToKafkaConfigData.getRedditTopics());
  }

}
