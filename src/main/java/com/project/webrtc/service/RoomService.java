package com.project.webrtc.service;

import com.project.webrtc.model.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomService {
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    public Room createRoom(String roomName) {
        String roomId = UUID.randomUUID().toString();
        Room room = new Room(roomId, roomName);
        rooms.put(roomId, room);
        return room;
    }

    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }

    public void removeRoom(String roomId) {
        rooms.remove(roomId);
    }

    public void addParticipant(String roomId, String userId) {
        Room room = rooms.get(roomId);
        if (room != null) {
            room.addParticipant(userId);
        }
    }

    public void removeParticipant(String roomId, String userId) {
        Room room = rooms.get(roomId);
        if (room != null) {
            room.removeParticipant(userId);
            if (room.isEmpty()) {
                rooms.remove(roomId);
            }
        }
    }
} 