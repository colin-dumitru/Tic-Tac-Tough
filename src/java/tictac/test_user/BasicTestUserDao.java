/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.test_user;

import java.util.List;
import org.hibernate.classic.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import tictac.test.Test;
import tictac.user.TransactionError;

/**
 *
 * @author bkt
 */
public class BasicTestUserDao implements TestUserDao {

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
    public void saveTestUser(TestUser test_user) throws TransactionError {
        if (this._session == null || !this._session.isOpen()) {
            throw new TransactionError("Session not opened");
        }

        this._session.beginTransaction();

        this._session.saveOrUpdate(test_user);

        this._session.getTransaction().commit();
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("unchecked")
    public List<TestUser> listRecordsWithTestId(long testId) throws TransactionError {
        if (this._session == null || !this._session.isOpen()) {
            throw new TransactionError("Session not opened");
        }

        return (List<TestUser>) this._session.createCriteria(TestUser.class)
                .add(Restrictions.eq("_testId", testId)).list();
           
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    
    @Override
    public void deleteTestUser(TestUser test_user) throws TransactionError {
        if (this._session == null || !this._session.isOpen()) {
            throw new TransactionError("Session not opened");
        }
        
        this._session.beginTransaction();
        this._session.delete(test_user);
        this._session.getTransaction().commit();
    }

}
