package com.petpal.backend.repository;


import com.petpal.backend.domain.Message;
import com.petpal.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiver(User sender, User receiver);

    @Query("SELECT DISTINCT m.receiver FROM Message m WHERE m.sender = :sender")
    List<User> findDistinctReceiverBySender(@Param("sender") User sender);
}