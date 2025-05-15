package com.project.webrtc.controller;

import com.project.webrtc.model.Room;
import com.project.webrtc.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class VideoCallController {

    private final RoomService roomService;
    
    public VideoCallController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "index";
    }

    @PostMapping("/create-room")
    public String createRoom(@RequestParam String roomName) {
        Room room = roomService.createRoom(roomName);
        return "redirect:/room/" + room.getId();
    }

    @GetMapping("/room/{roomId}")
    public String joinRoom(@PathVariable String roomId, Model model) {
        Room room = roomService.getRoom(roomId);
        if (room == null) {
            return "redirect:/";
        }
        
        // Generate a unique user ID for this session
        String userId = UUID.randomUUID().toString();
        
        model.addAttribute("room", room);
        model.addAttribute("userId", userId);
        return "room";
    }
} 