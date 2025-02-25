package com.bayaniact.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username) {

        return ResponseEntity.ok("POST received: " + username);
    }

}
