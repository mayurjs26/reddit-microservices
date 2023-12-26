package com.reddit.microservices.service.transformer;

import com.reddit.microservices.kafka.avro.model.RedditAvroModel;
import com.reddit.microservices.service.entities.RedditPost;
import org.springframework.stereotype.Component;

@Component
public class RedditToAvroTransformer {

    public RedditAvroModel getRedditAvroModelFromJson(RedditPost post) {
        return RedditAvroModel.newBuilder()
                .setTitle(post.getTitle())
                .setText(post.getText())
                .setCreatedAt(post.getCreatedAt())
                .build();
    }
}
