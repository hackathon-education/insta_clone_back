package com.insta.backend.security;

import com.insta.backend.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, CustomUserDetailsService uds) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = uds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                String email = jwtUtil.getSubject(token);
                UserDetails ud = userDetailsService.loadUserByUsername(email);
                var authToken = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (Exception ignored) {
            }
        }
        chain.doFilter(req, res);
    }
}
