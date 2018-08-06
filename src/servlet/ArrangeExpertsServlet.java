package servlet;

import DAO.ExpertDao;
import DAO.StudentDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.Expert;
import entity.Student;
import utility.GroupGenerator;
import utility.LocalConfigReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by guwei on 2016/3/15.
 */
public class ArrangeExpertsServlet extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException
    {
        GroupGenerator.GenerateGroups();

        ExpertDao expertDao = new ExpertDao();
        ArrayList<Expert> experts = expertDao.searchAllExpert();

        //response json
        JSONObject responseObject = new JSONObject();
        JSONArray goods = new JSONArray();
        for (int i = 1; i < experts.size(); ++i)
        {
            Expert ep = experts.get(i);
            JSONObject jo = new JSONObject();
            jo.put("major", ep.getMajorIn());
            jo.put("examPlace", LocalConfigReader.GetExamPlace());
            jo.put("expertId", ep.getExpertId());
            jo.put("expertName", ep.getExpertName());
            jo.put("examGroup", ep.getExamGroup());

            goods.put(jo);
        }

        responseObject.put("goods", goods);
        responseObject.put("error_code","2000");

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }
}
