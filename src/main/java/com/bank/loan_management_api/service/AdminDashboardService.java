package com.bank.loan_management_api.service;

import com.bank.loan_management_api.dto.response.DashboardSummaryResponse;
import com.bank.loan_management_api.enums.LoanStatus;
import com.bank.loan_management_api.repository.CustomerRepository;
import com.bank.loan_management_api.repository.LoanApplicationRepository;
import com.bank.loan_management_api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    public AdminDashboardService(UserRepository userRepository,
                                 CustomerRepository customerRepository,
                                 LoanApplicationRepository loanApplicationRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.loanApplicationRepository = loanApplicationRepository;
    }

    public DashboardSummaryResponse getDashboardSummary() {

        DashboardSummaryResponse response = new DashboardSummaryResponse();

        response.setTotalUsers(userRepository.count());
        response.setTotalCustomers(customerRepository.count());
        response.setTotalLoans(loanApplicationRepository.count());

        response.setSubmittedLoans(
                loanApplicationRepository.countByStatus(LoanStatus.SUBMITTED)
        );

        response.setUnderReviewLoans(
                loanApplicationRepository.countByStatus(LoanStatus.UNDER_REVIEW)
        );

        response.setApprovedLoans(
                loanApplicationRepository.countByStatus(LoanStatus.APPROVED)
        );

        response.setRejectedLoans(
                loanApplicationRepository.countByStatus(LoanStatus.REJECTED)
        );

        response.setTotalRequestedAmount(
                loanApplicationRepository.sumTotalRequestedAmount()
        );

        return response;
    }
}