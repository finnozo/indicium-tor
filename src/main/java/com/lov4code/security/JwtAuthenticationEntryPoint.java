package com.lov4code.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lov4code.response.APIResponse;
import com.lov4code.util.UtilMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        log.error("Responding with unauthorized error. Message - {}", e.getMessage());
        // httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        APIResponse apiResponse = APIResponse.builder()
                .id(0L)
                .timestamp(LocalDateTime.now().toString())
                .success(false)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED.name())
                .path(UtilMethod.getPath())
                .message(e.getMessage())
                .build();
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(mapper.writeValueAsString(apiResponse));
    }
}
