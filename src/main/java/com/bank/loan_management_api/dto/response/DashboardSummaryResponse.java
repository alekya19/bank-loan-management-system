package com.bank.loan_management_api.dto.response;

import java.math.BigDecimal;

public class DashboardSummaryResponse {

    private long totalUsers;
    private long totalCustomers;
    private long totalLoans;
    private long submittedLoans;
    private long underReviewLoans;
    private long approvedLoans;
    private long rejectedLoans;
    private BigDecimal totalRequestedAmount;

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public long getTotalLoans() {
        return totalLoans;
    }

    public void setTotalLoans(long totalLoans) {
        this.totalLoans = totalLoans;
    }

    public long getSubmittedLoans() {
        return submittedLoans;
    }

    public void setSubmittedLoans(long submittedLoans) {
        this.submittedLoans = submittedLoans;
    }

    public long getUnderReviewLoans() {
        return underReviewLoans;
    }

    public void setUnderReviewLoans(long underReviewLoans) {
        this.underReviewLoans = underReviewLoans;
    }

    public long getApprovedLoans() {
        return approvedLoans;
    }

    public void setApprovedLoans(long approvedLoans) {
        this.approvedLoans = approvedLoans;
    }

    public long getRejectedLoans() {
        return rejectedLoans;
    }

    public void setRejectedLoans(long rejectedLoans) {
        this.rejectedLoans = rejectedLoans;
    }

    public BigDecimal getTotalRequestedAmount() {
        return totalRequestedAmount;
    }

    public void setTotalRequestedAmount(BigDecimal totalRequestedAmount) {
        this.totalRequestedAmount = totalRequestedAmount;
    }
}