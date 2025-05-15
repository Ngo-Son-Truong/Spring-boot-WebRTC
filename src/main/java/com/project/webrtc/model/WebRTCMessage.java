package com.project.webrtc.model;

public class WebRTCMessage {
    private String from;
    private String type;
    private Object data;
    private String roomId;
    
    public WebRTCMessage() {
    }
    
    public WebRTCMessage(String from, String type, Object data, String roomId) {
        this.from = from;
        this.type = type;
        this.data = data;
        this.roomId = roomId;
    }
    
    public String getFrom() {
        return from;
    }
    
    public void setFrom(String from) {
        this.from = from;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    public String getRoomId() {
        return roomId;
    }
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
} 