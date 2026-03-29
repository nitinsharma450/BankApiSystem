package com.example.web.user;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Basic;
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
    private long accountNumber; 

    @Column(name = "user_name", nullable = false, length = 20)
    private String name;

    
    @Column(name = "phone_number", length = 15, nullable = false) 
    private long phoneNumber;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "date_account_creation")
    private Date dateOfCreation;
    
    @Column(name = "Balance")
    private double Balance=2000.0;
    
    @Column(name = "role",nullable = false)
    private String role="user";
    
   private String typeOfAccount;
   private double interestRate;
   
  

  

    public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public User(long accountNumber, String name, long phoneNumber, Date dob, Date dateOfCreation, double balance,
			String role, String typeOfAccount, double interestRate) {
		super();
		this.accountNumber = accountNumber;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.dob = dob;
		this.dateOfCreation = dateOfCreation;
		Balance = balance;
		this.role = role;
		this.typeOfAccount = typeOfAccount;
		this.interestRate = interestRate;
		
		
		if(typeOfAccount=="saving") {
			interestRate=5.2;
		}
		
		else {
			interestRate=2.5;
		}
	}



	// Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    

    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
    	
        this.accountNumber = accountNumber;
    }
    

    public double getBalance() {
		return Balance;
	}

	public void setBalance(double balance) {
		
		Balance = balance;
	}
	
	
	

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		
		this.interestRate = interestRate;
	}

	public String getTypeOfAccount() {
		return typeOfAccount;
	}

	public void setTypeOfAccount(String typeOfAccount) {
		this.typeOfAccount = typeOfAccount;
	}

	@Override
	public String toString() {
		return "User [accountNumber=" + accountNumber + ", name=" + name + ", phoneNumber=" + phoneNumber + ", dob="
				+ dob + ", dateOfCreation=" + dateOfCreation + ", Balance=" + Balance + ", role=" + role
				+ ", typeOfAccount=" + typeOfAccount + ", interestRate=" + interestRate + "]";
	}

	


}
