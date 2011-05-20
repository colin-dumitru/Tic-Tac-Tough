/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.test_question;

import java.util.List;
import tictac.question.Question;
import tictac.test.Test;
import tictac.user.TransactionError;

/**
 *
 * @author bkt
 */
public interface TestQuestionDao {
    public void saveTestQuestion(TestQuestion question) throws TransactionError;
    public void deleteTestQuestion(TestQuestion question) throws TransactionError;
    
    public List<TestQuestion> listQuestionsWithTestId(long testId) throws TransactionError;
    public List<TestQuestion> listQuestionsWithLink(long testId, long questionId) throws TransactionError;
}