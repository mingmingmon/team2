package com.example.team2.api.controller;

import com.example.team2.api.dto.ChatRequestDto;
import com.example.team2.api.dto.UserFeatureResponseDto;
import com.example.team2.api.service.ChattingService;
import com.example.team2.api.service.UserFeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Tag(name = "chatting", description = "채팅")
public class ChattingController {

    private final ChattingService chattingService;

    @Operation(summary = "사용자 특성 입력 api", description = "사용자의 특성을 입력 받고 완성된 롤 프롬프트를 String으로 반환합니다.")
    @ResponseBody
    @PostMapping("")
    public Map<String, String> askQuestion(@RequestBody ChatRequestDto request) {
        try {
            // 외부 API 호출하여 응답 받기
            Map<String, Object> output = chattingService.query(Collections.singletonMap("question", request.getQuestion()));

            // 'text' 키 확인
            if (output.containsKey("text")) {
                String apiResponseText = (String) output.get("text");

                // 정규표현식을 사용하여 "API 응답:" 이후 텍스트 전체 추출
                Pattern pattern = Pattern.compile("API 응답:\\s*(.*)", Pattern.DOTALL);
                Matcher matcher = pattern.matcher("API 응답: " + apiResponseText);

                if (matcher.find()) {
                    String responseText = matcher.group(1).trim(); // 앞뒤 공백 제거
                    return Collections.singletonMap("response", responseText);
                } else {
                    return Collections.singletonMap("response", apiResponseText);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "API 응답에 'text' 키가 없습니다.");
            }
        } catch (ResponseStatusException e) {
            throw e; // 이미 처리된 예외 재발생
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }
}