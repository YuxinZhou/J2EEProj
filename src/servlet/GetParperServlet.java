package servlet;

import DAO.*;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.Question;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by guwei on 2016/4/7.
 */
public class GetParperServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subject = request.getParameter("subject");

        StandardDao standardDao = new StandardDao();
        ArrayList<String> standards = standardDao.getAllKinds();
        QuestionDao questionDao = new QuestionDao();
        ArrayList<Question> chosedQuestions = new ArrayList<>();

        for (int i = 0; i < standards.size(); ++i)
        {
            //Hard code
            if (standards.get(i).equals("专业"))
            {
                ExamDao examDao = new ExamDao();

                chosedQuestions.add(GetRandomQuestionOfSubject(
                        questionDao.getQuestions(standards.get(i)),examDao.getSubject(subject)));
            }
            else
            {
                chosedQuestions.add(GetRandomQuestion(questionDao.getQuestions(standards.get(i))));
            }
        }

        //response json
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < chosedQuestions.size(); ++i) {
            JSONObject jso = new JSONObject();
            Question question = chosedQuestions.get(i);
            jso.put("type", question.getKind());
            jso.put("content", question.getContent());
            jso.put("limit", question.getLimitTime());

            jsonArray.put(jso);
        }

        JSONObject responseObject = new JSONObject();
        responseObject.put("goods", jsonArray);
        if (jsonArray.length() != 0) {
            responseObject.put("error_code", "2000");
        }
        else
        {
            responseObject.put("error_code", "2001");
        }

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }


    private Question GetRandomQuestion(ArrayList<Question> questions)
    {
        Question question;
        question = questions.get((int)(Math.random() * questions.size()));
        return question;
    }

    private Question GetRandomQuestionOfSubject(ArrayList<Question> questions, String subject)
    {
        Question question;
        question = GetRandomQuestion(questions);

        int count = 0;
        while (!question.getSubject().equals(subject) && count<500)
        {
            ++count;
            question = GetRandomQuestion(questions);
        }
        return question;
    }
}