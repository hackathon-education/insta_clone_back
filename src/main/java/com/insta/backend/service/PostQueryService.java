package com.insta.backend.service;

import com.insta.backend.Model.Post;
import com.insta.backend.Repo.PostRepo;
import com.insta.backend.controller.dto.PostCardDto;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PostQueryService {

    private final PostRepo postRepo;
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_INSTANT;

    public PostQueryService(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    public Page<PostCardDto> listPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> result = postRepo.findAllByOrderByCreatedAtDesc(pageable);
        return result.map(this::toCardDto);
    }

    public List<PostCardDto> listAll() {
        // 소량일 때만 추천. 메인 피드는 보통 페이징 권장
        Page<Post> result = postRepo.findAllByOrderByCreatedAtDesc(
                PageRequest.of(0, 1000, Sort.by(Sort.Direction.DESC, "createdAt")));
        return result.map(this::toCardDto).getContent();
    }

    public PostCardDto getOne(String postId) {
        Post p = postRepo.findById(postId).orElseThrow(() -> new NotFoundException("Post not found"));
        return toCardDto(p);
    }

    private PostCardDto toCardDto(Post p) {
        return new PostCardDto(
                p.getId(),
                p.getUserId(),
                p.getContent(),
                p.getImage(),
                p.getLikes(),
                p.getComments(),
                p.getCreatedAt() != null ? ISO.format(p.getCreatedAt()) : null);
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String msg) {
            super(msg);
        }
    }
}
