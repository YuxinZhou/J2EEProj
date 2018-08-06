package servlet;

import DAO.StudentDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by guwei on 2016/3/15.
 */
public class StudentListServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            StudentDao studentDao = new StudentDao();
        List<Student> studentList = studentDao.searchAllStudent();

        //response json
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < studentList.size(); ++i) {
            JSONObject jso = new JSONObject();
            jso.put("name", studentList.get(i).getStudentName());
            jso.put("ticket", studentList.get(i).getTicketNumber());
            jso.put("subject", studentList.get(i).getSubject());
            jso.put("personalId", studentList.get(i).getCertificatedId());
            jso.put("hasConfirmed", studentList.get(i).isConfirm());
            jso.put("examPlace", studentList.get(i).getExamPlaceLocation());
            jso.put("examGroup", studentList.get(i).getExamgroup());
            jso.put("examNumber", studentList.get(i).getExamnumber());
            jso.put("examType", studentList.get(i).getCategory());
            jso.put("gender", studentList.get(i).getGender());
            jso.put("age", studentList.get(i).getAge());
            jso.put("province", studentList.get(i).getProvince());
            jso.put("region", studentList.get(i).getRegion());
            jso.put("highSchool", studentList.get(i).getGraduateSchool());
            jso.put("email", studentList.get(i).getEmail());
            jso.put("address", studentList.get(i).getAddress());
            jso.put("hasVerified", studentList.get(i).getVerify());
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