package com.bank.loan_management_api.repository;

import com.bank.loan_management_api.entity.Customer;
import com.bank.loan_management_api.entity.LoanApplication;
import com.bank.loan_management_api.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByCustomer(Customer customer);

    List<LoanApplication> findByStatus(LoanStatus status);

    List<LoanApplication> findByCustomer_User_Email(String email);
}