package com.choi.securitystudy.config;

import com.choi.securitystudy.handler.LoginSuccessHandler;
import filter.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF 필터를 disable로 설정
        http
                .csrf(csrf -> csrf.disable());

        // 기본 로그인 비활성화
        http
                .formLogin(login -> login.disable());

        // 경로별 인가
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()
                );

        // 커스텀 필터 추가
        http
                .addFilterBefore(new LoginFilter(authenticationManager(authenticationConfiguration) , loginSuccessHandler), UsernamePasswordAuthenticationFilter.class);

        // 세션 설정 STATELESS
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    // 비밀번호 암호화용 Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
