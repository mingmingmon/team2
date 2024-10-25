package com.example.team2.global.config;


import io.swagger.v3.oas.models.info.Contact;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI springBootAPI() {
        Info info = new Info()
                .title("2팀")
                .description("사회 초년생을 위한 금융 교육 서비스")
                .contact(new Contact().name("전민주").url("https://github.com/mingmingmon").email("mingmingmon@naver.com"));

        Server server = new Server().url("/");

        return new OpenAPI()
                .servers(List.of(server))
                .info(info);
    }
}
