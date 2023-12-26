package com.reddit.microservices.service.entities;

import lombok.Data;

@Data
public class RedditPost {

    private String text;

    private long createdAt;

    private String title;

    public RedditPost(String text, long createdAt, String title) {
        this.text = text;
        this.createdAt = createdAt;
        this.title = title;
    }
}
