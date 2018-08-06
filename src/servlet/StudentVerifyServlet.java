package servlet;

import DAO.StudentDao;
import JSON.JSONObject;
import entity.Student;
import utility.GroupGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by guwei on 2016/4/14.
 */
public class StudentVerifyServlet extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException
    {
        String ticketNumber = request.getParameter("ticket");
        String value = request.getParameter("value");

        StudentDao studentDao = new StudentDao();

        studentDao.verifyStudentInfo(ticketNumber, value);


        //response json
        JSONObject responseObject = new JSONObject();

        responseObject.put("error_code","2000");

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }
}