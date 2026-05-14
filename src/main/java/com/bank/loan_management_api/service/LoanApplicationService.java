package com.bank.loan_management_api.service;

import com.bank.loan_management_api.dto.request.LoanApplicationRequest;
import com.bank.loan_management_api.dto.response.LoanApplicationResponse;
import com.bank.loan_management_api.entity.Customer;
import com.bank.loan_management_api.entity.LoanApplication;
import com.bank.loan_management_api.entity.User;
import com.bank.loan_management_api.enums.LoanStatus;
import com.bank.loan_management_api.exception.BadRequestException;
import com.bank.loan_management_api.repository.CustomerRepository;
import com.bank.loan_management_api.repository.LoanApplicationRepository;
import com.bank.loan_management_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository,
                                  CustomerRepository customerRepository,
                                  UserRepository userRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public LoanApplicationResponse applyForLoan(String email, LoanApplicationRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new BadRequestException("Customer profile must be completed before applying for loan"));

        LoanApplication loan = new LoanApplication();
        loan.setCustomer(customer);
        loan.setLoanType(request.getLoanType());
        loan.setRequestedAmount(request.getRequestedAmount());
        loan.setTermMonths(request.getTermMonths());
        loan.setPurpose(request.getPurpose());
        loan.setStatus(LoanStatus.SUBMITTED);
        loan.setSubmittedAt(LocalDateTime.now());
        loan.setUpdatedAt(LocalDateTime.now());

        LoanApplication savedLoan = loanApplicationRepository.save(loan);

        return mapToResponse(savedLoan);
    }

    private LoanApplicationResponse mapToResponse(LoanApplication loan) {

        LoanApplicationResponse response = new LoanApplicationResponse();

        response.setId(loan.getId());
        response.setLoanType(loan.getLoanType());
        response.setRequestedAmount(loan.getRequestedAmount());
        response.setTermMonths(loan.getTermMonths());
        response.setPurpose(loan.getPurpose());
        response.setStatus(loan.getStatus());
        response.setSubmittedAt(loan.getSubmittedAt());
        response.setCustomerEmail(loan.getCustomer().getUser().getEmail());

        return response;
    }
}