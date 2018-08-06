package servlet;

import DAO.ExamDao;
import DAO.ExpertDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.Expert;
import socket.JabberServer;
import utility.LocalConfigReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by guwei on 2016/3/17.
 */
public class ExpertFindPasswordServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String expertUsername = request.getParameter("username");
        String expertPersonalId = request.getParameter("personalId");

        ExpertDao expertDao = new ExpertDao();
        Expert expert = expertDao.getExpertInfo(expertUsername);

        JSONObject responseObject = new JSONObject();
        responseObject.put("error_code", "2000");
        if (expert != null && expert.getCertificatedId().equals(expertPersonalId)) {

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
                            socket.getOutputStream())));
                    JSONObject jso = new JSONObject();
                    jso.put("requestID", "004");
                    jso.put("expertID", expert.getExpertId());

                    pw.println(jso.toString());
                    pw.flush();
                    String str = br.readLine();
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
