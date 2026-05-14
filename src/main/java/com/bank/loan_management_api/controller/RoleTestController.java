package com.bank.loan_management_api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class RoleTestController {

    @GetMapping("/api/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, String> adminDashboard(Principal principal) {
        return Map.of(
                "message", "Welcome Admin Dashboard",
                "user", principal.getName()
        );
    }

    @GetMapping("/api/loans/review")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOAN_OFFICER')")
    public Map<String, String> loanReview(Principal principal) {
        return Map.of(
                "message", "Loan review access granted",
                "user", principal.getName()
        );
    }

    @GetMapping("/api/customer/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Map<String, String> customerProfile(Principal principal) {
        return Map.of(
                "message", "Customer profile access granted",
                "user", principal.getName()
        );
    }
}