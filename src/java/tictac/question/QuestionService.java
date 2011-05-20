/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.question;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tictac.core.AplicationParams;

/**
 *
 * @author bkt
 */
public class QuestionService {
    protected static final String LOG_PREFIX = "[REGISTER SERVICE]";
    
    protected AplicationParams _params;
    protected QuestionDao _questionDao;
    
    DateFormat _df;    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------    
    public void RegisterService() {        
        this._df = new SimpleDateFormat("yyyy.mm.dd hh:mm:ss ");
    }    
    //----------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------- 
    public AplicationParams getParams() {
        return _params;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------  
    public void setParams(AplicationParams _params) {
        this._params = _params;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------    

    public QuestionDao getQuestionDao() {
        return _questionDao;
    }
    //----------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------- 

    public void setQuestionDao(QuestionDao _qDao) {
        this._questionDao = _qDao;
    }  
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------   
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean insertQuestion(Question question) throws InvalidQuestionException {
        //verify inputs
        return true;
    }
    
}
