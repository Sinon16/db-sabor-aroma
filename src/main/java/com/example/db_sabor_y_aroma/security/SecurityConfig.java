package com.example.db_sabor_y_aroma.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    @Value("${FRONTEND_URL:http://localhost:5173}")
    private String frontendUrl;

    public SecurityConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", frontendUrl));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Content-Disposition", "Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Documentación Swagger, Consola H2, Ruta de Error y Preflight OPTIONS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/error", "/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // 2. Autenticación y Registro
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/usuarios", "/api/usuarios/**").permitAll()

                        // 3. Catálogo Público
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/ofertas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/regiones/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/comunas/**").permitAll()

                        // 4. Funciones de Compra del CLIENTE (y ADMIN)
                        .requestMatchers("/api/carrito", "/api/carrito/**").hasAnyAuthority("CLIENTE", "ADMIN", "ROLE_CLIENTE", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/ordenes", "/api/ordenes/**").hasAnyAuthority("CLIENTE", "ADMIN", "ROLE_CLIENTE", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/detalle-ordenes", "/api/detalle-ordenes/**").hasAnyAuthority("CLIENTE", "ADMIN", "ROLE_CLIENTE", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/ordenes/usuario/**").hasAnyAuthority("CLIENTE", "ADMIN", "ROLE_CLIENTE", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/reportes", "/api/reportes/**").hasAnyAuthority("CLIENTE", "ADMIN", "ROLE_CLIENTE", "ROLE_ADMIN")

                        // 5. Permisos EXCLUSIVOS del ADMINISTRADOR (CRUD completo)
                        .requestMatchers(HttpMethod.POST, "/api/productos", "/api/productos/**", "/api/categorias", "/api/categorias/**", "/api/ofertas", "/api/ofertas/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/productos", "/api/productos/**", "/api/categorias", "/api/categorias/**", "/api/ofertas", "/api/ofertas/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/productos", "/api/productos/**", "/api/categorias", "/api/categorias/**", "/api/ofertas", "/api/ofertas/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        .requestMatchers("/api/ordenes", "/api/ordenes/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers("/api/detalle-ordenes", "/api/detalle-ordenes/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers("/api/reportes", "/api/reportes/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")

                        // Cualquier otra ruta requiere estar autenticado
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .addFilterBefore(new JwtAuthFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}