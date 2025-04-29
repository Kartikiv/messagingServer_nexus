package com.example.messagingserver.controller;

import com.example.messagingserver.beans.ChatMessage;
import jakarta.ws.rs.POST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
//    @MessageMapping("/chat.sendPrivate")
//    public void debug(@Payload String rawJson) {
//        System.out.println("RAW MESSAGE: " + rawJson);
//    }

    @MessageMapping("/chat.sendPrivate")
    public void sendPrivateMessage(@Payload ChatMessage message) {

        message.setTimestamp(LocalDateTime.now().toString());

        if (message.getFileData() != null && !message.getFileData().isBlank()) {
            System.out.println(" File received:");
            System.out.println("   ▶ From     : " + message.getSender());
            System.out.println("   ▶ To       : " + message.getRecipient());
            System.out.println("   ▶ Filename : " + message.getFilename());
            System.out.println("   ▶ MIME     : " + message.getMimeType());
            System.out.println("   ▶ Size     : " + message.getFileData().length() + " chars");
        } else {
            System.out.println("Text message:");
            System.out.println("   ▶ From : " + message.getSender());
            System.out.println("   ▶ To   : " + message.getRecipient());
            System.out.println("   ▶ Text : " + message.getContent());
        }

        messagingTemplate.convertAndSendToUser(
                message.getRecipient(),
                "/queue/messages",
                message
        );
    }

}