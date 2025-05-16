package com.project.webrtc.controller;

import com.project.webrtc.model.Room;
import com.project.webrtc.model.User;
import com.project.webrtc.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class VideoCallController {

    private final RoomService roomService;
    
    public VideoCallController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        User currentUser = (User) request.getAttribute("currentUser");
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("user", currentUser);
        return "index";
    }

    @PostMapping("/create-room")
    public String createRoom(@RequestParam String roomName, HttpServletRequest request) {
        User currentUser = (User) request.getAttribute("currentUser");
        
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        Room room = roomService.createRoom(roomName);
        return "redirect:/room/" + room.getId();
    }

    @GetMapping("/room/{roomId}")
    public String joinRoom(@PathVariable String roomId, Model model, HttpServletRequest request) {
        User currentUser = (User) request.getAttribute("currentUser");
        
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        Room room = roomService.getRoom(roomId);
        if (room == null) {
            return "redirect:/";
        }
        
        // Use user's ID for WebRTC signaling
        String userId = currentUser.getId();
        
        model.addAttribute("room", room);
        model.addAttribute("userId", userId);
        model.addAttribute("user", currentUser);
        model.addAttribute("fullName", currentUser.getFullName());
        return "room";
    }
} 