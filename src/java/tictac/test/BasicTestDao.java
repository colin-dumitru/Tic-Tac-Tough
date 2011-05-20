/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.test;

import java.util.List;
import org.hibernate.classic.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import tictac.user.TransactionError;

/**
 *
 * @author bkt
 */
@Repository("testDao")
public class BasicTestDao implements TestDao{
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
    public Test saveTest(Test test) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        this._session.beginTransaction();
        
        this._session.saveOrUpdate(test);
        
        this._session.getTransaction().commit(); 
        return test;
    }
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("unchecked")
    public List<Test> listTests() throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
       
        return this._session.createCriteria(Test.class).list();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Override
    public List<Test> findTestWithUSerId(long userId) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        /*intoarcem lista de teste create de utilizatorul cu id-ul respectiv*/
        return (List<Test>) this._session.createCriteria(Test.class)
                .add(Restrictions.eq("_authorid", userId)).list();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public List<Test> findTestWithId(long testId) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        /*intoarcem lista de teste create de utilizatorul cu id-ul respectiv*/
        return (List<Test>) this._session.createCriteria(Test.class)
                .add(Restrictions.eq("_id", testId)).list();
    }
}
