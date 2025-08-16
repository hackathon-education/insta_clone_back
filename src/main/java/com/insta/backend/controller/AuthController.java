package com.insta.backend.controller;

import com.insta.backend.Model.Users;
import com.insta.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Users user) {
        try {
            Users saved = authService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalStateException e) {
            // 이메일 중복 시 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        try {
            String token = authService.login(email, password);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "401: 이메일 또는 비밀번호가 올바르지 않습니다."));
        } catch (org.springframework.security.core.userdetails.UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "404: 사용자를 찾을 수 없습니다."));
        } catch (org.springframework.security.authentication.DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "403: 비활성화된 계정입니다."));
        } catch (org.springframework.security.authentication.CredentialsExpiredException e) {
            return ResponseEntity.status(402)
                    .body(Map.of("message", "402: 이메일 인증이 완료되지 않았습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "500: 서버 내부 오류가 발생했습니다."));
        }

    }
}
