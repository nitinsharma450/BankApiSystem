package com.example.web.user;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long accountNumber; // Changed to Long, assuming it's a numeric account number

    @Column(name = "user_name", nullable = false, length = 20)
    private String name;

    
    @Column(name = "phone_number", length = 15, nullable = false) // Increased length and added nullable = false
    private int phoneNumber;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "date_account_creation")
    private Date dateOfCreation;

    @Column(name = "balance", nullable = false)
    private Double balance = 0.0;

    public User() {
        // Default constructor (no need to call super())
    }

    public User(String name, int phoneNumber, Date dob, Date dateOfCreation, Long accountNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.dateOfCreation = dateOfCreation;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    @Override
    public String toString() {
        return "User [name=" + name + ", phoneNumber=" + phoneNumber + ", dob=" + dob + ", dateOfCreation=" + dateOfCreation + ", accountNumber=" + accountNumber + ", balance=" + balance+"]";
    }
}
