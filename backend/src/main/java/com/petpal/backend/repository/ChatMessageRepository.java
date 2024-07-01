package com.petpal.backend.repository;


import com.petpal.backend.domain.Message;
import com.petpal.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiver(User sender, User receiver);

    @Query("SELECT DISTINCT m.sender FROM Message m WHERE m.receiver = :user UNION SELECT DISTINCT m.receiver FROM Message m WHERE m.sender = :user")
    List<User> findDistinctUsersByMessages(@Param("user") User user);

    @Query("SELECT m FROM Message m WHERE (m.sender = :user1 AND m.receiver = :user2) OR (m.sender = :user2 AND m.receiver = :user1)")
    List<Message> findMessagesBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);
}
