package servlet;

import DAO.StudentDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.Student;
import utility.LocalConfigReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by guwei on 2016/3/22.
 */
public class WarnServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String examGroup = request.getParameter("examGroup");

        int num = LocalConfigReader.AddWarning(Integer.valueOf(examGroup));

        JSONObject responseObject = new JSONObject();
        if (num == 1) {
            responseObject.put("error_code", "2001");
        }
        else
        {
            responseObject.put("error_code", "2002");
        }

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }
}