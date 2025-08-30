package com.insta.backend.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "savedPosts")
@CompoundIndex(name = "user_post_unique", def = "{'userId': 1, 'postId': 1}", unique = true)
public class SavedPost {

    @Id
    private String id;

    private String userId;
    private String postId;
    private LocalDateTime createdAt;

    // 기본 생성자
    public SavedPost() {
        this.createdAt = LocalDateTime.now();
    }

    // 생성자
    public SavedPost(String userId, String postId) {
        this.userId = userId;
        this.postId = postId;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}