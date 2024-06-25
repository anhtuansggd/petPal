package com.petpal.backend.controller;

import com.petpal.backend.domain.Message;
import com.petpal.backend.domain.User;
import com.petpal.backend.dto.MessageRequest;
import com.petpal.backend.repository.ChatMessageRepository;
import com.petpal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest messageRequest) {
        User sender = userRepository.findById(messageRequest.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(messageRequest.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found"));
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(messageRequest.getMessage());
        chatMessageRepository.save(message);
        return ResponseEntity.ok().body("Message sent successfully");
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(@RequestParam Long senderId, @RequestParam Long receiverId) {
        User sender = userRepository.findById(senderId).orElseThrow();
        User receiver = userRepository.findById(receiverId).orElseThrow();
        List<Message> messages = chatMessageRepository.findBySenderAndReceiver(sender, receiver);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/contacts/{senderId}")
    public ResponseEntity<List<User>> getContacts(@PathVariable Long senderId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(sender);
        List<User> contacts = chatMessageRepository.findDistinctReceiverBySender(sender);
        for (User contact : contacts) {
            System.out.println(contact);
        }
        return ResponseEntity.ok(contacts);
    }
}