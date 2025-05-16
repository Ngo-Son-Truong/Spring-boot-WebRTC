package com.project.webrtc.config;

import com.project.webrtc.model.User;
import com.project.webrtc.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public AuthInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Skip authentication for login, register pages and static resources
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/login") || 
            requestURI.equals("/register") || 
            requestURI.startsWith("/css/") || 
            requestURI.startsWith("/js/") || 
            requestURI.startsWith("/images/")) {
            return true;
        }

        // Check if the user is authenticated
        String sessionId = getSessionIdFromCookies(request);
        if (sessionId != null) {
            User user = userService.getUserBySessionId(sessionId);
            if (user != null) {
                // Store user in request for controllers to use
                request.setAttribute("currentUser", user);
                return true;
            }
        }

        // Redirect to login page if not authenticated
        response.sendRedirect("/login");
        return false;
    }

    private String getSessionIdFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> sessionCookie = Arrays.stream(cookies)
                    .filter(cookie -> "SESSION_ID".equals(cookie.getName()))
                    .findFirst();

            if (sessionCookie.isPresent()) {
                return sessionCookie.get().getValue();
            }
        }
        return null;
    }
} 