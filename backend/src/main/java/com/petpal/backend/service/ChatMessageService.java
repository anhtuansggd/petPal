package com.petpal.backend.service;

import com.petpal.backend.domain.Message;
import com.petpal.backend.domain.User;
import com.petpal.backend.dto.MessageRequest;
import com.petpal.backend.repository.ChatMessageRepository;
import com.petpal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    public Message saveMessage(MessageRequest messageRequest){
        User sender = userRepository.findById(messageRequest.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
        sender.setPassword(null);
        User receiver = userRepository.findById(messageRequest.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found"));
        sender.setPassword(null);
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessage(messageRequest.getMessage());
        return chatMessageRepository.save(message);
    }

    public List<Message> getMessage(Long senderId, long receiverId){
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));
        List<Message> messages = chatMessageRepository.findBySenderAndReceiver(sender, receiver);
        return messages;
    }

    @Transactional
    public List<User> getContacts(Long senderId){
        User sender = userRepository.findById(senderId).orElseThrow(() -> new RuntimeException("User not found"));
        List<User> contacts = chatMessageRepository.findDistinctReceiverBySender(sender);
        return contacts;

    }
}
