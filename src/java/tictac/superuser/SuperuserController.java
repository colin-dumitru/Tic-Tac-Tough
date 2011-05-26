/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.superuser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tictac.test_user.TestUserDao;
import tictac.category.Category;
import tictac.category.CategoryDao;
import tictac.question.Question;
import tictac.question.QuestionDao;
import tictac.search.SearchQuery;
import tictac.test.Test;
import tictac.test.TestDao;
import tictac.test.TestsService;
import tictac.test_question.TestQuestion;
import tictac.test_question.TestQuestionDao;
import tictac.user.TransactionError;
import tictac.user.User;

/**
 *
 * @author Administrator
 */
@Controller
public class SuperuserController {

    protected TestsService _testService;
    protected TestDao _testDao;
    protected CategoryDao _categoryDao;
    protected QuestionDao _questionDao;
    protected TestQuestionDao _testQuestionDao;
    protected TestUserDao _testUserDao;

    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping("/tests")
    public String listTests(HttpSession session, Model model) {
        /*listam testele create si oefrim posibilitatea sa editeze unul din ele*/

        User user = (User) session.getAttribute("user");

        /*verificam daca este logat*/
        if (user == null) {
            /*il redirectionam catre pagina principala*/
            return "/web/home";
        }

        /*verificam daca are drepturile necsare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }
        try {
            /*luam testele efectuate de user*/
            List<Test> result = this._testDao.findTestWithUSerId(user.getUserId().longValue());

            /*daca nu a aefectuar teste trimitem o lista goala*/
            if (result == null) {
                model.addAttribute("testList", new LinkedList<Test>());
            } else {
                model.addAttribute("testList", result);
            }
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);

            model.addAttribute("error", "An error occured while fetching your test list");
        }

        /*adaugam si un test nou gol in caz ca vrea sa il adauge*/
        model.addAttribute("test", new Test());

        /*luam userul din sesiune*/
        return "/WEB-INF/superusers/tests.jsp";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    @RequestMapping("/questions")
    public String listQuestions(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            /*daca nu este logat il redirectionam catre pagina princiapala*/
            return "/web/home";
        }

        /*verificam daca are drepturile necesare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }

        /*luam lista de intrebari puse de utilizator*/
        try {
            List<Question> list = this._questionDao.findQuestionWithUserId(user.getUserId().longValue());

            /*adaugam lista de teste in model*/
            if (list != null) {
                model.addAttribute("questionList", list);
            } else {
                model.addAttribute("questionList", new LinkedList<Question>());
            }
        } catch (TransactionError ex) {
            model.addAttribute("error", "An error occured while fetching your question list");
            return "/WEB-INF/error.jsp";
        }

        /*adaugam lista de category pentru adaugare*/
        List<Category> categories = null;

        try {
            /* luam lista de categorii si o trimitem la view*/
            categories = this._categoryDao.listCategories();
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("categories", categories);

        /*adaugam o intrebare goala in model daca vrea sa adauge una goala*/
        model.addAttribute("question", new Question());
        return "/WEB-INF/superusers/questions.jsp";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    @RequestMapping(value = "/editTest/{testId}")
    public String editTest(@PathVariable("testId") long testId, HttpSession session,
            Model model) {
        /*verificam daca userul este logat*/
        User user = (User) session.getAttribute("user");

        if (user == null) {
            /*il redirectionam catre pagina principala*/
            return "/web/home";
        }

        /*verificam daca are drepturile necsare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }

        List<Test> list = null;

        try {
            /*luam testul cu id-ul respectiv*/
            list = this._testDao.findTestWithId(testId);
        } catch (TransactionError ex) {
            model.addAttribute("error", ex.toString());
            return "/web/error";
        }

        /*verificam daca testul exista si daca a fost creeat de utilizatorul nostru*/
        if (list.size() != 1) {
            model.addAttribute("error", "The test does not exist!");
            return "/web/error";
        }

        Test test = list.get(0);

        if (test.getAuthorid() != user.getUserId().longValue()) {
            model.addAttribute("error", "The test was not created by you!");
            return "/web/error";
        }

        /*daca totul este corect umplem forumul cu testul*/
        model.addAttribute("test", test);

        List<Category> categories = null;
        try {
            /* luam lista de categorii si o trimitem la view*/
            categories = this._categoryDao.listCategories();
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        model.addAttribute("categories", categories);

        /*listam intrebarile existente in test*/
        try {
            List<TestQuestion> listTestQuestion = null;
            try {
                listTestQuestion = this._testQuestionDao.listQuestionsWithTestId(test.getId());
            } catch (TransactionError ex) {
                model.addAttribute("error", ex.toString());
                return "/web/error";
            }

            List<Question> totalQuestions = new LinkedList<Question>();
            List<Question> questionList = new ArrayList<Question>();

            for (TestQuestion tq : listTestQuestion) {
                questionList = this._questionDao.findQuestionWithId(tq.getQuestionId());

                if (questionList.size() == 1) {
                    totalQuestions.add(questionList.get(0));
                }
            }

            /*adaugam lista de teste in model*/
            model.addAttribute("questionList", totalQuestions);

        } catch (TransactionError ex) {
            model.addAttribute("error", "An error occured while fetching your question list");
            return "/WEB-INF/error.jsp";
        }

        /*adaugam o intrebare goala in model daca vrea sa adauge una goala*/
        model.addAttribute("question", new Question());
        /*adugam un search querry gol daca vrea sa caute o intrebare*/
        model.addAttribute("searchQuery", new SearchQuery());

        return "/WEB-INF/superusers/editTest.jsp";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    @RequestMapping("/addTest")
    public String addTest(HttpSession session, Model model, @ModelAttribute Test test) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "/web/home";
        }

        /*verificam daca are drepturile necsare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }

        Test result = null;
        test.setAuthorid(new Long(user.getUserId()).intValue());

        try {
            result = this._testDao.saveTest(test);
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "/web/editTest/" + new Long(result.getId()).toString();
    }

    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping("/updateTest/{testId}")
    public String updateTest(HttpSession session, @ModelAttribute Test test, Model model,
            @PathVariable("testId") long testId) {

        /*verificam daca userul este logat*/
        User user = (User) session.getAttribute("user");

        if (user == null) {
            /*il redirectionam catre pagina principala*/
            return "/web/home";
        }

        /*verificam daca are drepturile necsare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }

        List<Test> list = null;

        try {
            /*luam testul cu id-ul respectiv*/
            list = this._testDao.findTestWithId(testId);
        } catch (TransactionError ex) {
            model.addAttribute("error", ex.toString());
            return "/web/error";
        }

        /*verificam daca testul exista si daca a fost creeat de utilizatorul nostru*/
        if (list.size() != 1) {
            model.addAttribute("error", "The test does not exist!");
            return "/web/error";
        }

        /*luam testul original din baza de date cu id-ul specificat*/
        Test original = list.get(0);

        if (original.getAuthorid() != user.getUserId().longValue()) {
            model.addAttribute("error", "The test was not created by you!");
            return "/web/error";
        }

        /*modificam originalul cu datele noului test*/
        original.setAuthorid(new Long(test.getAuthorid()).intValue());
        original.setCategoryid(test.getCategoryid());
        original.setName(test.getName());
        original.setNumq(test.getNumq());
        original.setTime(test.getTime());
        original.setUseAllQuestions(test.getUseAllQuestions());
        original.setDifficulty(test.getDifficulty());

        /*salvam inapoi originalul in baza de date*/
        try {
            this._testDao.saveTest(original);
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "/web/editTest/" + new Long(original.getId()).toString();
    }

    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------
    @RequestMapping("/addQuestion")
    public String addQuestions(HttpSession session, Model model, @ModelAttribute Question question) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            /*daca nu este logat il redirectionam catre pagina princiapala*/
            return "/web/home";
        }

        /*verificam daca are drepturile necesare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }

        /*setam id-l utilizatorului*/
        question.setAuthorId(user.getUserId());

        try {
            /*adaugam intrebarea in baza noastra de date*/
            this._questionDao.saveQuestion(question);
        } catch (TransactionError ex) {
            model.addAttribute("error", "An error occured while adding your question \n" + ex.toString());
            return "/web/error.jsp";
        }

        return "/web/questions";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    @RequestMapping("/addQuestionForTest/{testId}")
    public String addQuestionForTest(HttpSession session, Model model,
            @ModelAttribute Question question, @PathVariable("testId") long testId) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            /*daca nu este logat il redirectionam catre pagina princiapala*/
            return "/web/home";
        }

        /*verificam daca are drepturile necesare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }

        List<Test> tests = null;

        try {
            /*verifivam daca testul a fost creat de utilizatorul nostru*/
            tests = this._testDao.findTestWithId(testId);
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }


        if (tests.size() != 1 || tests.get(0).getAuthorid() != user.getUserId()) {
            model.addAttribute("error", "Invalid test specified!");
            return "/WEB-INF/error.jsp";
        }

        /*setam id-l utilizatorului*/
        question.setAuthorId(user.getUserId());

        try {
            /*adaugam intrebarea in baza noastra de date*/
            this._questionDao.saveQuestion(question);

            TestQuestion tq = new TestQuestion();
            tq.setQuestionId(question.getId());
            tq.setTestId(testId);

            this._testQuestionDao.saveTestQuestion(tq);
        } catch (TransactionError ex) {
            model.addAttribute("error", "An error occured while adding your question \n" + ex.toString());
            return "/WEB-INF/error.jsp";
        }

        return "/web/editTest/" + testId;
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    @RequestMapping("/addQuestionToTest/{testId}/{questionId}")
    public void addQuestionToTest(HttpSession session, Model model,
            @PathVariable("testId") long testId, @PathVariable("questionId") long questionId) {

        /*verificam daca userul este logat*/
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return;
        }

        /*luam testul cu id-ul respectiv si verificam drepturile*/
        List<Test> tests = null;

        try {
            tests = this._testDao.findTestWithId(testId);
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (tests == null || tests.size() != 1 || tests.get(0).getAuthorid() != user.getUserId()) {
            return;
        }

        /*luam intrebarea cu id-ul specificat*/
        List<Question> questions = null;

        try {
            questions = this._questionDao.findQuestionWithId(questionId);
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (questions == null || questions.size() != 1) {
            return;
        }

        try {
            /*verificam daca intrebarea este deja in test*/
            if (this._testQuestionDao.listQuestionsWithLink(testId, questionId).size() > 0) {
                return;
            }
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*adaugam legatura in baza de date*/
        TestQuestion testQuestion = new TestQuestion(testId, questionId);

        try {
            this._testQuestionDao.saveTestQuestion(testQuestion);
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    @RequestMapping("/updateQuestion/{questionId}")
    public String updateQuestion(HttpSession session, @ModelAttribute Question question, Model model,
            @PathVariable("questionId") long questionId) {

        /*verificam daca userul este logat*/
        User user = (User) session.getAttribute("user");

        if (user == null) {
            /*il redirectionam catre pagina principala*/
            return "/web/home";
        }

        /*verificam daca are drepturile necsare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }

        List<Question> list = null;

        try {
            /*luam testul cu id-ul respectiv*/
            list = this._questionDao.findQuestionWithId(questionId);
        } catch (TransactionError ex) {
            model.addAttribute("error", ex.toString());
            return "/web/error";
        }

        /*verificam daca testul exista si daca a fost creeat de utilizatorul nostru*/
        if (list.size() != 1) {
            model.addAttribute("error", "The test does not exist!");
            return "/web/error";
        }

        /*luam testul original din baza de date cu id-ul specificat*/
        Question original = list.get(0);

        if (!original.getAuthorId().equals(user.getUserId())) {
            model.addAttribute("error", "The question was not created by you!");
            return "/web/error";
        }

        /*modificam originalul cu datele noului test*/
        original.setAnswer1(question.getAnswer1());
        original.setAnswer2(question.getAnswer2());
        original.setAnswer3(question.getAnswer3());
        original.setAnswer4(question.getAnswer4());
        original.setCategoryId(question.getCategoryId());
        original.setContent(question.getContent());
        original.setCorrectAnswer(question.getCorrectAnswer());
        original.setDifficulty(question.getDifficulty());

        /*salvam inapoi originalul in baza de date*/
        try {
            this._questionDao.saveQuestion(original);
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "/web/questions";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    @RequestMapping("/deleteQuestion/{questionId}")
    public String deleteQuestion(HttpSession session, Model model, @PathVariable("questionId") long questionId) {

        /*verificam daca userul este logat*/
        User user = (User) session.getAttribute("user");

        if (user == null) {
            /*il redirectionam catre pagina principala*/
            return "/web/home";
        }

        /*verificam daca are drepturile necsare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }

        List<Question> list = null;

        try {
            /*luam testul cu id-ul respectiv*/
            list = this._questionDao.findQuestionWithId(questionId);
        } catch (TransactionError ex) {
            model.addAttribute("error", ex.toString());
            return "/web/error";
        }

        /*verificam daca testul exista si daca a fost creeat de utilizatorul nostru*/
        if (list.size() != 1) {
            model.addAttribute("error", "The test does not exist!");
            return "/web/error";
        }

        /*luam testul original din baza de date cu id-ul specificat*/
        Question original = list.get(0);

        if (!original.getAuthorId().equals(user.getUserId())) {
            model.addAttribute("error", "The question was not created by you!");
            return "/web/error";
        }
        try {

            /*scoatem intrarile din toate testele in care exista*/
            for (TestQuestion tq : this._testQuestionDao.listQuestionsWithQuestionId(questionId)) {
                this._testQuestionDao.deleteTestQuestion(tq);
            }

            /*stergem intrebarea din baza de date*/
            this._questionDao.deleteQuestion(original);
        } catch (TransactionError ex) {
            model.addAttribute("error", "An error occured while deleting your question\n" + ex.toString());
            return "/WEB-INF/error.jsp";
        }

        return "/web/questions";
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    @RequestMapping("/removeQuestionFromTest/{testId}/{questionId}")
    public String removeQuestionFromTest(HttpSession session, Model model, @PathVariable("questionId") long questionId,
            @PathVariable("testId") long testId) throws TransactionError {

        /*verificam daca userul este logat*/
        User user = (User) session.getAttribute("user");

        if (user == null) {
            /*il redirectionam catre pagina principala*/
            return "/web/home";
        }

        /*verificam daca are drepturile necsare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }

        List<Test> testList = null;

        try {
            /*luam testul cu id-ul respectiv*/
            testList = this._testDao.findTestWithId(testId);
        } catch (TransactionError ex) {
            model.addAttribute("error", ex.toString());
            return "/web/error";
        }

        /*verificam daca testul exista si daca a fost creeat de utilizatorul nostru*/
        if (testList.size() != 1) {
            model.addAttribute("error", "The test does not exist!");
            return "/web/error";
        }

        /*luam testul original din baza de date cu id-ul specificat*/
        Test original = testList.get(0);

        if (original.getAuthorid() != user.getUserId().longValue()) {
            model.addAttribute("error", "The question was not created by you!");
            return "/web/error";
        }
        List<TestQuestion> result = this._testQuestionDao.listQuestionsWithLink(testId, questionId);

        for (TestQuestion tq : result) {
            this._testQuestionDao.deleteTestQuestion(tq);
        }

        return "/web/editTest/" + testId;
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    @RequestMapping("/deleteTest/{testId}")
    public String deleteTest(HttpSession session, Model model, @PathVariable("testId") long testId) {
        /*verificam daca userul este logat*/
        User user = (User) session.getAttribute("user");

        if (user == null) {
            /*il redirectionam catre pagina principala*/
            return "/web/home";
        }

        /*verificam daca are drepturile necsare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }

        List<Test> testList = null;

        try {
            /*luam testul cu id-ul respectiv*/
            testList = this._testDao.findTestWithId(testId);
        } catch (TransactionError ex) {
            model.addAttribute("error", ex.toString());
            return "/web/error";
        }

        /*verificam daca testul exista si daca a fost creeat de utilizatorul nostru*/
        if (testList.size() != 1) {
            model.addAttribute("error", "The test does not exist!");
            return "/web/error";
        }

        /*luam testul original din baza de date cu id-ul specificat*/
        Test original = testList.get(0);

        if (original.getAuthorid() != user.getUserId().longValue()) {
            model.addAttribute("error", "The question was not created by you!");
            return "/web/error";
        }
        try {
            /*luam test-question-urile de legatura si le stergem din baza de date*/
            for (TestQuestion tq : this._testQuestionDao.listQuestionsWithTestId(original.getId())) {
                this._testQuestionDao.deleteTestQuestion(tq);
            }

            this._testDao.deleteTest(original);
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }



        return "/web/tests";
    }

    @RequestMapping("/viewUserScores/{testid}")
    public String viewUserScores(HttpSession session, Model model, @PathVariable("testId") long testId) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            /*il redirectionam catre pagina principala*/
            return "/web/home";
        }

        /*verificam daca are drepturile necsare*/
        if (user.getType() != User.EDITOR_USER) {
            model.addAttribute("error", "You do not have privilegies to acces this page");
            return "/web/error";
        }

        List<Test> testList = null;

        try {
            /*luam testul cu id-ul respectiv*/
            testList = this._testDao.findTestWithId(testId);
        } catch (TransactionError ex) {
            model.addAttribute("error", ex.toString());
            return "/web/error";
        }

        /*verificam daca testul exista si daca a fost creeat de utilizatorul nostru*/
        if (testList.size() != 1) {
            model.addAttribute("error", "The test does not exist!");
            return "/web/error";
        }

        /*luam testul original din baza de date cu id-ul specificat*/
        Test original = testList.get(0);

        if (original.getAuthorid() != user.getUserId().longValue()) {
            model.addAttribute("error", "The question was not created by you!");
            return "/web/error";
        }
        try {
            /*luam test-question-urile de legatura si le stergem din baza de date*/
            model.addAttribute("testUserList", this._testUserDao.listRecordsWithTestId(testId));
        } catch (TransactionError ex) {
            Logger.getLogger(SuperuserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "/WEB-INF/superusers/userScores.jsp";
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

    public CategoryDao getCategoryDao() {
        return _categoryDao;
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    public void setCategoryDao(CategoryDao _categoryDao) {
        this._categoryDao = _categoryDao;
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
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    public TestQuestionDao getTestQuestionDao() {
        return _testQuestionDao;
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    public void setTestQuestionDao(TestQuestionDao _testQuestionDao) {
        this._testQuestionDao = _testQuestionDao;
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    public TestUserDao getTestUserDao() {
        return _testUserDao;
    }
    //----------------------------------------------------------------------------------------------    
    //----------------------------------------------------------------------------------------------

    public void setTestUserDao(TestUserDao _testUserDao) {
        this._testUserDao = _testUserDao;
    }
}
