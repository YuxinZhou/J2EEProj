package servlet;

import DAO.StandardDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.Standard;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by guwei on 2016/4/7.
 */
public class GetStandardServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StandardDao standardDao = new StandardDao();
        ArrayList<Standard> standards = standardDao.getStandards();

        //response json
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < standards.size(); ++i) {
            JSONObject jso = new JSONObject();
            Standard standard = standards.get(i);
            jso.put("standardName", standard.getStandardName());
            jso.put("type", standard.getKind());
            int[] gradeSection = new int[3];
            gradeSection[0] = standard.getGradeSection1();
            gradeSection[1] = standard.getGradeSection2();
            gradeSection[2] = standard.getGradeSection3();
            jso.put("gradeSection", gradeSection);

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
}