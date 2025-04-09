package com.bayaniact.util.captcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class RecaptchaService {

    @Value("${google.recaptcha.key.secret}")
    private String secretKey;

    @Value("${recaptcha.verify.url}")
    private String verifyUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean verify(String recaptchaResponse) {
        String url = String.format("%s?secret=%s&response=%s", verifyUrl, secretKey, recaptchaResponse);

        Map<String, Object> response = restTemplate.postForObject(url, null, Map.class);

        return response != null && (Boolean) response.get("success");
    }
}