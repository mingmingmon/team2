package com.example.team2.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestDto {
    String question;
}
