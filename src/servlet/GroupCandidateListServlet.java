package servlet;

import DAO.ExamDao;
import DAO.ScoreDao;
import DAO.StudentDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.Score;
import entity.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by guwei on 2016/3/22.
 */
public class GroupCandidateListServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String examGroup = request.getParameter("examGroup");

        StudentDao studentDao = new StudentDao();

        ArrayList<Integer> studentIds = studentDao.getExamNumberList(Integer.valueOf(examGroup));

        // TODO: Hard code : isArt
        boolean isArt = true;

        //response json
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < studentIds.size(); ++i) {
            JSONObject jso = new JSONObject();
            jso.put("id", studentIds.get(i));
            jso.put("isArt", isArt);
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