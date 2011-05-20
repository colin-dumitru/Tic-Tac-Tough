/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.user;

import java.util.List;

/**
 *
 * @author Administrator
 */
public interface UserDao {
    public void saveUser(User user) throws TransactionError;
    public List<User> listUsers() throws TransactionError;
    
    public List<User> listUsersByScore(boolean ascending) throws TransactionError;
    
    public User findUser(String userName) throws TransactionError;
    
}
