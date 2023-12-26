package com.reddit.microservices.service;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reddit.microservices.config.KafkaConfigData;
import com.reddit.microservices.kafka.avro.model.RedditAvroModel;
import com.reddit.microservices.kafka.producer.config.service.KafkaProducer;
import com.reddit.microservices.service.entities.RedditPost;
import com.reddit.microservices.service.transformer.RedditToAvroTransformer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RedditAPIService {

    private static final Logger LOG = LoggerFactory.getLogger(RedditAPIService.class);
    private ObjectMapper mapper;
    private OkHttpClient httpClient;
    private final RedditToAvroTransformer redditToAvroTransformer;
    private final KafkaProducer<String, RedditAvroModel> kafkaProducer;

    private final KafkaConfigData kafkaConfigData;

    public RedditAPIService(RedditToAvroTransformer redditToAvroTransformer,
                            KafkaProducer<String, RedditAvroModel> kafkaProducer, KafkaConfigData kafkaConfigData) {
        this.redditToAvroTransformer = redditToAvroTransformer;
        this.kafkaProducer = kafkaProducer;
        this.kafkaConfigData = kafkaConfigData;
        this.mapper = new ObjectMapper();
        this.httpClient = new OkHttpClient();
    }

    public RedditApiResponse getAPIData(String url) {

        RedditApiResponse response;
        try {
            Request request = new Request.Builder().url(url).build();
            Response result = httpClient.newCall(request).execute();
            response = mapper.readValue(result.body().byteStream(), RedditApiResponse.class);

        }  catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    public void submitSubRedditDataToKafka(List<String> subRedditList) {

        int limit = 100;
        String urlTemplate = "https://reddit.com/r/%s/new.json?limit=%d";
        while (true) {
          for (String subReddit : subRedditList) {
            String url = String.format(urlTemplate, subReddit, limit);
            RedditApiResponse response = getAPIData(url);
              System.out.println(response);
//            if (response != null) {
//                response.getData().getChildren().forEach((redditPost -> {
//                    RedditApiResponse.PostData data = redditPost.getData();
//                    RedditPost post = new RedditPost(data.getSelftext(), data.getCreated_utc(), data.getTitle());
//                    RedditAvroModel redditAvroModel = redditToAvroTransformer.getRedditAvroModelFromJson(post);
//                    kafkaProducer.send(kafkaConfigData.getTopicName(),subReddit, redditAvroModel);
//                }));
//              System.out.println("Submit to kafka");
//            }
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }

          }
        }
  }


}
