package com.example.web.dto;

public class TransactionRequest {
    private Long accountNumber;
    private Double amount;
    private String description;
    
    // Default constructor
    public TransactionRequest() {
    }
    
    // Getters and setters
    public Long getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}