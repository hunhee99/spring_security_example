package com.choi.securitystudy.handler;

import com.choi.securitystudy.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        String accessToken = this.jwtUtil.createAccessToken(username, role);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = "{\"accessToken\":\"%s\"}".formatted(accessToken);

        response.getWriter().write(json);
        response.getWriter().flush();
    }
}
