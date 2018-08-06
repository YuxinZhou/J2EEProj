package servlet;

import DAO.ExamDao;
import DAO.ScoreDao;
import DAO.StudentDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.Score;
import entity.Student;
import utility.LocalConfigReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guwei on 2016/3/17.
 */
public class GradeListServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ScoreDao scoreDao = new ScoreDao();
        StudentDao studentDao = new StudentDao();
        ArrayList<Score> scores = scoreDao.searchAllScore();
        ExamDao examDao = new ExamDao();

        //response json
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < scores.size(); ++i) {
            JSONObject jso = new JSONObject();
            Score score = scores.get(i);
            jso.put("major", examDao.getCategory(score.getSubject()));
            jso.put("examPlace", LocalConfigReader.GetExamPlace());
            jso.put("subject", score.getSubject());
            jso.put("ticket", score.getTicketNumber());
            Student student = studentDao.searchStudentInfo(score.getTicketNumber());
            jso.put("name", student.getStudentName());
            jso.put("firstGrade", score.getFirstGrade());
            jso.put("finalGrade", score.getFinalGrade());
            jso.put("lastGrade", score.getRetrialGrade());

            jsonArray.put(jso);
        }

        JSONObject responseObject = new JSONObject();
        responseObject.put("goods", jsonArray);
        responseObject.put("error_code", "2000");

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }
}