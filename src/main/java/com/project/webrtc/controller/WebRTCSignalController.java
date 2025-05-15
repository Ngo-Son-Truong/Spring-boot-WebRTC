package com.project.webrtc.controller;

import com.project.webrtc.model.WebRTCMessage;
import com.project.webrtc.service.RoomService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebRTCSignalController {

    private final SimpMessagingTemplate messagingTemplate;
    private final RoomService roomService;
    
    public WebRTCSignalController(SimpMessagingTemplate messagingTemplate, RoomService roomService) {
        this.messagingTemplate = messagingTemplate;
        this.roomService = roomService;
    }

    @MessageMapping("/webrtc-signal")
    public void handleSignalling(@Payload WebRTCMessage message) {
        // Forward the message to all users in the room
        String roomId = message.getRoomId();
        
        if (message.getType().equals("join")) {
            // When a user joins a room
            roomService.addParticipant(roomId, message.getFrom());
        } else if (message.getType().equals("leave")) {
            // When a user leaves a room
            roomService.removeParticipant(roomId, message.getFrom());
        }
        
        // Forward the message to all participants in the room
        messagingTemplate.convertAndSend("/topic/room/" + roomId, message);
    }
} 