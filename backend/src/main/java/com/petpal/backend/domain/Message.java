package com.petpal.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    private Long messageId;
    private Long senderId;
    private Long receiverId;
    private String message;
    private LocalDate createdAt;

}
