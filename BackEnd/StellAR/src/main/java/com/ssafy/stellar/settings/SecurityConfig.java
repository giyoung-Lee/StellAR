package com.ssafy.stellar.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(http -> http.disable())  // HTTP 기본 인증 비활성화
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
                .cors(cors -> cors.disable())  // CORS 설정 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").authenticated()  // Swagger 경로에 대해 인증 요구
                        .anyRequest().permitAll())  // 그 외의 모든 요청에 대해 접근 허용
                .formLogin(form -> form
                        .defaultSuccessUrl("/swagger-ui/index.html", true)  // 로그인 성공 시 Swagger UI로 리디렉션
                        .permitAll())
                .logout(logout -> logout.permitAll());  // 로그아웃 허용

        return httpSecurity.build();
    }
}
