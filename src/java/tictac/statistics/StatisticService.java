/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.statistics;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tictac.core.AplicationParams;
import tictac.user.UserDao;

/**
 *
 * @author colin
 */
public class StatisticService {
    protected UserDao _userDao;    
    protected UserStatisticStrategy _userStrategy;
    protected ResourceLoader _resourceLoader;
    protected AplicationParams _params;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public StatisticService() {
        this._resourceLoader = new DefaultResourceLoader();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void update() {
         this.updateUserTop();       
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void updateUserTop() {
        UserStatisticStrategy strategy = new TopUserStrategy();
        
        strategy.setParams(this._params);
        
        this.setUserStrategy(strategy);
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
    
    
}
