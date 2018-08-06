package servlet;

import DAO.ExamDao;
import DAO.ExpertDao;
import DAO.UserDao;
import JSON.JSONObject;
import entity.Exam;
import entity.Expert;
import utility.LocalConfigReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by guwei on 2016/3/17.
 */
public class ExpertLoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // http body
        BufferedReader buf = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        char[] c = new char[1024];
        try {
            while (buf.read(c) != -1) {
                stringBuilder.append(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String body = stringBuilder.toString();
        body = URLDecoder.decode(body, "UTF-8");

        //json
        JSONObject jsonObject = new JSONObject(body);
        String userName = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String examPlace = jsonObject.getString("examPlace");
        String major = jsonObject.getString("major");
        String subject = jsonObject.getString("subject");

        //response json
        JSONObject responseObject = new JSONObject();

        UserDao userdao = new UserDao();
        ExamDao examDao = new ExamDao();
        ExpertDao expertDao = new ExpertDao();
        Expert expert = expertDao.getExpertInfo(userName);

        if (!expert.getPassword().equals(password)) {
            responseObject.put("error_code", "2001");
        } else if (!LocalConfigReader.GetExamPlace().equals(examPlace)) {
            responseObject.put("error_code", "2002");
        } else if (!expert.getMajorIn().equals(major)) {
            responseObject.put("error_code", "2003");
        } else if (!examDao.getSubject(expert.getMajorIn()).equals(subject)) {
            responseObject.put("error_code", "2004");
        } else {
            responseObject.put("error_code", "2000");
        }

        JSONObject goods = new JSONObject();
        goods.put("examPlace", LocalConfigReader.GetExamPlace());
        goods.put("examGroup", expert.getExamGroup());
        goods.put("subject", expert.getMajorIn());
        goods.put("expertId",expert.getExpertId());
        goods.put("examId",expert.getExamId());
        // TODO:get exam time
        Exam exam = examDao.getExamInfo(expert.getExamId());
        goods.put("examTime", exam.getTime());

        responseObject.put("goods", goods);

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }
}
