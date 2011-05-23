/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.test;

import java.util.List;
import tictac.question.Question;
import tictac.user.TransactionError;

/**
 *
 * @author bkt
 */
public interface TestDao {
    public Test saveTest(Test test) throws TransactionError;
    public List<Test> listTests() throws TransactionError;
    
    public List<Test> findTestWithId(long testId) throws TransactionError;
    public List<Test> findTestWithUSerId(long userId) throws TransactionError;

    public List<Test> findTestWithName(String querry) throws TransactionError;
    
}
