package com.example.web.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.web.user.TransactionHistory;
import com.example.web.user.User;

@Repository
public class repository {

    @Autowired
    private SessionFactory factory;

    @Autowired
    private TransactionRepository transactionRepo;

    // Helper method to log transactions
    private void logTransaction(long accountNumber, String type, double amount, String desc) {
        TransactionHistory tx = new TransactionHistory(accountNumber, type, amount, desc);
        transactionRepo.saveTransaction(tx);
    }

    public void insert(User user) {
        Transaction transaction = null;
        Session session = factory.openSession();

        try {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
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

    public void deleteAccount(long AccountNumber) {
        Transaction transaction = null;
        Session session = factory.openSession();

        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, AccountNumber);
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("SOMETHING WENT WRONG QUERY GET ROLLBACK");
        } finally {
            session.close();
        }
    }

    public void UpdateAccount(User user) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            User userToUpdate = session.get(User.class, user.getAccountNumber());

            if (userToUpdate != null) {
                userToUpdate.setPhoneNumber(user.getPhoneNumber());
                userToUpdate.setDob(user.getDob());
                userToUpdate.setDateOfCreation(user.getDateOfCreation());
                userToUpdate.setName(user.getName());

                session.update(userToUpdate);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public User getAccount(long accountNumber) {
        Session session = factory.openSession();
        User user = null;

        try {
            user = session.get(User.class, accountNumber);
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG RETRIEVING ACCOUNT");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public double deposit(long accountNumber, double amount) {
        if (amount < 0) throw new IllegalArgumentException("INVALID AMOUNT");

        Transaction transaction = null;
        Session session = factory.openSession();

        try {
            transaction = session.beginTransaction();
            User acUser = session.get(User.class, accountNumber);
            if (acUser == null) throw new IllegalArgumentException("INVALID ACCOUNT NUMBER");

            double newBalance = acUser.getBalance() + amount;
            acUser.setBalance(newBalance);

            session.merge(acUser);
            transaction.commit();

            logTransaction(accountNumber, "DEPOSIT", amount, "Amount deposited");

            return newBalance;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("DEPOSIT FAILED: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public double withdraw(long accountNumber, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("INVALID AMOUNT");

        Transaction transaction = null;
        Session session = factory.openSession();

        try {
            transaction = session.beginTransaction();
            User acUser = session.get(User.class, accountNumber);

            if (acUser == null) throw new IllegalArgumentException("ACCOUNT NOT FOUND!!");
            if (acUser.getBalance() < amount) throw new IllegalArgumentException("INSUFFICIENT BALANCE");

            double newBalance = acUser.getBalance() - amount;
            acUser.setBalance(newBalance);

            session.merge(acUser);
            transaction.commit();

            logTransaction(accountNumber, "WITHDRAW", amount, "Amount withdrawn");

            return newBalance;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("WITHDRAW FAILED: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void moneyTransfer(long ac1, double amount, long ac2) {
        if (amount <= 0) throw new IllegalArgumentException("INVALID AMOUNT");

        Transaction transaction = null;
        Session session = factory.openSession();

        try {
            transaction = session.beginTransaction();
            User sender = session.find(User.class, ac1);
            User receiver = session.find(User.class, ac2);

            if (sender == null) throw new IllegalArgumentException("SENDER ACCOUNT NOT FOUND!!");
            if (receiver == null) throw new IllegalArgumentException("RECEIVER ACCOUNT NOT FOUND!!");
            if (sender.getBalance() < amount) throw new IllegalArgumentException("INSUFFICIENT BALANCE");

            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);

            session.merge(sender);
            session.merge(receiver);
            transaction.commit();

            logTransaction(ac1, "TRANSFER_SENT", amount, "Transferred to account " + ac2);
            logTransaction(ac2, "TRANSFER_RECEIVED", amount, "Received from account " + ac1);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("TRANSFER FAILED: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    public boolean calculateInterest(long AccountNumber) {
        Transaction transaction = null;
        Session session = factory.openSession();
        boolean success = false;

        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, AccountNumber);
            if (user == null) throw new IllegalArgumentException("ACCOUNT NOT FOUND");

            double interestAmount = (user.getBalance() * user.getInterestRate() / 100);
            double newBalance = user.getBalance() + interestAmount;
            user.setBalance(newBalance);

            session.merge(user);
            transaction.commit();
            success = true;

            logTransaction(AccountNumber, "INTEREST", interestAmount, "Interest applied to account");

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return success;
    }
}
