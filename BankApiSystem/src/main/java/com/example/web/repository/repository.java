package com.example.web.repository;



import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.web.user.User;




// import com.example.web.user; // Removed incorrect import


@Repository
public class repository {
	
	@Autowired
	private SessionFactory factory;
	@Autowired
    private TransactionRepository transactionRepository;
	
	 
	public void insert(User user) {
		Transaction transaction=null;
            try (Session session = factory.openSession()) {
                transaction=session.beginTransaction();
                
                session.persist(user);
                transaction.commit();
            }
                catch(Exception e){
                
               
                transaction.rollback();}
		}
		
		public void deleteAccount(int AccountNumber) {
			Transaction transaction=null;
			Session session=factory.openSession();
			
			try {
				transaction=session.beginTransaction();
			User user=session.get(User.class,AccountNumber);
				session.remove(user);
				
				transaction.commit();
			}
			catch (Exception e) {
				// TODO: handle exception
				transaction.rollback();
				System.out.println("SOMETHING WENT WRONG QUERY GET ROLLBACK");
			}
			
			finally {
				session.close();
			}
			
		}
	
		public void UpdateAccount(User user) {
			Session session = factory.openSession();
			Transaction transaction = null;
			if (user.getAccountNumber() == null) {
				throw new IllegalArgumentException("Account number cannot be null");
			}
			try {
				transaction = session.beginTransaction();
				
				// Retrieve the user by ID (assuming user.getAccountNumber() gets the user's account number)
				User userToUpdate = session.get(User.class, user.getAccountNumber());  // Use the user's ID
				
				if (userToUpdate != null) {
					// Update the fields of userToUpdate with the new values from user
					userToUpdate.setName(user.getName());
					userToUpdate.setPhoneNumber(user.getPhoneNumber());
					userToUpdate.setDob(user.getDob());
					userToUpdate.setDateOfCreation(user.getDateOfCreation());
					// userToUpdate.setAccountNumber(user.getAccountNumber());
					// Add other fields as needed
				
					// Merge the updated user object
					session.update(userToUpdate);
				
					transaction.commit();  // Commit the transaction
				} else {
					System.out.println("User not found with account number: " + user.getAccountNumber());
				}
				
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();  // Rollback in case of an exception
				}
				System.out.println("SOMETHING WENT WRONG ON UPDATING ACCOUNT");
				e.printStackTrace();
			} finally {
				session.close();  // Close the session
			}
		}
		public User getAccount(int accountNumber) {
        Session session = factory.openSession();
        User user = null;
        
        try {
            user = session.get(User.class, (long)accountNumber);
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG RETRIEVING ACCOUNT");
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return user;
    }
    
    public List<User> getAllAccounts() {
        Session session = factory.openSession();
        List<User> users = new ArrayList<>();
        
        try {
            Query<User> query = session.createQuery("from User", User.class);
            users = query.getResultList();
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG RETRIEVING ALL ACCOUNTS");
            e.printStackTrace();
        } finally {
            session.close();
        }
		return users;
    }


	public boolean deposit(Long accountNumber, Double amount, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        Session session = factory.openSession();
        Transaction transaction = null;
        boolean success = false;
        
        try {
            transaction = session.beginTransaction();
            
            User user = session.get(User.class, accountNumber);
            if (user == null) {
                throw new IllegalArgumentException("Account not found with number: " + accountNumber);
            }
            
            // Update balance
            Double newBalance = user.getBalance() + amount;
            user.setBalance(newBalance);
            
            // Update user in database
            session.merge(user);
            transaction.commit();
			Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
			com.example.web.transaction.Transaction txn = new com.example.web.transaction.Transaction(
				accountNumber, amount, "DEPOSIT", currentDate, description, newBalance);
            transactionRepository.saveTransaction(txn);
            
            success = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("SOMETHING WENT WRONG DURING DEPOSIT");
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return success;
    }
	
	public boolean withdraw(Long accountNumber, Double amount, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        Session session = factory.openSession();
        Transaction transaction = null;
        boolean success = false;
        
        try {
            transaction = session.beginTransaction();
            
            User user = session.get(User.class, accountNumber);
            if (user == null) {
                throw new IllegalArgumentException("Account not found with number: " + accountNumber);
            }
            
            // Check if sufficient funds
            if (user.getBalance() < amount) {
                throw new IllegalArgumentException("Insufficient funds: balance is " + user.getBalance());
            }
			Double newBalance = user.getBalance() - amount;
            user.setBalance(newBalance);
            
            // Update user in database
            session.update(user);
            transaction.commit();
            
            // Record the transaction
            Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
            com.example.web.transaction.Transaction txn = new com.example.web.transaction.Transaction(
                accountNumber, amount, "WITHDRAW", currentDate, description, newBalance);
            transactionRepository.saveTransaction(txn);
            
            success = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("SOMETHING WENT WRONG DURING WITHDRAWAL");
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return success;
    }
	public List<com.example.web.transaction.Transaction> getTransactionHistory(Long accountNumber) {
        return transactionRepository.getTransactionsForAccount(accountNumber);
    }
    public void moneyTransfer(long ac1 ,long ac2, double amount){
        Transaction transaction=null;
        Session session =factory.openSession();
        try {
            transaction =session.beginTransaction();
            User user1=session.get(User.class,ac1);
            double newBalance=user1.getBalance()-amount;
            user1.setBalance(newBalance);
            User user2=session.get(User.class,ac2);
            double newBalance2=user2.getBalance()+amount;
            // deposit(ac2, amount, "Money transfer ");
            user2.setBalance(newBalance2);
            session.merge(user2);
            session.merge(user1);
            transaction.commit();
            System.out.println("tranfered");
        } catch (Exception e) {
            transaction.rollback();
        }
        session.close();
    }
}
