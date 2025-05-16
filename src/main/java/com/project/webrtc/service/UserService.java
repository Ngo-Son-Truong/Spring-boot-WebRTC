package com.project.webrtc.service;

import com.project.webrtc.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final Map<String, String> sessions = new ConcurrentHashMap<>(); // sessionId -> userId
    
    public UserService() {
        // Add a default admin user
        User admin = new User(UUID.randomUUID().toString(), "admin", "admin123", "Administrator");
        admin.addRole("ADMIN");
        users.put(admin.getId(), admin);
    }
    
    public User registerUser(String username, String password, String fullName) {
        // Check if username already exists
        if (users.values().stream().anyMatch(user -> user.getUsername().equals(username))) {
            return null;
        }
        
        String userId = UUID.randomUUID().toString();
        User newUser = new User(userId, username, password, fullName);
        newUser.addRole("USER");
        users.put(userId, newUser);
        return newUser;
    }
    
    public String authenticateUser(String username, String password) {
        User user = users.values().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        
        if (user != null) {
            String sessionId = UUID.randomUUID().toString();
            sessions.put(sessionId, user.getId());
            return sessionId;
        }
        
        return null;
    }
    
    public User getUserBySessionId(String sessionId) {
        String userId = sessions.get(sessionId);
        if (userId != null) {
            return users.get(userId);
        }
        return null;
    }
    
    public void logout(String sessionId) {
        sessions.remove(sessionId);
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    public User getUserById(String userId) {
        return users.get(userId);
    }
    
    public User getUserByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
} 