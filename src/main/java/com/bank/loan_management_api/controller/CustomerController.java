package com.bank.loan_management_api.controller;

import com.bank.loan_management_api.dto.request.CustomerCreateRequest;
import com.bank.loan_management_api.dto.response.CustomerResponse;
import com.bank.loan_management_api.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponse> createCustomerProfile(
            @Valid @RequestBody CustomerCreateRequest request,
            Principal principal) {

        CustomerResponse response = customerService.createCustomerProfile(
                principal.getName(),
                request
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}