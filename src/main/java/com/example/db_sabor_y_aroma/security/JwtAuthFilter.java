package com.example.db_sabor_y_aroma.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtUtils.validateToken(token)) {
                String username = jwtUtils.getUsernameFromToken(token);
                String rol = jwtUtils.getRolFromToken(token);

                List<SimpleGrantedAuthority> authorities = Collections.emptyList();
                if (rol != null) {
                    String rolLimpio = rol.replace("ROLE_", "");
                    authorities = List.of(
                        new SimpleGrantedAuthority(rolLimpio),
                        new SimpleGrantedAuthority("ROLE_" + rolLimpio)
                    );
                }

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, authorities
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        System.out.println(">>> PETICION: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println(">>> HEADER AUTH: " + header);
        System.out.println(">>> AUTH EN CONTEXTO: " + SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request, response);
    }
}