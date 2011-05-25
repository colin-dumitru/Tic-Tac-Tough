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
import tictac.test.Test;
import tictac.test.TestDao;
import tictac.user.TransactionError;

/**
 *
 * @author bkt
 */
@Controller
public class TestSearchController {
    protected TestDao _testDao;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @RequestMapping(value = "/searchTest", method = RequestMethod.GET)
    public @ResponseBody
    String getTime(@RequestParam String querry, @RequestParam Long categoryId) {
        Element root = new DOMElement("result");
        
        List<Test> result = null;        
        
        try {
            result = this._testDao.findTestWithName(querry);
        } catch (TransactionError ex) {
            Logger.getLogger(QuestionSearchController.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        
        for(Test test : result) {
            /*verificam si daca testul are categoria care trebuie*/
            if(test.getCategoryid().equals(categoryId))
                 root.add(test.toXML());
        }
        
        return root.asXML();
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
