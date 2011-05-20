/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.category;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import tictac.user.TransactionError;

/**
 *
 * @author bkt
 */
@Repository("categoryDao")
public class BasicCategoryDao implements CategoryDao{

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
    public void saveCategories(Category cat) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        this._session.beginTransaction();
        
        this._session.saveOrUpdate(cat);
        
        this._session.getTransaction().commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Category> listCategories() throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
       
        List<Category> list=this._session.createCriteria(Category.class).list();
        return list;
    }

    @Override
    public Category findCategory(String categoryName) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        //this._session.beginTransaction();
        List list = this._session.createCriteria(Category.class)
                .add(Restrictions.eq("_name", categoryName)).list();
        
        if(list.size() != 1)
            return null;
        
        return (Category) list.get(0);
    }

    
}
