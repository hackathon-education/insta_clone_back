package com.insta.backend.service;

import com.insta.backend.Model.SavedPost;
import com.insta.backend.Repo.SavedPostRepository;
import com.insta.backend.controller.dto.SaveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SavedService {

    @Autowired
    private SavedPostRepository savedPostRepository;

    /**
     * 게시물 저장
     */
    @Transactional
    public SaveResponse savePost(String userId, String postId) {
        System.out.println("Save request - userId: " + userId + ", postId: " + postId); // 디버깅용

        // 이미 저장되어 있는지 확인
        if (savedPostRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new IllegalStateException("이미 저장한 게시물입니다.");
        }

        try {
            SavedPost savedPost = new SavedPost(userId, postId);
            SavedPost saved = savedPostRepository.save(savedPost);
            // System.out.println("Post saved successfully: " + saved.getId()); // 디버깅용

            return new SaveResponse(saved.getPostId(), true, saved.getCreatedAt());
        } catch (DuplicateKeyException e) {
            // 동시성 처리: 거의 같은 시점에 중복 저장 시도
            throw new IllegalStateException("이미 저장한 게시물입니다.");
        }
    }

    /**
     * 게시물 저장 해제
     */
    @Transactional
    public SaveResponse unsavePost(String userId, String postId) {
        // System.out.println("Unsave request - userId: " + userId + ", postId: " +
        // postId); // 디버깅용

        // 저장되어 있는지 확인
        Optional<SavedPost> savedPost = savedPostRepository.findByUserIdAndPostId(userId, postId);
        // System.out.println("Found saved post: " + savedPost.isPresent()); // 디버깅용

        if (savedPost.isEmpty()) {
            throw new IllegalStateException("저장되지 않은 게시물입니다.");
        }

        savedPostRepository.deleteByUserIdAndPostId(userId, postId);
        // System.out.println("Post unsaved successfully"); // 디버깅용

        return new SaveResponse(postId, false);
    }

    /**
     * 사용자가 저장한 게시물 목록 조회
     */
    public List<SavedPost> getSavedPosts(String userId) {
        // System.out.println("Get saved posts - userId: " + userId); // 디버깅용
        List<SavedPost> result = savedPostRepository.findByUserIdOrderByCreatedAtDesc(userId);
        // System.out.println("Found saved posts count: " + result.size()); // 디버깅용
        return result;
    }

    /**
     * 특정 게시물이 저장되어 있는지 확인
     */
    public boolean isPostSaved(String userId, String postId) {
        return savedPostRepository.existsByUserIdAndPostId(userId, postId);
    }
}