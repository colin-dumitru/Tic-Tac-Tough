/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.normaluser;


import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tictac.user.User;

/**
 *
 * @author Administrator
 */
@Controller
public class NormalUserController {
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
        return null; //todo
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
       
    
}
