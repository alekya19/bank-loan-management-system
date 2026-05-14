package com.bank.loan_management_api.dto.request;

import com.bank.loan_management_api.enums.LoanType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class LoanApplicationRequest {

    @NotNull(message = "Loan type is required")
    private LoanType loanType;

    @NotNull(message = "Requested amount is required")
    @Positive(message = "Requested amount must be positive")
    private BigDecimal requestedAmount;

    @NotNull(message = "Term months is required")
    @Min(value = 6, message = "Minimum loan term is 6 months")
    @Max(value = 360, message = "Maximum loan term is 360 months")
    private Integer termMonths;

    @NotBlank(message = "Purpose is required")
    private String purpose;

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}