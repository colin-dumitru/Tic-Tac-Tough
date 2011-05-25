/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.statistics;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tictac.core.AplicationParams;
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
    protected ResourceLoader _resourceLoader;
    protected AplicationParams _params;
    protected TestStaticticStrategy _testStrategy;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public StatisticService() {
        this._resourceLoader = new DefaultResourceLoader();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void update() {
         this.updateUserTop();
         this.updateTestTop();  
         
         this.compute();
         
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void updateUserTop() {
        UserStatisticStrategy strategy = new TopUserStrategy();        
        strategy.setParams(this._params);
        
        this.setUserStrategy(strategy);       
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    private void updateTestTop() {
        TestStaticticStrategy strategy = new TopTestStrategy();        
        strategy.setParams(this._params);
        
        this.setTestStrategy(strategy);
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
    public TestDao getTestDao() {
        return _testDao;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setTestDao(TestDao _testDao) {
        this._testDao = _testDao;
    }
    
    
}
