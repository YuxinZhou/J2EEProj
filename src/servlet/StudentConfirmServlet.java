package servlet;

import DAO.StudentDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.Student;
import utility.GroupGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by guwei on 2016/3/15.
 */
public class StudentConfirmServlet extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException
    {
        String ticketNumber = request.getParameter("ticket");

        StudentDao studentDao = new StudentDao();
        Student student = studentDao.searchStudentInfo(ticketNumber);

        GroupGenerator.ArrangeStudentGroup(student);
        student.setConfirm(true);


        //response json
        JSONObject responseObject = new JSONObject();
        JSONObject goods = new JSONObject();
        goods.put("examGroup", student.getExamgroup());
        goods.put("examNumber", student.getExamnumber());

        responseObject.put("goods", goods);
        responseObject.put("error_code","2000");

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }
}