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
import org.springframework.context.annotation.PropertySource;
import java.util.List;
import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.reddit.microservices")
@EnableConfigurationProperties
@PropertySource("application.yml")
public class RedditToKafkaApplication implements CommandLineRunner {

  private static final Logger LOG = LoggerFactory.getLogger(RedditToKafkaApplication.class);

  // @Autowired
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
    submitSubRedditDataToKafka(redditToKafkaConfigData.getRedditTopics());
  }

  private void submitSubRedditDataToKafka(List<String> topics) {

    int limit = 100;
    String urlTemplate = "https://reddit.com/r/%s/new.json?limit=%d";
    while (true) {
      for (String topic : topics) {
        String url = String.format(urlTemplate, topic, limit);
        System.out.println(url);
        RedditApiResponse response = redditAPIService.getAPIData(url);
        if (response != null) {
          System.out.println("Submit to kafka");
        }
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }
    }



  }
}
