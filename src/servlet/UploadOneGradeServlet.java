package servlet;

import DAO.ExpertDao;
import DAO.ScoreDao;
import DAO.StudentDao;
import DAO.UserDao;
import JSON.JSONObject;
import entity.Expert;
import entity.Score;
import utility.LocalConfigReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by guwei on 2016/3/22.
 */
public class UploadOneGradeServlet extends HttpServlet {
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
        int examNumber = jsonObject.getInt("examNumber");
        String gradeType = jsonObject.getString("gradeType");
        String subject = jsonObject.getString("subject");
        int grade = jsonObject.getInt("grade");
        int expertId = jsonObject.getInt("expertId");
        String uploadTime = jsonObject.getString("uploadTime");

        Score score = new Score();
        score.setExamNumber(examNumber);
        score.setSubject(subject);
        score.setExpertId(expertId);
        score.setExamPlaceLocation(LocalConfigReader.GetExamPlace());

        ExpertDao expertDao = new ExpertDao();
        int examId = expertDao.getExamId(expertId);
        score.setExamId(examId);

        ScoreDao scoreDao = new ScoreDao();
        if (gradeType.equals("firstGrade"))
        {
            score.setFirstGrade(Integer.valueOf(grade));
            scoreDao.addFirstScore(score);
        }
        else if (gradeType.equals("finalGrade"))
        {
            score.setFinalGrade(Integer.valueOf(grade));
            scoreDao.addFinalScore(score);
        }
        else
        {
            score.setRetrialGrade(Integer.valueOf(grade));
            scoreDao.addRetrialScore(score);
        }

        //response json
        JSONObject responseObject = new JSONObject();
        responseObject.put("error_code", "2000");

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }
}