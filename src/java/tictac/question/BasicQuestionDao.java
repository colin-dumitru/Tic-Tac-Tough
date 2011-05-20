/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.question;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import tictac.user.TransactionError;

/**
 *
 * @author bkt
 */
public class BasicQuestionDao implements QuestionDao{
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
    public void saveQuestion(Question question) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        this._session.beginTransaction();
        
        this._session.saveOrUpdate(question);
        
        this._session.getTransaction().commit();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    @SuppressWarnings("unchecked")
    public List<Question> listQuestions() throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
       
        List<Question> ret = (List<Question>)this._session.createCriteria(Question.class).list();

        return ret;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public List<Question> findQuestionWithUserId(long userId) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        /*intoarcem lista de teste create de utilizatorul cu id-ul respectiv*/
        return (List<Question>) this._session.createCriteria(Question.class)
                .add(Restrictions.eq("_authorId", userId)).list();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public List<Question> findQuestionWithId(long id) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        /*intoarcem lista de teste create de utilizatorul cu id-ul respectiv*/
        return (List<Question>) this._session.createCriteria(Question.class)
                .add(Restrictions.eq("_id", id)).list();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Override
    public void deleteQuestion(Question question) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        this._session.beginTransaction();
        this._session.delete(question);
        this._session.getTransaction().commit();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public List<Question> findQuestionWithContent(String content) throws TransactionError {
        if(this._session == null || !this._session.isOpen())
            throw new TransactionError("Session not opened");
        
        List<Question> result = new ArrayList<Question>();
        
        /*impartim criteria pe cuvinte*/
        String[] split = content.split(" ");
        /*luam lista completa de interabri*/
        List<Question> listQuestions = this.listQuestions();
        
        /*daca contine toate cuvintele din criteria*/
        boolean valid = true;
        
        /*iteram prin intrebari si ferificam care contin cuvintele cautate*/
        for(Question question : listQuestions) {
            valid = true;
            
            for(String word : split)
                if(!question.getContent().contains(word)) {
                    valid = false;
                    break;
                }
            
            if(valid)
                result.add(question);
        }
        
        return result;
    }
}
