/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.statistics;

<<<<<<< HEAD
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tictac.core.AplicationParams;
=======
import tictac.test.TestDao;
>>>>>>> b7ff8524747e946977dbb92a45cc2739a45144f5
import tictac.user.UserDao;

/**
 *
 * @author colin
 */
public class StatisticService {
    protected UserDao _userDao;    
    protected TestDao _testDao;    
    protected UserStatisticStrategy _userStrategy;
<<<<<<< HEAD
    protected ResourceLoader _resourceLoader;
    protected AplicationParams _params;
=======
    protected TestStaticticStrategy _testStrategy;
>>>>>>> b7ff8524747e946977dbb92a45cc2739a45144f5
    
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
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
<<<<<<< HEAD
    protected void updateUserTop() {
        UserStatisticStrategy strategy = new TopUserStrategy();
        
        strategy.setParams(this._params);
        
        this.setUserStrategy(strategy);
=======
    protected void updateUserTop() {        
        this.setUserStrategy(new TopUserStrategy());
>>>>>>> b7ff8524747e946977dbb92a45cc2739a45144f5
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
<<<<<<< HEAD
    public AplicationParams getParams() {
        return _params;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setParams(AplicationParams _params) {
        this._params = _params;
    }
    
=======
    public TestDao getTestDao() {
        return _testDao;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setTestDao(TestDao _testDao) {
        this._testDao = _testDao;
    }
    
    
>>>>>>> b7ff8524747e946977dbb92a45cc2739a45144f5
    
}
