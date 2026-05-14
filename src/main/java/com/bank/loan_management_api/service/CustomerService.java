package com.bank.loan_management_api.service;

import com.bank.loan_management_api.dto.request.CustomerCreateRequest;
import com.bank.loan_management_api.dto.response.CustomerResponse;
import com.bank.loan_management_api.entity.Customer;
import com.bank.loan_management_api.entity.User;
import com.bank.loan_management_api.exception.BadRequestException;
import com.bank.loan_management_api.repository.CustomerRepository;
import com.bank.loan_management_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository,
                           UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CustomerResponse createCustomerProfile(String email, CustomerCreateRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (customerRepository.existsByUser(user)) {
            throw new BadRequestException("Customer profile already exists");
        }

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setSsnLast4(request.getSsnLast4());
        customer.setEmploymentStatus(request.getEmploymentStatus());
        customer.setAnnualIncome(request.getAnnualIncome());
        customer.setAddressLine1(request.getAddressLine1());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setZipCode(request.getZipCode());

        Customer saved = customerRepository.save(customer);

        return mapToResponse(saved);
    }

    private CustomerResponse mapToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setEmail(customer.getUser().getEmail());
        response.setDateOfBirth(customer.getDateOfBirth());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setEmploymentStatus(customer.getEmploymentStatus());
        response.setAnnualIncome(customer.getAnnualIncome());
        response.setAddressLine1(customer.getAddressLine1());
        response.setCity(customer.getCity());
        response.setState(customer.getState());
        response.setZipCode(customer.getZipCode());
        return response;
    }
}