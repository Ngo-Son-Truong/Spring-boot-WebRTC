package com.project.webrtc.controller;

import com.project.webrtc.model.User;
import com.project.webrtc.model.WebRTCMessage;
import com.project.webrtc.service.RoomService;
import com.project.webrtc.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebRTCSignalController {

    private final SimpMessagingTemplate messagingTemplate;
    private final RoomService roomService;
    private final UserService userService;
    
    public WebRTCSignalController(SimpMessagingTemplate messagingTemplate, RoomService roomService, UserService userService) {
        this.messagingTemplate = messagingTemplate;
        this.roomService = roomService;
        this.userService = userService;
    }

    @MessageMapping("/webrtc-signal")
    public void handleSignalling(@Payload WebRTCMessage message) {
        // Forward the message to all users in the room
        String roomId = message.getRoomId();
        String userId = message.getFrom();
        
        // Tìm thông tin người dùng nếu là message join mà không có fullName
        if (message.getFullName() == null || message.getFullName().isEmpty()) {
            User user = userService.getUserById(userId);
            if (user != null) {
                message.setFullName(user.getFullName());
            }
        }
        
        if (message.getType().equals("join")) {
            // When a user joins a room
            roomService.addParticipant(roomId, userId);
        } else if (message.getType().equals("leave")) {
            // When a user leaves a room
            roomService.removeParticipant(roomId, userId);
        }
        
        // Forward the message to all participants in the room
        messagingTemplate.convertAndSend("/topic/room/" + roomId, message);
    }
} 