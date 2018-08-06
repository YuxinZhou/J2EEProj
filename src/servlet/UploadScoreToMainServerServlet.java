package servlet;

import DAO.ScoreDao;
import DAO.StudentDao;
import DAO.UserDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.MobileAdmin;
import entity.PadAdmin;
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
import java.util.List;

/**
 * Created by guwei on 2016/3/27.
 */
public class UploadScoreToMainServerServlet extends HttpServlet {
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
                jso.put("requestID", "003");

                JSONArray scoresJo = new JSONArray();
                ScoreDao scoreDao = new ScoreDao();
                ArrayList<Score> scores = scoreDao.searchAllScore();
                for (int i=0; i<scores.size();++i) {
                    JSONObject jo = new JSONObject();
                    Score score = scores.get(i);
                    jo.put("ScoreId", score.getScoreId());
                    jo.put("ExamId", score.getExamId());
                    jo.put("StudentId", score.getStudentId());
                    jo.put("TempId", score.getExamNumber());
                    jo.put("FirstPoint", score.getFirstGrade());
                    jo.put("FinalPoint", score.getFinalGrade());
                    jo.put("LastPoint", score.getRetrialGrade());
                    jo.put("Subject", score.getSubject());
                    jo.put("ExpertId", score.getExpertId());
                    scoresJo.put(jo);
                }
                jso.put("score", scoresJo);

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