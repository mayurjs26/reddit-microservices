package com.reddit.microservices.service;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RedditAPIService {

    private static final Logger LOG = LoggerFactory.getLogger(RedditAPIService.class);
    private ObjectMapper mapper;
    private OkHttpClient httpClient;

    public RedditAPIService() {
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

    //    private final RestTemplate restTemplate;
//
//    public RedditAPIService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder builder) {
//        return builder.build();
//    }
//
//    public RedditApiResponse getRedditPostData(String apiUrl) {
//        return restTemplate.getForObject(apiUrl, RedditApiResponse.class);
//    }



}
