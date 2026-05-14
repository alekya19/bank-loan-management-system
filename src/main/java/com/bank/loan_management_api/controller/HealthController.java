package com.bank.loan_management_api.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/api/health")
    public Map<String, String> healthCheck() {

        return Map.of(
                "status", "UP",
                "service", "loan-management-api"
        );
    }
}
