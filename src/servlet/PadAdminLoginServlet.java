package servlet;

import DAO.UserDao;
import JSON.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by guwei on 2016/3/17.
 */
public class PadAdminLoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // http body
        BufferedReader buf = request.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        char[] c = new char[1024];
        try {
            while (buf.read(c) != -1) {
                stringBuilder.append(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String body = stringBuilder.toString();
        body = URLDecoder.decode(body, "UTF-8");

        //json
        JSONObject jsonObject = new JSONObject(body);
        String userName = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        //response json
        JSONObject responseObject = new JSONObject();


        UserDao userdao = new UserDao();

        if (userdao.hasUser_padmin(userName)
                && userdao.judgePassword_padmin(userName, password)) {
            responseObject.put("error_code", "2000");
        } else {
            //user name or password uncorret
            responseObject.put("error_code", "2001");
        }

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }
}