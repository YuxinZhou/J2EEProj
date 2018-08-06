package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.JspApplicationContext;

import DAO.QuestionDao;
import DAO.StandardDao;
import DAO.StudentDao;
import DAO.UserDao;
import JSON.JSONArray;
import JSON.JSONObject;
import entity.*;
import utility.GroupGenerator;
import utility.LocalConfigReader;
import utility.TicketNumberGetter;
//import DAO.UserDao;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/// login for mobile admin
public class AdminLoginServlet extends HttpServlet {
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
                        socket.getInputStream(), "UTF-8"));
                pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream())));
                JSONObject jso = new JSONObject();
                jso.put("requestID", "001");
                jso.put("serverID", LocalConfigReader.ServerID);

                pw.println(jso.toString());
                pw.flush();
                String str = br.readLine();
                System.out.println(str.length());
                System.out.println(str);

                JSONObject responseOb = new JSONObject(str);
                JSONObject info = responseOb.getJSONObject("info");

//                JSONArray mobileAdminArray = info.getJSONArray("mobileAdminInfo");
//                JSONObject mobileAdminInfo = mobileAdminArray.getJSONObject(0);
//                MobileAdmin mobileAdmin = new MobileAdmin();
//                mobileAdmin.setAccount(mobileAdminInfo.getString("username"));
//                mobileAdmin.setPassword(mobileAdminInfo.getString("password"));
//                UserDao userDao = new UserDao();
//                ArrayList<MobileAdmin> mobileAdmins = new ArrayList<>();
//                mobileAdmins.add(mobileAdmin);
//                userDao.addAllMobileAdmin(mobileAdmins);
//
//                JSONObject padAdminInfo = info.getJSONObject("padAdminInfo");
//                PadAdmin padAdmin = new PadAdmin();
//                padAdmin.setAccount(padAdminInfo.getString("username"));
//                padAdmin.setAccount(padAdminInfo.getString("password"));
//                ArrayList<PadAdmin> padAdmins = new ArrayList<>();
//                padAdmins.add(padAdmin);
//                userDao.addAllPadAdmin(padAdmins);

                JSONArray studentArray = info.getJSONArray("studentInfo");
                ArrayList<Student> students = new ArrayList<>();
                for(int i=0; i<studentArray.length();i++) {
                    //逐条解析JSONArray
                    JSONObject obj = new JSONObject();
                    obj = studentArray.getJSONObject(i);
                    Student student = new Student();

                    int StudentId = Integer.valueOf((String) obj.get("StudentId")).intValue();
                    student.setStudentId(StudentId);

                    String IdCard = (String) obj.get("IdCard");
                    student.setCertificatedId(IdCard);
                    String StudentName = (String) obj.get("StudentName");
                    student.setStudentName(StudentName);
                    String PhoneNum = (String) obj.get("PhoneNum");
                    student.setPhoneNumber(PhoneNum);
                    String Category = (String) obj.get("Category");
                    student.setCategory(Category);
                    String Gender = (String) obj.get("Gender");
                    student.setGender(Gender);
                    int Age = Integer.valueOf((String) obj.get("Age")).intValue();
                    student.setAge(Age);
                    String ExamPermitNumber = (String) obj.get("ExamPermitNumber");
                    student.setTicketNumber(ExamPermitNumber);
                    String Email = (String) obj.get("Email");
                    student.setEmail(Email);
                    String Address = (String) obj.get("Address");
                    student.setAddress(Address);
                    String Province = (String) obj.get("Province");
                    student.setProvince(Province);
                    String Region = (String) obj.get("Region");
                    student.setRegion(Region);
                    String GraduateSchool = (String) obj.get("GraduateSchool");
                    student.setGraduateSchool(GraduateSchool);
                    String Location = (String) obj.get("Location");
                    student.setExamPlaceLocation(Location);
                    students.add(student);
                }
                StudentDao studentDao = new StudentDao();
                studentDao.addAllStudent(students);
                    //TODO

                JSONArray range = info.getJSONArray("ticketNumberRange");
                String min = range.getJSONObject(0).getString("min");
                String max = range.getJSONObject(0).getString("max");
                TicketNumberGetter.SetTicketNumberRange(min, max);

                LocalConfigReader.ReExamTime = info.getString("retrialExamTime");

                pw.println("END");
                pw.flush();
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

        //LocalConfigReader.ReExamTime = "2016-4-20 19:05:00.0";
        ThreadRequestReExam thread = new ThreadRequestReExam("Thread");
        thread.start();


        //response json
        JSONObject responseObject = new JSONObject();


        UserDao userdao = new UserDao();

        if (userdao.hasUser_madmin(userName)
                && userdao.judgePassword_madmin(userName, password)) {
            responseObject.put("error_code", "2000");

            GroupGenerator.GenerateGroups();
        } else {
            //user name or password uncorret
            responseObject.put("error_code", "2001");
        }

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(responseObject.toString());
        response.getWriter().close();
    }

    class ThreadRequestReExam extends Thread{
        private String name;
        public ThreadRequestReExam(String name) {
            this.name=name;
        }

        public void run() {

            while (true) {
                System.out.println(name + "运行  :  ");
                try {
                    sleep((int) Math.random() * 10);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (compare_date(LocalConfigReader.ReExamTime, df.format(new Date())) < 0)
                    {
                        System.out.println("time !!!");
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

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
                            socket.getInputStream(), "UTF-8"));
                    pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                            socket.getOutputStream())));
                    JSONObject jso = new JSONObject();
                    jso.put("requestID", "005");

                    pw.println(jso.toString());
                    pw.flush();
                    String str = br.readLine();
                    System.out.println(str.length());
                    System.out.println(str);

                    JSONObject responseOb = new JSONObject(str);
                    JSONObject info = responseOb.getJSONObject("goods");

                    JSONArray standardArray = info.getJSONArray("standard");
                    ArrayList<Standard> standards = new ArrayList<>();
                    for(int i=0; i<standardArray.length();i++) {
                        //逐条解析JSONArray
                        JSONObject obj = new JSONObject();
                        obj = standardArray.getJSONObject(i);
                        Standard standard = new Standard();

                        String StandardId = (String) obj.get("StandardId");
                        standard.setStandardId(Integer.valueOf(StandardId).intValue());
                        String StandardName = (String) obj.get("StandardName");
                        standard.setStandardName(StandardName);
                        String Type = (String) obj.get("Type");
                        standard.setKind(Type);
                        String GradeSection1 = (String) obj.get("GradeSection1");
                        standard.setGradeSection1(Integer.valueOf(GradeSection1).intValue());
                        String GradeSection2 = (String) obj.get("GradeSection2");
                        standard.setGradeSection2(Integer.valueOf(GradeSection2).intValue());
                        String GradeSection3 = (String) obj.get("GradeSection3");
                        standard.setGradeSection3(Integer.valueOf(GradeSection3).intValue());

                        standards.add(standard);
                    }
                    StandardDao standardDao = new StandardDao();
                    standardDao.addAllStandard(standards);

                    JSONArray questionArray = info.getJSONArray("question");
                    ArrayList<Question> questions = new ArrayList<>();
                    for(int i=0; i<questionArray.length();i++) {
                        //逐条解析JSONArray
                        JSONObject obj = new JSONObject();
                        obj = questionArray.getJSONObject(i);
                        Question question = new Question();

                        String QuestionId = (String) obj.get("QuestionId");
                        question.setQuestionId(Integer.valueOf(QuestionId).intValue());
                        String Content = (String) obj.get("Content");
                        question.setContent(Content);
                        String Type = (String) obj.get("Type");
                        question.setKind(Type);
                        String Time = (String) obj.get("Time");
                        question.setLimitTime(Integer.valueOf(Time).intValue());
                        String Subject = (String) obj.get("Subject");
                        question.setSubject(Subject);

                        questions.add(question);
                    }
                    QuestionDao questionDao = new QuestionDao();
                    questionDao.addAllQuestion(questions);

                    pw.println("END");
                    pw.flush();
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
    }

    public static void main(String args[]) {
        int i= compare_date("1995-11-12 15:21", "1999-12-11 09:59");
        System.out.println("i=="+i);
    }

    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                //System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                //System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}