# WebRTC Video Call Application

A video calling application built with Spring Boot and WebRTC that supports screen sharing, front/back camera switching, and multi-user rooms.

## Features

- Create and join video call rooms
- Real-time video and audio communication
- Screen sharing
- Front/back camera switching
- Multiple participants in a single room
- Mute/unmute audio
- Enable/disable video
- Simple and intuitive UI with Thymeleaf

## Tech Stack

- **Backend**: Spring Boot 2.7.0
- **Frontend**: Thymeleaf, Bootstrap 5, JavaScript
- **WebRTC**: For peer-to-peer communication
- **WebSocket**: For signaling using STOMP over SockJS

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Modern web browser that supports WebRTC (Chrome, Firefox, Edge, Safari)

## Running the Application

1. Clone this repository:
   ```
   git clone https://github.com/yourusername/webrtc-video-call.git
   cd webrtc-video-call
   ```

2. Build and run the application:
   ```
   mvn spring-boot:run
   ```

3. Open your browser and navigate to:
   ```
   http://localhost:8080
   ```

4. Create a new room or join an existing one to start a video call

## Notes for Development

- The application uses STUN servers for NAT traversal. For production, consider adding TURN servers as well.
- For proper camera switching functionality, HTTPS is required when deployed to production (browsers restrict access to multiple cameras on insecure origins).
- For local development, you can use localhost without HTTPS.

## How It Works

1. Users create or join a room
2. WebSocket connection is established for signaling
3. When users join a room, WebRTC peer connections are created
4. Media streams (audio/video) are exchanged directly between peers
5. Screen sharing is implemented by replacing video tracks in the peer connection

## Security Considerations

- This application is designed for demonstration purposes
- In a production environment, consider adding:
  - User authentication
  - Room access control
  - End-to-end encryption
  - TURN server for reliable connectivity 