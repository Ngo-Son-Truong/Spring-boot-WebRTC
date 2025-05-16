package com.project.webrtc.controller;

import com.project.webrtc.model.User;
import com.project.webrtc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse response,
                        RedirectAttributes redirectAttributes) {
        
        String sessionId = userService.authenticateUser(username, password);
        
        if (sessionId != null) {
            // Set session cookie
            Cookie sessionCookie = new Cookie("SESSION_ID", sessionId);
            sessionCookie.setMaxAge(86400); // 1 day
            sessionCookie.setPath("/");
            response.addCookie(sessionCookie);
            
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String fullName,
                          RedirectAttributes redirectAttributes) {
        
        User newUser = userService.registerUser(username, password, fullName);
        
        if (newUser != null) {
            redirectAttributes.addFlashAttribute("success", "Registration successful. Please login.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Username already exists");
            return "redirect:/register";
        }
    }

    @GetMapping("/logout")
    public String logout(@RequestParam(name = "session", required = false) String sessionId,
                        HttpServletResponse response) {
        
        if (sessionId != null) {
            userService.logout(sessionId);
        }
        
        // Clear session cookie
        Cookie sessionCookie = new Cookie("SESSION_ID", null);
        sessionCookie.setMaxAge(0);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
        
        return "redirect:/login";
    }
} 