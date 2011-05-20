/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tictac.user;

import tictac.user.InvalidUserException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tictac.core.AplicationParams;
import tictac.user.TransactionError;
import tictac.user.User;
import tictac.user.UserDao;

/**
 *
 * @author Administrator
 */
@Service("userService")
@Transactional
public class UserService {
    protected static final String LOG_PREFIX = "[REGISTER SERVICE]";
    
    protected AplicationParams _params;
    protected UserDao _userDao;
    
    DateFormat _df;    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------    
    public void RegisterService() {        
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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public boolean registerUser(User user) throws InvalidUserException {
        //this.logg("regeresting user " + user.getName());
        
        /*verificam daca usernameul si parola indeplinesc conditile*/
        if(user.getUserName().length() < User.UNAME_MIN_LENGTH ) {
            throw new InvalidUserException("Username is too short");
        } else if(user.getUserName().length() >= User.UNAME_MAX_LENGTH ) {
            throw new InvalidUserException("Username is too long");
        } else if(user.getPassword().length() < User.PASSWORD_MIN_LENGTH ) {
            throw new InvalidUserException("Password is too short");
        } else if(user.getPassword().length() >= User.PASSWORD_MAX_LENGTH ) {
            throw new InvalidUserException("Username is too short");
        } else if(user.getEmail().length() >= User.EMAIL_MAX_LENGTH ) {
            throw new InvalidUserException("Email is too short");
        } else if(user.getName().length() >= User.NAME_MAX_LENGTH ) {
            throw new InvalidUserException("Name is too short");
        }
        
        /*verificam daca usernamul si email-ul este unic*/        
        try{
            for(User iter : this._userDao.listUsers())
                if(user.getUserName().equals(iter.getUserName()))
                    throw new InvalidUserException("Username already exists");
                else if(user.getEmail().equals(iter.getEmail()))
                    throw new InvalidUserException("Email already in use");
        } catch(TransactionError err) {
            this.logg(err.getMessage());                    
        }
        
        /*daca indeplineste toate conditiile il adaugam in baza de date*/
        try{
            this._userDao.saveUser(user); 
        } catch(TransactionError err) {
            this.logg(err.getMessage());
        }
        
        return true;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public User loginUser(User user) {
        User result = null;
        
        /*luam utilizatorul cu acelasi usernam ca si cel furnizat*/
        try {
            result = this._userDao.findUser(user.getUserName());
        } catch (TransactionError ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(result == null) { /*daca se gasetse un utilizator*/
            return null;            
        } else {
            /*verificam si daca parolele coincid*/
            if(result.getPassword().trim().equals(user.getPassword().trim()))
                return result;
            else 
                return null;
        }
    }
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void logg(String what) {       
        
        if(this._params.logger() != null) {            
            
            try {
                this._params.logger().write(UserService.LOG_PREFIX + " [" +
                        this._df.format(new Date()) + "] " + what);
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, what);            
        }
    }

}
