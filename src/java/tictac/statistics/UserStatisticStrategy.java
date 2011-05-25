/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.statistics;

import org.springframework.core.io.ResourceLoader;
import tictac.core.AplicationParams;
import tictac.user.UserDao;

/**
 *
 * @author colin
 */
public interface UserStatisticStrategy {
    public void setParams(AplicationParams params);
    public void update  (UserDao userDao);
}
