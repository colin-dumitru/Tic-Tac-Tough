/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import tictac.core.AplicationParams;
import tictac.question.Question;
import tictac.question.QuestionDao;
import tictac.test_question.TestQuestion;
import tictac.user.TransactionError;
import tictac.user.UserService;
import tictac.test.TestDao;
import tictac.test_question.TestQuestionDao;

/**
 *
 * @author Administrator
 */
@Service
public class TestsService {
    protected static final String LOG_PREFIX = "[SUPER USER SERVICE]";
    
    protected AplicationParams _params;
    protected TestDao _testDao;
    protected QuestionDao _questionDao;
    protected TestQuestionDao _testQuestionDao;
    
    protected DateFormat _df;    
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public TestsService() {
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
    public List<Question> createTest(Test test) {
        List<Question> questionList = new ArrayList<Question> ();
        
       List<TestQuestion> linkedQuestions = null;
       
        /*luam lista de legatura de intrebari din etstul respectiv*/
        try {
           
         linkedQuestions = this._testQuestionDao.listQuestionsWithTestId(test.getId());
        } catch (TransactionError ex) {
            Logger.getLogger(TestsService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        /*luam lista de intrebari din testul respectiv*/
        List<Question> questions = new ArrayList<Question>();
        
        for(TestQuestion tq : linkedQuestions) {
            try {
                questions.addAll(this._questionDao.findQuestionWithId(tq.getQuestionId()));
            } catch (TransactionError ex) {
                Logger.getLogger(TestsService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /*verificam cate intrebari sunt in test si daca trebuie folosite toate*/
        /*iustina nu intelege asta*/
        if(test.getUseAllQuestions() == Test.RESTRAIN_QUESTIONS){
            /*folost pentru a scoate o intrebare aleatoare din lista*/
            Random random = new Random(System.currentTimeMillis());
            
            /*cat timp lista de intrebari este mai mare decat cea prescris
             scoatem cate o intrebare aleatoare*/
            while(questions.size() > test.getNumq())
                questions.remove(random.nextInt(questions.size()));
        }
        
        return questionList;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public QuestionDao getQuestionDao() {
        return _questionDao;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setQuestionDao(QuestionDao _questionDao) {
        this._questionDao = _questionDao;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public TestDao getTestDao() {
        return _testDao;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setTestDao(TestDao _testDao) {
        this._testDao = _testDao;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public TestQuestionDao getTestQuestionDao() {
        return _testQuestionDao;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setTestQuestionDao(TestQuestionDao _testQuestionDao) {
        this._testQuestionDao = _testQuestionDao;
    }
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void logg(String what) {       
        
        if(this._params.logger() != null) {            
            
            try {
                this._params.logger().write(TestsService.LOG_PREFIX + " [" +
                        this._df.format(new Date()) + "] " + what);
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, what);            
        }
    }

    
    
}
