package servlet;

import DAO.ExamDao;
import DAO.ExpertDao;
import DAO.StudentDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.Expert;
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
public class ExpertListServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ExpertDao expertDao = new ExpertDao();
        ExamDao examDao = new ExamDao();
        ArrayList<Expert> expertList = expertDao.searchAllExpert();

        //response json
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < expertList.size(); ++i) {
            JSONObject jso = new JSONObject();
            Expert expert = expertList.get(i);
            jso.put("major", expert.getMajorIn());
            jso.put("subject", examDao.getSubject(expert.getMajorIn()));
            jso.put("examPlace", LocalConfigReader.GetExamPlace());
            jso.put("expertId", expert.getExpertId());
            jso.put("expertName", expert.getExpertName());
            jso.put("examGroup", expert.getExamGroup());

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