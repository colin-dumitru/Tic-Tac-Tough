/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.test;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author bkt
 */
@Entity
@Table(name = "tests")
public class Test implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    protected long _id;
    
    @Column(name = "name")
    protected String _name;
    
    @Column(name = "categoryid")
    @NumberFormat
    protected Long _categoryid;
    
    @Column(name = "qtime")
    protected int _time;
    
    @Column(name = "numq")
    protected int _numq;
    
    @Column(name = "difficulty")
    protected int _difficulty;
    
    @Column(name = "authorid")
    protected long _authorid;
    
    @Column(name = "useallq")
    protected int _useAllQuestions;
    
    @Column(name = "accessed")
    protected long _accessed;
    
    public static final int USE_ALL_QUESTIONS = 1;
    public static final int RESTRAIN_QUESTIONS = 0;
    
    public static final int STATIC_DIFFICULTY = 0;
    public static final int DYNAMIC_DIFFICULTY = 1;
    
    public static final int DIFFICULTY_THRESHOLD  = 3;
    
    public static final int HARD_SCORE = 3;   
    public static final int MEDIUM_SCORE = 2;
    public static final int EASY_SCORE = 1;
    
    
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public Test() {
        this._difficulty = 0;
        this._name = "";
        this._numq = 0;
        this._time = 0;
        this._categoryid = 0l;
        this._authorid = 0;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public long getAuthorid() {
        return _authorid;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setAuthorid(int _authorid) {
        this._authorid = _authorid;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public Long getCategoryid() {
        return _categoryid;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setCategoryid(Long _categoryid) {
        this._categoryid = _categoryid;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public int getDifficulty() {
        return _difficulty;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setDifficulty(int _difficulty) {
        this._difficulty = _difficulty;
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
    public int getNumq() {
        return _numq;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setNumq(int _numq) {
        this._numq = _numq;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public long getId() {
        return _id;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setId(int _id) {
        this._id = _id;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public int getTime() {
        return _time;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setTime(int _time) {
        this._time = _time;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public int getUseAllQuestions() {
        return _useAllQuestions;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setUseAllQuestions(int _useAllQuestions) {
        this._useAllQuestions = _useAllQuestions;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public long getAccessed() {
        return _accessed;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setAccessed(long _accessed) {
        this._accessed = _accessed;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public Element toXML() {
        Element root  = new DOMElement("test");
        
        /*adaugam elementele in xml*/
        Element id = new DOMElement("id"); root.add(id);
        id.addAttribute("value", new Long(this._id).toString());
        
        Element name = new DOMElement("name"); root.add(name);
        name.addAttribute("value", this._name);
        
        Element categoryid = new DOMElement("categoryid"); root.add(categoryid);
        categoryid.addAttribute("value", new Long(this._categoryid).toString());
        
        Element authorid = new DOMElement("authorid"); root.add(authorid);
        authorid.addAttribute("value", new Long(this._authorid).toString());
        
        Element difficulty = new DOMElement("difficulty"); root.add(difficulty);
        difficulty.addAttribute("value", new Integer(this._difficulty).toString());
        
        Element numq = new DOMElement("numq"); root.add(numq);
        numq.addAttribute("value", new Integer(this._numq).toString());
        
        Element time = new DOMElement("time"); root.add(time);
        time.addAttribute("value", new Integer(this._time).toString());
        
        Element useAllQuestions = new DOMElement("useAllQuestions"); root.add(useAllQuestions);
        useAllQuestions.addAttribute("value", new Integer(this._useAllQuestions).toString());
        
        return root;
        
    }
    
}
