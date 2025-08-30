package com.insta.backend.controller;

import com.insta.backend.Model.SavedPost;
import com.insta.backend.controller.dto.SaveRequest;
import com.insta.backend.controller.dto.SaveResponse;
import com.insta.backend.service.SavedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/saved")
@CrossOrigin(origins = "*")
public class SavedController {

    @Autowired
    private SavedService savedService;

    /**
     * 게시물 저장
     * POST /api/v1/saved
     */
    @PostMapping
    public ResponseEntity<?> savePost(@RequestBody SaveRequest request) {
        try {
            // 요청 검증
            if (request == null || request.getPostId() == null || request.getPostId().trim().isEmpty()) {
                return createErrorResponse(HttpStatus.BAD_REQUEST, 400, "게시물 ID는 필수입니다.");
            }

            // SecurityContext에서 사용자 ID 추출
            String userId = getCurrentUserId();
            if (userId == null) {
                return createErrorResponse(HttpStatus.UNAUTHORIZED, 401, "로그인이 필요합니다.");
            }

            SaveResponse response = savedService.savePost(userId, request.getPostId());
            return ResponseEntity.ok(response);

        } catch (IllegalStateException e) {
            return createErrorResponse(HttpStatus.CONFLICT, 409, e.getMessage());
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 오류입니다. 잠시 후 다시 시도하세요.");
        }
    }

    /**
     * 게시물 저장 해제
     * DELETE /api/v1/saved/{postId}
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> unsavePost(@PathVariable String postId) {
        try {
            // SecurityContext에서 사용자 ID 추출
            String userId = getCurrentUserId();
            if (userId == null) {
                return createErrorResponse(HttpStatus.UNAUTHORIZED, 401, "로그인이 필요합니다.");
            }

            SaveResponse response = savedService.unsavePost(userId, postId);
            return ResponseEntity.ok(response);

        } catch (IllegalStateException e) {
            return createErrorResponse(HttpStatus.NOT_FOUND, 404, e.getMessage());
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 오류입니다. 잠시 후 다시 시도하세요.");
        }
    }

    /**
     * 저장한 게시물 목록 조회
     * GET /api/v1/saved
     */
    @GetMapping
    public ResponseEntity<?> getSavedPosts() {
        try {
            // SecurityContext에서 사용자 ID 추출
            String userId = getCurrentUserId();
            if (userId == null) {
                return createErrorResponse(HttpStatus.UNAUTHORIZED, 401, "로그인이 필요합니다.");
            }

            List<SavedPost> savedPosts = savedService.getSavedPosts(userId);
            return ResponseEntity.ok(savedPosts);

        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 오류입니다. 잠시 후 다시 시도하세요.");
        }
    }

    /**
     * SecurityContext에서 현재 사용자 ID 추출
     */
    private String getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    return ((UserDetails) principal).getUsername();
                }
                return principal.toString();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 에러 응답 생성
     */
    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, int code, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", code);
        errorResponse.put("message", message);
        errorResponse.put("details", null);

        return ResponseEntity.status(status).body(errorResponse);
    }
}