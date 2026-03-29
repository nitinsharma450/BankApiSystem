package com.example.web.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.web.user.TransactionHistory;

@Repository
public class TransactionRepository {

    @Autowired
    private SessionFactory factory;

    public void saveTransaction(TransactionHistory transaction) {
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

    public List<TransactionHistory> getTransactionsForAccount(Long accountNumber) {
        Session session = factory.openSession();
        List<TransactionHistory> transactions = new ArrayList<>();

        try {
            Query<TransactionHistory> query = session.createQuery(
                "from TransactionHistory where accountNumber = :accountNumber order by timestamp desc", 
                TransactionHistory.class);
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
