package com.bank.loan_management_api.controller;

import com.bank.loan_management_api.dto.request.LoanApplicationRequest;
import com.bank.loan_management_api.dto.response.LoanApplicationResponse;
import com.bank.loan_management_api.service.LoanApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/loans")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<LoanApplicationResponse> applyForLoan(
            @Valid @RequestBody LoanApplicationRequest request,
            Principal principal) {

        LoanApplicationResponse response =
                loanApplicationService.applyForLoan(principal.getName(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}