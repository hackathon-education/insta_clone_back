package com.insta.backend.Repo;

import com.insta.backend.Model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepo extends MongoRepository<Post, String> {
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
