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
import com.bank.loan_management_api.dto.request.LoanStatusUpdateRequest;
import com.bank.loan_management_api.enums.Role;
import java.util.List;
import java.util.stream.Collectors;
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
    //Add Method — Get Pending Loans
    public List<LoanApplicationResponse> getPendingLoans() {

        return loanApplicationRepository
                .findByStatus(LoanStatus.SUBMITTED)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    //Add Method — Update Loan Status
    @Transactional
    public LoanApplicationResponse updateLoanStatus(
            Long loanId,
            LoanStatusUpdateRequest request) {

        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new BadRequestException("Loan not found"));

        LoanStatus newStatus = request.getStatus();

        if (newStatus == LoanStatus.SUBMITTED) {
            throw new BadRequestException("Cannot move loan back to SUBMITTED");
        }

        loan.setStatus(newStatus);
        loan.setUpdatedAt(LocalDateTime.now());

        LoanApplication updatedLoan = loanApplicationRepository.save(loan);

        return mapToResponse(updatedLoan);
    }
    //Add Method — Customer View Own Loans
    public List<LoanApplicationResponse> getMyLoans(String email) {

        return loanApplicationRepository
                .findByCustomer_User_Email(email)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}