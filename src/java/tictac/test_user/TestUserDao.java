/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.test_user;

import java.util.List;
import tictac.test.Test;
import tictac.user.TransactionError;

/**
 *
 * @author bkt
 */
public interface TestUserDao {
    public void saveTestUser(TestUser testUser) throws TransactionError;
    public void deleteTestUser(TestUser testUser) throws TransactionError;
    
    public List<TestUser> listRecordsWithTestId(long testId) throws TransactionError;    
}