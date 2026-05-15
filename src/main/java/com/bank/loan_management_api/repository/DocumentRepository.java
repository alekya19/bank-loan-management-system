package com.bank.loan_management_api.repository;

import com.bank.loan_management_api.entity.Customer;
import com.bank.loan_management_api.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByCustomer(Customer customer);
}