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

//  Line 27 and 29 will cause the password of the user change to null from the test case 22 in the IntegrationTest

    public Message saveMessage(MessageRequest messageRequest){
        User sender = userRepository.findById(messageRequest.getSenderId()).orElseThrow(() -> new RuntimeException("Sender not found"));
//        sender.setPassword(null);
        User receiver = userRepository.findById(messageRequest.getReceiverId()).orElseThrow(() -> new RuntimeException("Receiver not found"));
//        sender.setPassword(null);
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
    public List<Message> getMessagesBetweenUsers(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id).orElseThrow(() -> new RuntimeException("User not found: " + user1Id));
        User user2 = userRepository.findById(user2Id).orElseThrow(() -> new RuntimeException("User not found: " + user2Id));
        return chatMessageRepository.findMessagesBetweenUsers(user1, user2);
}

    @Transactional
    public List<User> getContacts(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return chatMessageRepository.findDistinctUsersByMessages(user);
    }
}
