/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.statistics;

import org.springframework.scheduling.quartz.QuartzJobBean;
import tictac.user.UserDao;

/**
 *
 * @author colin
 */
public class StatisticService {
    protected UserDao _userDao;    
    protected UserStatisticStrategy _userStrategy;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void update() {
         this.updateUserTop();       
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void updateUserTop() {
        this.setUserStrategy(new TopUserStrategy());
        this.compute();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void setUserStrategy(UserStatisticStrategy strategy) {
        this._userStrategy = strategy;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void compute() {
        if(this._userStrategy != null) {
            this._userStrategy.update(_userDao);
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public UserDao getUserDao() {
        return _userDao;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setUserDao(UserDao _userDao) {
        this._userDao = _userDao;
    }
    
}
