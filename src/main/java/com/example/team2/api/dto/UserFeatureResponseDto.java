package com.example.team2.api.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserFeatureResponseDto {
    String prompt;

    public static UserFeatureResponseDto toDto(String prompt) {
        return UserFeatureResponseDto.builder()
                .prompt(prompt)
                .build();
    }
}
