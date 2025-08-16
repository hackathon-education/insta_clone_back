package com.insta.backend.service;

import com.insta.backend.Model.Users;
import com.insta.backend.Repo.UserRepo;
import com.insta.backend.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final SequenceService sequence;

    public AuthService(UserRepo userRepo,
            BCryptPasswordEncoder encoder,
            AuthenticationManager authManager,
            JwtUtil jwtUtil,
            SequenceService sequence) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.sequence = sequence;
    }

    public Users register(Users req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        // userId 자동 증가
        req.setUserId(sequence.getNextSequence("user_id"));

        // 비밀번호 해시 저장
        req.setPassword(encoder.encode(req.getPassword()));

        Users saved = userRepo.save(req);

        // 응답에서 password는 숨김 처리
        saved.setPassword(null);
        return saved;
    }

    public String login(String email, String rawPassword) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, rawPassword));
        if (!auth.isAuthenticated()) {
            throw new BadCredentialsException("Invalid credentials");
        }
        return jwtUtil.generate(email);
    }
}
