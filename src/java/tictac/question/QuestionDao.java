/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.question;

import java.util.List;
import tictac.user.TransactionError;

/**
 *
 * @author bkt
 */
public interface QuestionDao {
    public void saveQuestion(Question question) throws TransactionError;
    public void deleteQuestion(Question question)  throws TransactionError;
    
    public List<Question> listQuestions() throws TransactionError;    
    
    
    public List<Question> findQuestionWithUserId(long userId) throws TransactionError ;
    public List<Question> findQuestionWithId(long id) throws TransactionError;
    public List<Question> findQuestionWithContent(String content) throws TransactionError;
    
}
