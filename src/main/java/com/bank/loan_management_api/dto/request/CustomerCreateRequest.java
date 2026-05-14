package com.bank.loan_management_api.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomerCreateRequest {

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "SSN last 4 digits are required")
    @Size(min = 4, max = 4, message = "SSN last 4 must be exactly 4 digits")
    private String ssnLast4;

    @NotBlank(message = "Employment status is required")
    private String employmentStatus;

    @NotNull(message = "Annual income is required")
    @Positive(message = "Annual income must be positive")
    private BigDecimal annualIncome;

    @NotBlank(message = "Address line is required")
    private String addressLine1;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip code is required")
    private String zipCode;

    // getters and setters


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSsnLast4() {
        return ssnLast4;
    }

    public void setSsnLast4(String ssnLast4) {
        this.ssnLast4 = ssnLast4;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}