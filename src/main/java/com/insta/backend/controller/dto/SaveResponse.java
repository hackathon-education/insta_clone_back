package com.insta.backend.controller.dto;

import java.time.LocalDateTime;

public class SaveResponse {

    private String postId;
    private boolean saved;
    private LocalDateTime createdAt;

    // 기본 생성자
    public SaveResponse() {
    }

    // 저장 성공용 생성자
    public SaveResponse(String postId, boolean saved, LocalDateTime createdAt) {
        this.postId = postId;
        this.saved = saved;
        this.createdAt = createdAt;
    }

    // 해제 성공용 생성자
    public SaveResponse(String postId, boolean saved) {
        this.postId = postId;
        this.saved = saved;
        this.createdAt = null;
    }

    // Getters and Setters
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}