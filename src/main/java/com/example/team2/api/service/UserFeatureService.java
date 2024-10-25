package com.example.team2.api.service;

import com.example.team2.api.dto.UserFeatureResponseDto;
import org.springframework.stereotype.Service;

@Service
public class UserFeatureService {

    public UserFeatureResponseDto getUserFeaturePrompt(String level, String category) {
        return UserFeatureResponseDto.toDto(level + " " + "의 " + " " + category + "입니다.");
    }

}


