/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import tictac.core.AplicationParams;
import tictac.user.UserService;
import tictac.test.TestDao;

/**
 *
 * @author Administrator
 */
@Service
public class TestsService {
    protected static final String LOG_PREFIX = "[SUPER USER SERVICE]";
    
    protected AplicationParams _params;
    protected TestDao _testDao;
    
    protected DateFormat _df;    
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public TestsService() {
        this._df = new SimpleDateFormat("yyyy.mm.dd hh:mm:ss ");        
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
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void logg(String what) {       
        
        if(this._params.logger() != null) {            
            
            try {
                this._params.logger().write(TestsService.LOG_PREFIX + " [" +
                        this._df.format(new Date()) + "] " + what);
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, what);            
        }
    }

    
    
}
