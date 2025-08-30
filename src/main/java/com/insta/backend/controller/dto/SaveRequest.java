package com.insta.backend.controller.dto;

public class SaveRequest {

    private String postId;

    // 기본 생성자
    public SaveRequest() {
    }

    // 생성자
    public SaveRequest(String postId) {
        this.postId = postId;
    }

    // Getter and Setter
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}