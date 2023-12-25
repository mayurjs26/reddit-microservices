package com.reddit.microservices.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditApiResponse {
    private PostResponse data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PostResponse {
        private List<RedditPost> children;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RedditPost {
        private String kind;
        private PostData data;

        // Getter and setter
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PostData {
        private String selftext;
        private String title;
        private long created_utc;

        // Getter and setter
    }

}
