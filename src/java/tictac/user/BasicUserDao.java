/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.user;

import java.sql.Connection;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Repository("userDao")
public class BasicUserDao implements UserDao{
    private SessionFactory _sessionFactory;
    private Session _session;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    
    public SessionFactory getSessionFactory() {
        return _sessionFactory;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    
    public void setSessionFactory(SessionFactory _sessionFactory) {
        this._sessionFactory = _sessionFactory;
        
        this._session = this._sessionFactory.openSession();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public void saveUser(User user) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        this._session.beginTransaction();
        
        this._session.saveOrUpdate(user);
        
        this._session.getTransaction().commit();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public User findUser(String userName) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        //this._session.beginTransaction();
        List list = this._session.createCriteria(User.class)
                .add(Restrictions.eq("_userName", userName)).list();
        
        /*nu are trebuie sa fie niciodata 2 utilizatori cu acelasi username*/
        if(list.size() != 1)
            return null;
        
        return (User) list.get(0);                
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public User findUser(long id) throws TransactionError{
          if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        //this._session.beginTransaction();
        List list = this._session.createCriteria(User.class)
                .add(Restrictions.eq("_userId", id)).list();
        
        /*nu are trebuie sa fie niciodata 2 utilizatori cu acelasi username*/
        if(list.size() != 1)
            return null;
        
        return (User) list.get(0);            
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
       
        List<User> ret = (List<User>)this._session.createCriteria(User.class).list();

        return ret;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Override
    public List<User> listUsersByScore(boolean ascending) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
       
         List<User> ret = null;
                 
        if(ascending) {
            ret = (List<User>)this._session.createCriteria(User.class).addOrder(Order.asc("_testScore")).list();
        } else {
            ret = (List<User>)this._session.createCriteria(User.class).addOrder(Order.desc("_testScore")).list();
        }

        return ret;
    }
    
}
