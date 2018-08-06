package servlet;

import DAO.StudentDao;
import JSON.JSONObject;
import entity.Student;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import utility.Excel.ReadExcel;
import utility.GroupGenerator;
import utility.LocalConfigReader;
import utility.TicketNumberGetter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guwei on 2016/3/8.
 */
public class PersonalEnrollServlet extends HttpServlet {
    private String filePath;    // 文件存放目录
    private String tempPath;    // 临时文件目录

    // 初始化
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        // 从配置文件中获得初始化参数
        //filePath = config.getInitParameter("filepath");
        //tempPath = config.getInitParameter("temppath");

        ServletContext context = getServletContext();

        //filePath = context.getRealPath(filePath);
        //tempPath = context.getRealPath(tempPath);
        filePath = "C:/AutoData";
        tempPath = "C:/AutoData";
        System.out.println("文件存放目录、临时文件目录准备完毕 ...");
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            DiskFileItemFactory diskFactory = new DiskFileItemFactory();
            // threshold 极限、临界值，即硬盘缓存 1M
            diskFactory.setSizeThreshold(4 * 1024);
            // repository 贮藏室，即临时文件目录
            diskFactory.setRepository(new File(tempPath));

            ServletFileUpload upload = new ServletFileUpload(diskFactory);
            // 设置允许上传的最大文件大小 4M
            upload.setSizeMax(4 * 1024 * 1024);
            // 解析HTTP请求消息头
            List fileItems = upload.parseRequest(request);
            Iterator iter = fileItems.iterator();
            while(iter.hasNext())
            {
                FileItem item = (FileItem)iter.next();
                if(item.isFormField())
                {
                    System.out.println("处理表单内容 ...");
                    processFormField(item);
                }else{
                    System.out.println("处理上传的文件 ...");
                    processUploadFile(item);
                }
            }// end while()

        }catch(Exception e){
            System.out.println("使用 fileupload 包时发生异常 ...");
            e.printStackTrace();
        }// end try ... catch ...

        ReadExcel readExcel = new ReadExcel();
        ArrayList<Student> students = readExcel.getEntrollStudents(filePath + "/" + filename);

        StudentDao studentDao = new StudentDao();
        for (int i = 0; i < students.size(); ++i)
        {
            Student student = students.get(i);
            student.setTicketNumber(TicketNumberGetter.GetNextTicketNumber());
            student.setExamPlaceLocation(LocalConfigReader.GetExamPlace());
            studentDao.addEnrollStudent(student);
            //GroupGenerator.ArrangeStudentGroup(student);
            //student.setConfirm(true);
            studentDao.verifyStudentInfo(student.getTicketNumber(), "notYet");
            LocalConfigReader.AddLocalStudent(student);
        }

        //response json
        JSONObject responseObject = new JSONObject();

        responseObject.put("error_code","2000");

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }

    private void processFormField(FileItem item)
            throws Exception
    {
        String name = item.getFieldName();
        String value = item.getString();
    }

    private static String filename;
    // 处理上传的文件
    private void processUploadFile(FileItem item)
            throws Exception
    {
        // 此时的文件名包含了完整的路径，得注意加工一下
        filename = item.getName();
        System.out.println("完整的文件名：" + filename);
        int index = filename.lastIndexOf("\\");
        filename = filename.substring(index + 1, filename.length());

        long fileSize = item.getSize();

        if("".equals(filename) && fileSize == 0)
        {
            System.out.println("文件名为空 ...");
            return;
        }

        File uploadFile = new File(filePath + "/" + filename);
        item.write(uploadFile);
    }
}