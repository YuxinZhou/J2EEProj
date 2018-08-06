package servlet;

import DAO.ScoreDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.Score;

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
public class UploadAllGradeServlet extends HttpServlet {
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
        JSONArray array = new JSONArray(body);
        ScoreDao scoreDao = new ScoreDao();

        for (int i=0; i<array.length(); ++i)
        {
            JSONObject jsonObject = array.getJSONObject(i);
            String examNumber = jsonObject.getString("examNumber");
//            String gradeType = jsonObject.getString("gradeType");
            String subject = jsonObject.getString("subject");
//            String grade = jsonObject.getString("grade");
            String expertId = jsonObject.getString("expertId");
            int examId=jsonObject.getInt("examId");
            String uploadTime = jsonObject.getString("uploadTime");
            int firstGrade = jsonObject.getInt("firstGrade");
            int finalGrade = jsonObject.getInt("finalGrade");
            int lastGrade = jsonObject.getInt("lastGrade");

            Score score = new Score();
            score.setExamNumber(Integer.valueOf(examNumber));
            score.setSubject(subject);
            score.setExpertId(Integer.valueOf(expertId));
            score.setExamId(Integer.valueOf(examId));
            score.setFirstGrade(Integer.valueOf(firstGrade));
            score.setFirstGrade(Integer.valueOf(finalGrade));
            score.setRetrialGrade(Integer.valueOf(lastGrade));

            scoreDao.addScore(score);

//            if (gradeType == "firstGrade")
//            {
//                score.setFirstGrade(Integer.valueOf(grade));
//                scoreDao.addFirstScore(score);
//            }
//            else
//            {
//                score.setFinalGrade(Integer.valueOf(grade));
//                scoreDao.addFinalScore(score);
//            }
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