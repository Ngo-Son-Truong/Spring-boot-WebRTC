package com.project.webrtc.model;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private String id;
    private String name;
    private Set<String> participants;

    public Room(String id, String name) {
        this.id = id;
        this.name = name;
        this.participants = ConcurrentHashMap.newKeySet();
    }

    public Room(String id, String name, Set<String> participants) {
        this.id = id;
        this.name = name;
        this.participants = participants;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<String> participants) {
        this.participants = participants;
    }

    public void addParticipant(String userId) {
        participants.add(userId);
    }

    public void removeParticipant(String userId) {
        participants.remove(userId);
    }

    public boolean isEmpty() {
        return participants.isEmpty();
    }
} 