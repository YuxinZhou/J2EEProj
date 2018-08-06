package servlet;

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
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by guwei on 2016/3/28.
 */
public class UploadStudentToMainServerServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response json
        JSONObject responseObject = new JSONObject();
        responseObject.put("error_code", "2001");

        if (LocalConfigReader.ShouldConnectMainServer) {
            // communicate with main server
            Socket socket = null;
            BufferedReader br = null;
            PrintWriter pw = null;
            try {
                //客户端socket指定服务器的地址和端口号
                socket = new Socket(LocalConfigReader.MainServerIP, LocalConfigReader.MainServerPORT);
                System.out.println("Socket=" + socket);
                //同服务器原理一样
                br = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream(),"UTF-8")));
                JSONObject jso = new JSONObject();
                jso.put("requestID", "002");

                JSONArray studentJo = new JSONArray();
                StudentDao studentDao = new StudentDao();
                ArrayList<Student> students = studentDao.searchAllStudent();
                for (int i=0; i<students.size();++i) {
                    JSONObject jo = new JSONObject();
                    Student student = students.get(i);
                    jo.put("StudentId", student.getStudentId());
                    jo.put("IdCard", student.getCertificatedId());
                    jo.put("StudentName", student.getStudentName());
                    jo.put("PhoneNum", student.getPhoneNumber());
                    jo.put("Category", student.getCategory());
                    jo.put("Gender", student.getGender());
                    jo.put("Age", student.getAge());
                    jo.put("ExamPermitNumber", student.getTicketNumber());
                    jo.put("RegistrationNumber", student.getTicketNumber());
                    // no password
                    jo.put("Password", "123456");
                    jo.put("Email", student.getEmail());
                    jo.put("Address", student.getAddress());
                    jo.put("Province", student.getProvince());
                    jo.put("Region", student.getRegion());
                    jo.put("GraduateSchool", student.getGraduateSchool());
                    jo.put("Location", student.getExamPlaceLocation());

                    studentJo.put(jo);
                }
                jso.put("studentInfo", studentJo);

                pw.println(jso.toString());
                pw.flush();
                String str = br.readLine();
                System.out.println(str.length());
                System.out.println(str);

                pw.println("END");
                pw.flush();
                responseObject.put("error_code", "2000");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    System.out.println("close......");
                    br.close();
                    pw.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }
}