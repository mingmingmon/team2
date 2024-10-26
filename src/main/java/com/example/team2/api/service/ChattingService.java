package com.example.team2.api.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ChattingService {

    private static final String API_URL = "https://flow.edutrack.kr/api/v1/prediction/9b6fbaf1-5f50-4616-bc24-8e2d74bd403b";

    // SSL 검증을 비활성화한 RestTemplate 생성
    private RestTemplate getRestTemplate() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
        } };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        HostnameVerifier allHostsValid = (hostname, session) -> true;
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        return new RestTemplate();
    }

    public Map<String, Object> query(Map<String, String> payload) {
        try {
            RestTemplate restTemplate = getRestTemplate();

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 생성
            HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

            // POST 요청
            ResponseEntity<Map> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new ResponseStatusException(response.getStatusCode(), "HTTP error");
            }
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Request error: " + e.getMessage());
        }
    }

}
