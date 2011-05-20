/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tictac.user;

import javax.servlet.http.HttpSession;
import tictac.user.InvalidUserException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tictac.user.User;

/** 
 *
 * @author Administrator
 */
@Controller
public class UserController {
    protected UserService _userService;

   
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    public UserController() {
    }    
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
     public UserService getUserService() {
        return _userService;
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    public void setUserService(UserService _userService) {
        this._userService = _userService;
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping(value = "/register")
    public String registerForm(Model model) {
        model.addAttribute(new User());
        return "/WEB-INF/users/register.jsp";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping(value = "/registerUser")
    public String registerUser(@ModelAttribute User user, Model model, HttpSession session) {
        System.out.println("Registering user : " + user.getUserName());  
        
        try{
            /*incercam sa inregistram userul*/
            this._userService.registerUser(user);
            
            /*daca reuseste adaugam informatiile in sesiune*/
            session.setAttribute("user", user);
        }
        catch(InvalidUserException ex) {
            /*daca nu reuseste afiseaza eroarea in aceiasi pagina */
            model.addAttribute("registerError", ex.getMessage());
            return "/WEB-INF/users/register.jsp";           
        }
        
        return "/web/home";
    }       
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping(value = "/login")
    public String loginForm(Model model) {
        model.addAttribute(new User());
        return "/WEB-INF/users/login.jsp";   
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping(value = "/loginUser")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
        /*incercam sa inregistram userul*/
        User result = this._userService.loginUser(user);
        
        /*daca nu reuseste afisam eroarea in aceiasi pagina*/
        if(result == null) {
            model.addAttribute("loginError", "Invalid user or password specified");
            return "/WEB-INF/users/login.jsp";            
        }       
        /*daca reuseste adaugam atributul user in sesiune*/
        else {
            session.setAttribute("user", result);
        }
        
        return "/web/home";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    

}