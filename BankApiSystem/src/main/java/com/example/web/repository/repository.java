package com.example.web.repository;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.web.user.User;

@Repository
public class repository {
	
	@Autowired
	private SessionFactory factory;
	
	 
	public void insert(User user) {
		Transaction transaction=null;
		Session session=factory.openSession();
		
		
			transaction=session.beginTransaction();
			
			session.persist(user);
			transaction.commit();
		
		
			// TODO: handle exception
			transaction.rollback();
		
		
			session.close();
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
		    
		    try {
		        transaction = session.beginTransaction();
		        
		        // Retrieve the user by ID (assuming user.getId() gets the user's ID)
		        User userToUpdate = session.get(User.class, user.getAccountNumber());  // Use the user's ID
		        
		       
		            
		            // Merge the updated user object
		            session.update(userToUpdate);
		            
		            transaction.commit();  // Commit the transaction
		        
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

	

}
