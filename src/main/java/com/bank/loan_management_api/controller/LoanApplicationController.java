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
import com.bank.loan_management_api.dto.request.LoanStatusUpdateRequest;
import java.util.List;
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
    //Add API — Get Pending Loans
    @GetMapping("/pending-review")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOAN_OFFICER')")
    public ResponseEntity<List<LoanApplicationResponse>> getPendingLoans() {

        return ResponseEntity.ok(
                loanApplicationService.getPendingLoans()
        );
    }
    //Add API — Update Loan Status
    @PutMapping("/{loanId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'LOAN_OFFICER')")
    public ResponseEntity<LoanApplicationResponse> updateLoanStatus(
            @PathVariable Long loanId,
            @Valid @RequestBody LoanStatusUpdateRequest request) {

        return ResponseEntity.ok(
                loanApplicationService.updateLoanStatus(loanId, request)
        );
    }
    //Add API — Customer View Own Loans
    @GetMapping("/my-loans")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<LoanApplicationResponse>> getMyLoans(
            Principal principal) {

        return ResponseEntity.ok(
                loanApplicationService.getMyLoans(principal.getName())
        );
    }
}