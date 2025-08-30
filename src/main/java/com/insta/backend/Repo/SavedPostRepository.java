package com.insta.backend.Repo;

import com.insta.backend.Model.SavedPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SavedPostRepository extends MongoRepository<SavedPost, String> {

    // 특정 사용자의 특정 게시물 저장 여부 확인
    Optional<SavedPost> findByUserIdAndPostId(String userId, String postId);

    // 특정 사용자가 저장한 모든 게시물 조회 (최신순)
    List<SavedPost> findByUserIdOrderByCreatedAtDesc(String userId);

    // 특정 사용자의 특정 게시물 저장 해제
    void deleteByUserIdAndPostId(String userId, String postId);

    // 특정 게시물을 저장한 사용자 수 (선택적 - 통계용)
    @Query("{ 'postId': ?0 }")
    long countByPostId(String postId);

    // 특정 사용자가 저장한 게시물 존재 여부 확인
    boolean existsByUserIdAndPostId(String userId, String postId);
}