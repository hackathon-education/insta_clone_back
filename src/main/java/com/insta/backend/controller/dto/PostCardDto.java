package com.insta.backend.controller.dto;

public class PostCardDto {
    private String postId;
    private String userId;
    private String content;
    private String image;
    private int likes;
    private int comments;
    private String createdAt; // ISO-8601 문자열

    public PostCardDto(String postId, String userId, String content, String image,
            int likes, int comments, String createdAt) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.image = image;
        this.likes = likes;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
