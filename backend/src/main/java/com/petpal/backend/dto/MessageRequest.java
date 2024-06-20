package com.petpal.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageRequest {
    private Long senderId;
    private Long receiverId;
    private String message;
}