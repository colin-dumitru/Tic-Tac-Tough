/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.search;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tictac.question.Question;
import tictac.question.QuestionDao;
import tictac.user.TransactionError;

/**
 *
 * @author colin
 */
@Controller
public class QuestionSearchController {
    protected QuestionDao _questionDao;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @RequestMapping(value = "/searchQuestion", method = RequestMethod.GET)
    public @ResponseBody
    String getTime(@RequestParam String querry) {
        Element root = new DOMElement("result");
        
        List<Question> result = null;        
        
        try {
            result = this._questionDao.findQuestionWithContent(querry);
        } catch (TransactionError ex) {
            Logger.getLogger(QuestionSearchController.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        
        for(Question question : result) {
            root.add(question.toXML());
        }
        
        return root.asXML();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public QuestionDao getQuestionDao() {
        return _questionDao;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setQuestionDao(QuestionDao _questionDao) {
        this._questionDao = _questionDao;
    }
}
