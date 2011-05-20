/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.statistics;

import tictac.user.UserDao;

/**
 *
 * @author colin
 */
public interface UserStatisticStrategy {
    public void update  (UserDao userDao);
}
