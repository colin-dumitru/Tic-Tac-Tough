/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.user;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    protected Long _userId;
    
    @Column(name = "name")
    protected String _name;    
    
    @Column(name = "userName")
    protected String _userName;
    
    @Column(name = "password")
    protected String _password;
    
    @Column(name = "email")
    protected String _email;
    
    @Column(name = "score")
    protected int _testScore;
    
    @Column(name = "type")
    protected int _type;
    
    public static final int UNAME_MIN_LENGTH = 6;
    public static final int UNAME_MAX_LENGTH = 50;
    
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 50;
    
    public static final int EMAIL_MAX_LENGTH = 50;
    
    public static final int NAME_MAX_LENGTH = 50;
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public static final int NORMAL_USER = 0;
    public static final int EDITOR_USER = 1;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public User() {
        this._name = "";
        this._userName = "";
        this._password = "";
        this._testScore = 0;
        this._type = User.NORMAL_USER;
    }      
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------  

    public String getEmail() {
        return _email;
    }    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setEmail(String _email) {
        this._email = _email;
    }    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public String getName() {
        return _name;
    }    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setName(String _name) {
        this._name = _name;
    }    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public String getPassword() {
        return _password;
    }    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setPassword(String _password) {
        this._password = _password;
    }        
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public int getTestScore() {
        return _testScore;
    }
        
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setTestScore(int _testScore) {
        this._testScore = _testScore;
    }
        
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public String getUserName() {
        return _userName;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setUserName(String _userName) {
        this._userName = _userName;
    }    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public int getType() {
        return _type;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setType(int _type) {
        this._type = _type;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public Long getUserId() {
        return _userId;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setUserId(Long _userId) {
        this._userId = _userId;
    }
    
    
}
