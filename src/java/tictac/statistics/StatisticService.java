/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.statistics;

import tictac.test.TestDao;
import tictac.user.UserDao;

/**
 *
 * @author colin
 */
public class StatisticService {
    protected UserDao _userDao;    
    protected TestDao _testDao;    
    protected UserStatisticStrategy _userStrategy;
    protected TestStaticticStrategy _testStrategy;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void update() {
         this.updateUserTop();
         this.updateTestTop();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void updateUserTop() {        
        this.setUserStrategy(new TopUserStrategy());
        this.compute();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    private void updateTestTop() {
        this.setTestStrategy(new TopTestStrategy());
        this.compute();    
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void setUserStrategy(UserStatisticStrategy strategy) {
        this._userStrategy = strategy;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setTestStrategy(TestStaticticStrategy _testStrategy) {
        this._testStrategy = _testStrategy;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void compute() {
        if(this._userStrategy != null) {
            this._userStrategy.update(_userDao);
        }
        if(this._testStrategy != null) {
            this._testStrategy.update(_testDao);
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
    
    
    
}
