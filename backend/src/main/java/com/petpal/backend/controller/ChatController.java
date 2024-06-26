package com.petpal.backend.controller;

import com.petpal.backend.domain.Message;
import com.petpal.backend.domain.User;
import com.petpal.backend.dto.MessageRequest;
import com.petpal.backend.repository.ChatMessageRepository;
import com.petpal.backend.repository.UserRepository;
import com.petpal.backend.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest messageRequest) {
        Message message = chatMessageService.saveMessage(messageRequest);
        return ResponseEntity.ok().body("Message sent successfully: " + message.getId());
    }

    @GetMapping("/messages")
    public ResponseEntity<?> getMessages(@RequestParam Long senderId, @RequestParam Long receiverId) {
        List<Message> messages = chatMessageService.getMessage(senderId, receiverId);
        for(Message message : messages){
            message.getSender().setPassword(null);
            message.getReceiver().setPassword(null);
        }
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/contacts/{senderId}")
    public ResponseEntity<?> getContacts(@PathVariable Long senderId) {
        List<User> contacts = chatMessageService.getContacts(senderId);
        for( User user : contacts){
            user.setPassword(null);
        }
        return ResponseEntity.ok(contacts);
    }
}