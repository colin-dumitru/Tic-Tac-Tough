/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tictac.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.springframework.stereotype.Service;
import tictac.test_user.TestUser;
import tictac.test_user.TestUserDao;
import tictac.core.AplicationParams;
import tictac.question.Question;
import tictac.question.QuestionDao;
import tictac.test_question.TestQuestion;
import tictac.user.TransactionError;
import tictac.user.UserService;
import tictac.test_question.TestQuestionDao;
import tictac.user.User;
import tictac.user.UserDao;

/**
 *
 * @author Administrator
 */
@Service
public class TestsService {

    protected static final String LOG_PREFIX = "[SUPER USER SERVICE]";
    protected AplicationParams _params;
    protected TestDao _testDao;
    protected QuestionDao _questionDao;
    protected TestQuestionDao _testQuestionDao;
    protected TestUserDao _testUserDao;
    protected DateFormat _df;
    protected UserDao _userDao;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public TestsService() {
        this._df = new SimpleDateFormat("yyyy.mm.dd hh:mm:ss ");
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public AplicationParams getParams() {
        return _params;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setParams(AplicationParams _params) {
        this._params = _params;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected List<Question> listQuestions(Test test) {

        List<TestQuestion> linkedQuestions = null;

        /*luam lista de legatura de intrebari din etstul respectiv*/
        try {

            linkedQuestions = this._testQuestionDao.listQuestionsWithTestId(test.getId());
        } catch (TransactionError ex) {
            Logger.getLogger(TestsService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        /*luam lista de intrebari din testul respectiv*/
        List<Question> questions = new ArrayList<Question>();

        for (TestQuestion tq : linkedQuestions) {
            try {
                questions.addAll(this._questionDao.findQuestionWithId(tq.getQuestionId()));
            } catch (TransactionError ex) {
                Logger.getLogger(TestsService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*iustina nu intelege */
        return questions;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void initializeTest(long testId, HttpSession session) {
        List<Test> result = null;

        try {
            result = this._testDao.findTestWithId(testId);
        } catch (TransactionError ex) {
            Logger.getLogger(TestsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*luam testul cu id-ul respectiv*/
        if (result == null || result.size() != 1) {
            return;
        }

        Test test = result.get(0);
        /*creeam lista de intrebari pe nivele de dificultate*/
        List<Question> totalQuestions = this.listQuestions(test);

        List<Question> easyQuestions = new ArrayList<Question>();
        List<Question> mediumQuestions = new ArrayList<Question>();
        List<Question> hardQuestions = new ArrayList<Question>();

        /*impartim testele pe nivele de dificultate*/
        for (Question question : totalQuestions) {
            switch (question.getDifficulty().intValue()) {
                case Question.D_EASY:
                    easyQuestions.add(question);
                    break;
                case Question.D_MEDIUM:
                    mediumQuestions.add(question);
                    break;
                case Question.D_HIGH:
                    hardQuestions.add(question);
                    break;
            }
        }

        /*adaugam testul in sesiunea curenta*/
        session.setAttribute("currentTest", test);
        /*setam intrebarea curent*/
        session.setAttribute("currentQuestion", null);
        /*numarul total de intrebari*/
        session.setAttribute("totalQuestions", totalQuestions.size());

        /*adaugam listele in sesiune*/
        session.setAttribute("easyQuestions", easyQuestions);
        session.setAttribute("mediumQuestions", mediumQuestions);
        session.setAttribute("hardQuestions", hardQuestions);


        /*setam dificultatea initala pentru intrebari - dificultatea medie*/
        session.setAttribute("questionDifficulty", Question.D_MEDIUM);
        /*setam numarul de intrebari gresite consecutiv*/
        session.setAttribute("consecutiveWrongAnswers", 0l);
        /*setam numarul de intrebari raspunse in total*/
        session.setAttribute("answered", 0l);
        /*setam scorul curent*/
        session.setAttribute("score", 0l);

    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void createTestUser(long testid, long userid) {
        try {
            this._testUserDao.saveTestUser(new TestUser(testid, userid));
        } catch (TransactionError ex) {
            Logger.getLogger(TestsService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void incrementTestAccessed(long testId) {
        List<Test> result = null;

        try {
            result = this._testDao.findTestWithId(testId);
        } catch (TransactionError ex) {
            Logger.getLogger(TestsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*luam testul cu id-ul respectiv*/
        if (result == null || result.size() != 1) {
            return;
        }

        Test test = result.get(0);
        test.setAccessed(test.getAccessed() + 1);
        try {
            this._testDao.saveTest(test);
        } catch (TransactionError ex) {
            Logger.getLogger(TestsService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public Element advanceTest(HttpSession session, long answer) {
        /*luam testul pentru care se avanseaza testul*/
        Test test = (Test) session.getAttribute("currentTest");

        /*luam listele de intrebari din testul respectiv*/
        List<Question> easyQuestions = (List<Question>) session.getAttribute("easyQuestions");
        List<Question> mediumQuestions = (List<Question>) session.getAttribute("mediumQuestions");
        List<Question> hardQuestions = (List<Question>) session.getAttribute("hardQuestions");

        /*luam userul pentru care se face testarea*/
        User user = (User) session.getAttribute("user");
        long score = (long) session.getAttribute("score");

        if (test == null) {
            return null;
        }

        /*verfiicam daca s-a terminat testul sau nu*/
        long answered = (long) session.getAttribute("answered");

        /*luam numarul de intrebari gresite consecuive si ajustam dificultatea daca se cere*/
        long wrongAnsweres = (long) session.getAttribute("consecutiveWrongAnswers");
        int difficulty = (int) session.getAttribute("questionDifficulty");

        if (test.getDifficulty() == Test.DYNAMIC_DIFFICULTY) {
            if (wrongAnsweres >= Test.DIFFICULTY_THRESHOLD) {

                switch ((int) difficulty) {
                    case Question.D_HIGH:
                        difficulty = Question.D_MEDIUM;
                        break;
                    case Question.D_MEDIUM:
                        difficulty = Question.D_EASY;
                        break;
                    /*daca sunt deja usoare nu avem ce sa mai schimbam... decat premiu de participare*/
                }

                session.setAttribute("questionDifficulty", difficulty);
            }
        }

        /*daca exista o intrebare curenta, verificam respunsul corect si ajustam scorul userului*/
        Question currentQuestion = (Question) session.getAttribute("currentQuestion");
        if (currentQuestion != null) {
            /*luam timpul cand a fost tirmisa intrebarea*/
            long sentTime = (long) session.getAttribute("sentTime");

            score += this.handleQuestionAnswer(currentQuestion, answer, sentTime, test.getTime() * 1000);
            session.setAttribute("score", score);
        }
        
        /*verificam daca s-au terminat intrebarile*/
        if (easyQuestions.size() <= 0 && mediumQuestions.size() <= 0 && hardQuestions.size() <= 0) {
            return this.handleTestFinish(user, score, session);
        } 
        /*verificam daca s-a ajuns la numarul total de intrebari*/
        else if ((test.getUseAllQuestions() == Test.RESTRAIN_QUESTIONS) && answered >= test.getNumq()) {
            return this.handleTestFinish(user, score, session);
        }

        /*apoi luam urmatoarea intrebare*/
        Question nextQuestion = this.nextQuestion(easyQuestions, hardQuestions, mediumQuestions, difficulty);
        session.setAttribute("currentQuestion", nextQuestion);
        session.setAttribute("sentTime", System.currentTimeMillis());

        /*facem update la intrebari cu cele scoase*/
        session.setAttribute("easyQuestions", easyQuestions);
        session.setAttribute("mediumQuestions", mediumQuestions);
        session.setAttribute("hardQuestions", hardQuestions);

        int numq = 0;

        if (test.getUseAllQuestions() == Test.USE_ALL_QUESTIONS) {
            numq = (int) session.getAttribute("totalQuestions");
        } else {
            numq = test.getNumq();
        }

        /*marim numarul de intrebari raspunse*/
        session.setAttribute("answered", answered + 1l);


        return this.constructResponse(nextQuestion, numq, answered, test.getTime());
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected Element constructResponse(Question question, long totalQuestion, long answered,
            long timePerQuestion) {
        /*ceeam xml-ul pt raspuns*/
        Element root = new DOMElement("response");

        /*intrebarea propriuzisa*/
        root.add(question.toXML());

        /*numarul total de inrebari*/
        Element questionsElement = new DOMElement("total");
        questionsElement.addAttribute("value", new Long(totalQuestion).toString());
        root.add(questionsElement);
        /*nuamrul de intrebari raspunse*/
        Element answeredElement = new DOMElement("answered");
        answeredElement.addAttribute("value", new Long(answered).toString());
        root.add(answeredElement);
        /*timpul pe intrebare*/
        Element timePerQuestionElement = new DOMElement("timePerQuestion");
        timePerQuestionElement.addAttribute("value", new Long(timePerQuestion).toString());
        root.add(timePerQuestionElement);

        return root;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected Question nextQuestion(List<Question> easy, List<Question> hard, List<Question> medium,
            long difficulty) {

        Random random = new Random(System.currentTimeMillis());
        Question ret = null;

        switch ((int) difficulty) {
            case Question.D_EASY:
                if (easy.size() > 0) {
                    ret = easy.get(random.nextInt(easy.size()));
                    easy.remove(ret);
                } else if (medium.size() > 0) {
                    ret = medium.get(random.nextInt(medium.size()));
                    medium.remove(ret);
                } else if (hard.size() > 0) {
                    ret = hard.get(random.nextInt(hard.size()));
                    hard.remove(ret);
                }
                break;
            case Question.D_MEDIUM:
                if (medium.size() > 0) {
                    ret = medium.get(random.nextInt(medium.size()));
                    medium.remove(ret);
                } else if (hard.size() > 0) {
                    ret = hard.get(random.nextInt(hard.size()));
                    hard.remove(ret);
                } else if (easy.size() > 0) {
                    ret = easy.get(random.nextInt(easy.size()));
                    easy.remove(ret);
                }
                break;
            case Question.D_HIGH:
                if (hard.size() > 0) {
                    ret = hard.get(random.nextInt(hard.size()));
                    hard.remove(ret);
                } else if (medium.size() > 0) {
                    ret = medium.get(random.nextInt(medium.size()));
                    medium.remove(ret);
                } else if (easy.size() > 0) {
                    ret = easy.get(random.nextInt(easy.size()));
                    easy.remove(ret);
                }
                break;
        }

        return ret;

    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected long handleQuestionAnswer(Question current, long answer, long timeStarted, long questionTime) {
        if (current.getCorrectAnswer() == answer && ((System.currentTimeMillis() - timeStarted) < questionTime)) {
            switch (current.getDifficulty().intValue()) {
                case Question.D_EASY:
                    return Test.EASY_SCORE;
                case Question.D_MEDIUM:
                    return Test.MEDIUM_SCORE;
                case Question.D_HIGH:
                    return Test.HARD_SCORE;
            }
        } else {
            switch (current.getDifficulty().intValue()) {
                case Question.D_EASY:
                    return Test.EASY_SCORE * (-1);
                case Question.D_MEDIUM:
                    return Test.MEDIUM_SCORE * (-1);
                case Question.D_HIGH:
                    return Test.HARD_SCORE * (-1);
            }
        }

        return 0;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected Element handleTestFinish(User user, long score, HttpSession session) {
        /*salveam rezultatele in baza de date*/
        Test test = (Test) session.getAttribute("currentTest");
        this.setScoreForTestUser(test.getId(), user.getUserId(), score);

        /*consrtuim xml-ul cu rezultatele sa le trimitem inpoi*/
        session.removeAttribute("currentTest");

        user.setTestScore(user.getTestScore() + (int) score);
        
        try {
            this._userDao.saveUser(user);
        } catch (TransactionError ex) {
            Logger.getLogger(TestsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        Element result = new DOMElement("result");

        Element scoreElement = new DOMElement("score");
        scoreElement.addAttribute("value", new Long(score).toString());

        result.add(scoreElement);

        return result;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected void setScoreForTestUser(long testid, long userid, long score) {
        try {
            this._testUserDao.saveTestUser(new TestUser(testid, userid, score));
        } catch (TransactionError ex) {
            Logger.getLogger(TestsService.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void logg(String what) {

        if (this._params.logger() != null) {

            try {
                this._params.logger().write(TestsService.LOG_PREFIX + " ["
                        + this._df.format(new Date()) + "] " + what);
            } catch (IOException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, what);
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public UserDao getUserDao() {
        return _userDao;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setUserDao(UserDao _userDao) {
        this._userDao = _userDao;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
}
