package com.example.team2.api.controller;

import com.example.team2.api.dto.UserFeatureResponseDto;
import com.example.team2.api.service.UserFeatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/feature")
@RequiredArgsConstructor
@Tag(name = "userFeature", description = "사용자 특성")
public class UserFeatureController {

    private final UserFeatureService userFeatureService;

    @Operation(summary = "사용자 특성 입력 api", description = "사용자의 특성을 입력 받고 완성된 롤 프롬프트를 String으로 반환합니다.")
    @ResponseBody
    @PostMapping("")
    public ResponseEntity<UserFeatureResponseDto> getUserFeaturePrompt(
            @RequestParam(name = "level", defaultValue = "초급자") String level,
            @RequestParam(name = "category", defaultValue = "근로 기준법") String category
    ) {
        UserFeatureResponseDto prompt = userFeatureService.getUserFeaturePrompt(level, category);
        return ResponseEntity.ok(prompt);
    }
}
