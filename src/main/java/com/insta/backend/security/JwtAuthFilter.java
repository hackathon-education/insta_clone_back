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
        // System.out.println("Authorization header: " + auth); // 디버깅용

        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            // System.out.println("Extracted token: " + token); // 디버깅용

            try {
                String email = jwtUtil.getSubject(token);
                // System.out.println("Extracted email from token: " + email); // 디버깅용

                UserDetails ud = userDetailsService.loadUserByUsername(email);
                // System.out.println("UserDetails loaded: " + ud.getUsername()); // 디버깅용
                // System.out.println("UserDetails authorities: " + ud.getAuthorities()); //
                // 디버깅용

                var authToken = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
                // System.out.println("Authentication set successfully"); // 디버깅용

            } catch (Exception e) {
                // System.out.println("JWT processing error: " + e.getMessage()); // 디버깅용
                e.printStackTrace();
            }
        }
        chain.doFilter(req, res);
    }
}