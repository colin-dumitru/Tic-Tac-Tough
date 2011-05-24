/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.normaluser;


import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tictac.test.Test;
import tictac.test.TestsService;
import tictac.user.User;

/**
 *
 * @author Administrator
 */
@Controller
public class NormalUserController {
    protected TestsService _testService;
    
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
        //this._testService.initializeTest(testId, session);
        
        /*intoarcem spre view-ul testului*/        
        return "/WEB-INF/normalusers/test.jsp"; //todo
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping(value = "advanceTest/{answer}",  method = RequestMethod.GET) 
    public @ResponseBody String advanceTest(HttpSession session, @PathVariable("answer") long answer){
        return "";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

       
    
}
