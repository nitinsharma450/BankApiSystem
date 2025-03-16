package com.example.web.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.web.transaction.Transaction; // Import your transaction entity

@Repository
public class TransactionRepository {
    
    @Autowired
    private SessionFactory factory;
    
    public void saveTransaction(com.example.web.transaction.Transaction transaction) {
        Session session = factory.openSession();
        org.hibernate.Transaction hibernateTransaction = null;
        
        try {
            hibernateTransaction = session.beginTransaction();
            session.persist(transaction);
            hibernateTransaction.commit();
        } catch (Exception e) {
            if (hibernateTransaction != null) {
                hibernateTransaction.rollback();
            }
            System.out.println("SOMETHING WENT WRONG SAVING TRANSACTION");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public List<com.example.web.transaction.Transaction> getTransactionsForAccount(Long accountNumber) {
        Session session = factory.openSession();
        List<com.example.web.transaction.Transaction> transactions = new ArrayList<>();
        
        try {
            Query<com.example.web.transaction.Transaction> query = session.createQuery(
                "from Transaction where accountNumber = :accountNumber order by transactionDate desc", 
                com.example.web.transaction.Transaction.class);
            query.setParameter("accountNumber", accountNumber);
            transactions = query.getResultList();
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG RETRIEVING TRANSACTIONS");
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return transactions;
    }
}