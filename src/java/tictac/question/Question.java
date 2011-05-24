/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.question;

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
@Table(name = "questions")
public class Question implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    protected Long _id;
    @Column(name = "content")
    protected String _content;
    @Column(name = "categoryid")
    protected Long _categoryId;
    @Column(name = "authorid")
    protected Long _authorId;
    @Column(name = "answer1")
    protected String _answer1;
    @Column(name = "answer2")
    protected String _answer2;
    @Column(name = "answer3")
    protected String _answer3;
    @Column(name = "answer4")
    protected String _answer4;
    
    @NumberFormat
    @Column(name = "correctanswer")
    protected Long _correctAnswer;
    
    @NumberFormat
    @Column(name = "difficulty")
    protected Long _difficulty;
    
    public static final int D_HIGH = 2;
    public static final int D_MEDIUM = 1;
    public static final int D_EASY = 0;
    
    public static final int TAG_MIN_LENGTH = 3;
    public static final int TAG_MAX_LENGTH = 50;
    public static final int CONTENT_MIN_LENGTH = 6;
    public static final int CONTENT_MAX_LENGTH = 32700;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------  
    public Question() {
        this._authorId = (long) 0;
        this._categoryId = (long) 0;
        this._content = "";
        this._id = (long) 0;
        this._difficulty = (long) 0;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public Long getAuthorId() {
        return _authorId;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setAuthorId(Long _authorId) {
        this._authorId = _authorId;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public Long getCategoryId() {
        return _categoryId;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setCategoryId(Long _categoryId) {
        this._categoryId = _categoryId;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public String getContent() {
        return _content;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setContent(String _content) {
        this._content = _content;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public Long getId() {
        return _id;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setId(Long _id) {
        this._id = _id;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public String getAnswer1() {
        return _answer1;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setAnswer1(String _answer1) {
        this._answer1 = _answer1;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public String getAnswer2() {
        return _answer2;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setAnswer2(String _answer2) {
        this._answer2 = _answer2;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public String getAnswer3() {
        return _answer3;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setAnswer3(String _answer3) {
        this._answer3 = _answer3;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public String getAnswer4() {
        return _answer4;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setAnswer4(String _answer4) {
        this._answer4 = _answer4;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public Long getCorrectAnswer() {
        return _correctAnswer;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setCorrectAnswer(Long _correctAnswer) {
        this._correctAnswer = _correctAnswer;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public Element toXML() {
        Element root  = new DOMElement("question");
        
        /*adaugam elementele in xml*/
        Element id = new DOMElement("id"); root.add(id);
        id.addAttribute("value", this._id.toString());
        
        Element content = new DOMElement("content"); root.add(content);
        content.addAttribute("value", this._content);
        
        Element answer1 = new DOMElement("answer1"); root.add(answer1);
        answer1.addAttribute("value", this._answer1);
        
        Element answer2 = new DOMElement("answer2"); root.add(answer2);
        answer2.addAttribute("value", this._answer2);
        
        Element answer3 = new DOMElement("answer3"); root.add(answer3);
        answer3.addAttribute("value", this._answer3);
        
        Element answer4 = new DOMElement("answer4"); root.add(answer4);
        answer4.addAttribute("value", this._answer4);
        
        Element correctAnswer = new DOMElement("correctAnswer"); root.add(correctAnswer);
        correctAnswer.addAttribute("value", this._correctAnswer.toString());
        
        Element author = new DOMElement("authorId"); root.add(author);
        author.addAttribute("value", this._authorId.toString());
        
        Element category = new DOMElement("categoryId"); root.add(category);
        category.addAttribute("value", this._authorId.toString());
        
        Element difficulty = new DOMElement("difficulty"); root.add(difficulty);
        difficulty.addAttribute("value", this._difficulty.toString());
        
        return root;
        
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public Long getDifficulty() {
        return _difficulty;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setDifficulty(Long _difficulty) {
        this._difficulty = _difficulty;
    }
    
}
