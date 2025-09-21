package com.insta.backend.controller;

import com.insta.backend.controller.dto.PostCardDto;
import com.insta.backend.controller.dto.ApiError;
import com.insta.backend.service.PostQueryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostQueryService service;

    public PostController(PostQueryService service) {
        this.service = service;
    }

    // 목록(기본: 페이징) - /api/v1/posts?page=0&size=12
    @GetMapping
    public Object list(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            // 페이지 파라미터 없으면 간단 배열 (메인 그리드 소량 테스트용)
            List<PostCardDto> list = service.listAll();
            return list;
        }
        Page<PostCardDto> result = service.listPaged(page, size);
        return new PagedResponse(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.hasNext());
    }

    // 상세 - /api/v1/posts/{postId}
    @GetMapping("/{postId}")
    public ResponseEntity<?> getOne(@PathVariable String postId) {
        try {
            return ResponseEntity.ok(service.getOne(postId));
        } catch (PostQueryService.NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiError(404, "Post not found", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiError(500, "Internal server error", null));
        }
    }

    record PagedResponse(
            List<PostCardDto> content,
            int page,
            int size,
            long total,
            boolean hasNext) {
    }
}
