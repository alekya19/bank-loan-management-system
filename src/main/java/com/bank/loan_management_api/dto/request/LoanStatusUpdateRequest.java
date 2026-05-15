package com.bank.loan_management_api.dto.request;

import com.bank.loan_management_api.enums.LoanStatus;
import jakarta.validation.constraints.NotNull;

public class LoanStatusUpdateRequest {

    @NotNull(message = "Loan status is required")
    private LoanStatus status;

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }
}