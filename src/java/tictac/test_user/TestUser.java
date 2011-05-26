/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.test_user;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author bkt
 */
@Entity
@Table(name = "test_user")
public class TestUser implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    protected Long _id;
    @Column(name = "testid")
    protected Long _testId;
    @Column(name = "userid")
    protected Long _userId;
    @Column(name = "score")
    protected Long _score;
    @Column(name = "date")
    protected String _date;

    public TestUser() {
        this._testId = (long) 0;
        this._userId = (long) 0;
        Calendar cal = Calendar.getInstance();
        this._date = cal.getTime().toString();
        this._score = (long) 0;
    }

    public TestUser(Long _testid, Long _userId) {
        this._testId = _testid;
        this._userId = _userId;
        Calendar cal = Calendar.getInstance();
        this._date = cal.getTime().toString();
        this._score = (long) 0;
    }
    
    public TestUser(Long _testid, Long _userId, long _score) {
        this._testId = _testid;
        this._userId = _userId;
        Calendar cal = Calendar.getInstance();
        this._date = cal.getTime().toString();
        this._score = new Long(_score);
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public Long getTestId() {
        return _testId;
    }

    public void setTestId(Long _testid) {
        this._testId = _testid;
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String _date) {
        this._date = _date;
    }

    public Long getScore() {
        return _score;
    }

    public void setScore(Long _score) {
        this._score = _score;
    }

    public Long getUserId() {
        return _userId;
    }

    public void setUserId(Long _userId) {
        this._userId = _userId;
    }
    
    
}
