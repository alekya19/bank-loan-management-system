package com.bank.loan_management_api.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class SecureTestController {
    @GetMapping("/api/secure/profile")
    public Map<String, String> profile(Principal principal) {

        return Map.of(
                "message", "You accessed a secured API",
                "loggedInUser", principal.getName()
        );
    }
}
