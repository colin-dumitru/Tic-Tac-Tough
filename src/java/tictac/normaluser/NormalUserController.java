/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.normaluser;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tictac.category.Category;
import tictac.category.CategoryDao;
import tictac.test.Test;
import tictac.test.TestsService;
import tictac.user.TransactionError;
import tictac.user.User;

/**
 *
 * @author Administrator
 */
@Controller
public class NormalUserController {
    protected TestsService _testService;
    protected CategoryDao _categoryDao;
    
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    public TestsService getTestService() {
        return _testService;
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    public void setTestService(TestsService _testService) {
        this._testService = _testService;
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping(value = {"/home"})
    public String home(HttpSession session, Model model ) {
        User user = (User) session.getAttribute("user");
        
        if(user == null)
            return "/web/login";
        
        /*adaugam si atributul user sa fie folosit de pagina index*/
        model.addAttribute("user", user);
                
        return "/WEB-INF/index.jsp";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping(value = "/error") 
    public String error(){
        return "/WEB-INF/error.jsp";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping("takeTest")
    public String goToTest(HttpSession session, Model model){
         User user = (User) session.getAttribute("user");

        /*verificam daca este logat*/
        if (user == null) {
            /*il redirectionam catre pagina principala*/
            return "/web/home";
        }
        
        /*adaugam si lista de categorii cand face filtrarea*/
         List<Category> categories = null;
        try {
            /* luam lista de categorii si o trimitem la view*/
            categories = this._categoryDao.listCategories();
        } catch (TransactionError ex) {
            Logger.getLogger(NormalUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("categories", categories);
        
        return "/WEB-INF/normalusers/listTests.jsp";
    }
    //----------------------------------------------------------------------------------------------    
    //---------------------------------------------------------------------------------------------- 
    @RequestMapping("goToTest/{testId}")
    public String goToTest(HttpSession session, Model model, @PathVariable("testId") long testId){
         User user = (User) session.getAttribute("user");

        /*verificam daca este logat*/
        if (user == null) {
            /*il redirectionam catre pagina principala*/
            return "/web/home";
        }
        
        /*cream testul*/
        this._testService.initializeTest(testId, session);
        this._testService.incrementTestAccessed(testId);
        /*intoarcem spre view-ul testului*/        
        return "/WEB-INF/normalusers/test.jsp"; //todo
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping(value = "advanceTest/{answer}",  method = RequestMethod.GET) 
    public @ResponseBody String advanceTest(HttpSession session, @PathVariable("answer") long answer){
        User user = (User) session.getAttribute("user");
        
        if(user == null)
            return null;
        
        return this._testService.advanceTest(session, answer).asXML();
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping(value = "/top")
    public String top(HttpSession session){
        User user = (User) session.getAttribute("user");
        
        if(user == null)
            return null;
        
        return "/WEB-INF/normalusers/top.jsp";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    public CategoryDao getCategoryDao() {
        return _categoryDao;
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    public void setCategoryDao(CategoryDao _categoryDao) {
        this._categoryDao = _categoryDao;
    }
       
    
}
