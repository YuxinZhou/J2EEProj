package DAO;

import entity.Student;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by DL on 2016/3/14.
 */
public class StudentDao {
    public StudentDao() {
        try {
            ctx = new InitialContext();
            if (ctx == null)
                throw new Exception("No Context");
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/autonomyEnrollment");
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void addEnrollStudent(Student student) {
        try {
            int studentId;
            String certificatedId = student.getCertificatedId();
            String studentName = student.getStudentName();
            String phoneNumber = student.getPhoneNumber();
            String gender = student.getGender();
            int age = student.getAge();
            String ticketNumber = student.getTicketNumber();
            String address = student.getAddress();
            String province = student.getProvince();
            String region = student.getRegion();
            String graduateSchool = student.getGraduateSchool();
            String examPlaceLocation = student.getExamPlaceLocation();
            Boolean confirm = student.isConfirm();
            String email = student.getEmail();
            String category = student.getCategory();
            Connection con = ds.getConnection();
            ResultSet rs;
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select StudentId from student");
            if (rs.next()) {
                rs.last();
                studentId = rs.getInt(1) + 1;
                PreparedStatement pstmt = con
                        .prepareStatement("insert into student (StudentId,CertificateId,StudentName,PhoneNumber,Gender,Age,TicketNumber,Address,Province,Region,GraduateSchool,ExamPlaceLocation,Confirm,Email,Category) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setInt(1, studentId);
                pstmt.setString(2, certificatedId);
                pstmt.setString(3, studentName);
                pstmt.setString(4, phoneNumber);
                pstmt.setString(5, gender);
                pstmt.setInt(6, age);
                pstmt.setString(7, ticketNumber);
                pstmt.setString(8, address);
                pstmt.setString(9, province);
                pstmt.setString(10, region);
                pstmt.setString(11, graduateSchool);
                pstmt.setString(12, examPlaceLocation);
                pstmt.setBoolean(13, confirm);
                pstmt.setString(14, email);
                pstmt.setString(15, category);
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                studentId = 1;
                PreparedStatement pstmt = con
                        .prepareStatement("insert into student (StudentId,CertificateId,StudentName,PhoneNumber,Gender,Age,TicketNumber,Address,Province,Region,GraduateSchool,ExamPlaceLocation,Confirm,Email,Category) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setInt(1, studentId);
                pstmt.setString(2, certificatedId);
                pstmt.setString(3, studentName);
                pstmt.setString(4, phoneNumber);
                pstmt.setString(5, gender);
                pstmt.setInt(6, age);
                pstmt.setString(7, ticketNumber);
                pstmt.setString(8, address);
                pstmt.setString(9, province);
                pstmt.setString(10, region);
                pstmt.setString(11, graduateSchool);
                pstmt.setString(12, examPlaceLocation);
                pstmt.setBoolean(13, confirm);
                pstmt.setString(14, email);
                pstmt.setString(15, category);
                pstmt.executeUpdate();
                pstmt.close();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Student searchStudentInfo(String ticketnumber) {
        Student student = new Student();
        try {
            int studentId;
            String category;
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select StudentId,StudentName,Category,CertificateId,Confirm,ExamPlaceLocation from student where TicketNumber='" + ticketnumber + "'");
            if (rs.next()) {
                studentId = rs.getInt(1);
                student.setStudentName(rs.getString(2));
                category = rs.getString(3);
                student.setCategory(category);
                student.setCertificatedId(rs.getString(4));
                student.setConfirm(rs.getBoolean(5));
                student.setExamPlaceLocation(rs.getString(6));
                student.setTicketNumber(ticketnumber);
            } else {
                rs.close();
                stmt.close();
                con.close();
                return student;
            }
            rs = stmt.executeQuery("select Subject from exam where Category='" + category + "'");
            if (rs.next())
                student.setSubject(rs.getString(1));
            else {
                rs.close();
                stmt.close();
                con.close();
                return student;
            }
            rs = stmt.executeQuery("select ExamNumber,ExamGroup from examnumber where StudentId=" + studentId);
            if (rs.next()) {
                student.setExamnumber(rs.getInt(1));
                student.setExamgroup(rs.getInt(2));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return student;
    }

    public void confimStudentInfo(String ticketnumber, int examgroup, int examnumber) {
        try {
            int studentId;
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select StudentId from student where TicketNumber='" + ticketnumber + "'");
            if (rs.next())
                studentId = rs.getInt(1);
            else {
                rs.close();
                stmt.close();
                con.close();
                return;
            }
            stmt.executeUpdate("update student set Confirm=" + 1 + " where TicketNumber='" + ticketnumber + "'");
            PreparedStatement pstmt = con
                    .prepareStatement("insert into examnumber (StudentId,ExamNumber,ExamGroup) values(?,?,?)");
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, examnumber);
            pstmt.setInt(3, examgroup);
            pstmt.executeUpdate();
            pstmt.close();
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void verifyStudentInfo(String ticketnumber, String verify) {
        try {
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("update student set Verify='" + verify + "' where TicketNumber='" + ticketnumber + "'");
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Integer> getExamNumberList(int examgroup) {
        ArrayList<Integer> allExamNumbers = new ArrayList<Integer>();
        int examNumber;
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select ExamNumber from examnumber where ExamGroup=" + examgroup);
            while (rs.next()) {
                examNumber = rs.getInt(1);
                allExamNumbers.add(examNumber);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allExamNumbers;
    }

    public ArrayList<Student> searchAllStudent() {
        ArrayList<Student> allStudents = new ArrayList<Student>();
        try {
            int studentId;
            String category;
            ResultSet rs, rs1, rs2;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select * from student");
            while (rs.next()) {
                Statement _stmt = con.createStatement();
                Student student = new Student();
                studentId = rs.getInt(2);
                student.setStudentId(studentId);
                student.setCertificatedId(rs.getString(3));
                student.setStudentName(rs.getString(4));
                student.setPhoneNumber(rs.getString(5));
                student.setGender(rs.getString(6));
                student.setAge(rs.getInt(7));
                student.setTicketNumber(rs.getString(8));
                student.setAddress(rs.getString(9));
                student.setProvince(rs.getString(10));
                student.setRegion(rs.getString(11));
                student.setGraduateSchool(rs.getString(12));
                student.setExamPlaceLocation(rs.getString(13));
                student.setConfirm(rs.getBoolean(14));
                student.setEmail(rs.getString(15));
                student.setVerify(rs.getString(17));
                category = rs.getString(16);
                student.setCategory(category);
                rs1 = _stmt.executeQuery("select Subject from exam where Category='" + category + "'");
                if (rs1.next())
                    student.setSubject(rs1.getString(1));
                rs1.close();
                rs2 = _stmt.executeQuery("select ExamNumber,ExamGroup from examnumber where StudentId=" + studentId);
                if (rs2.next()) {
                    student.setExamnumber(rs2.getInt(1));
                    student.setExamgroup(rs2.getInt(2));
                }
                rs2.close();
                _stmt.close();
                allStudents.add(student);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allStudents;
    }

    public void addAllStudent(ArrayList<Student> students) {
        try {
            Connection con = ds.getConnection();
            PreparedStatement pstmt;
            for (int i = 0; i < students.size(); i++) {
                pstmt = con.prepareStatement("insert into student (StudentId,CertificateId,StudentName,PhoneNumber,Gender,Age,TicketNumber,Address,Province,Region,GraduateSchool,ExamPlaceLocation,Confirm,Email,Category,Verify)  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setInt(1, students.get(i).getStudentId());
                pstmt.setString(2, students.get(i).getCertificatedId());
                pstmt.setString(3, students.get(i).getStudentName());
                pstmt.setString(4, students.get(i).getPhoneNumber());
                pstmt.setString(5, students.get(i).getGender());
                pstmt.setInt(6, students.get(i).getAge());
                pstmt.setString(7, students.get(i).getTicketNumber());
                pstmt.setString(8, students.get(i).getAddress());
                pstmt.setString(9, students.get(i).getProvince());
                pstmt.setString(10, students.get(i).getRegion());
                pstmt.setString(11, students.get(i).getGraduateSchool());
                pstmt.setString(12, students.get(i).getExamPlaceLocation());
                pstmt.setBoolean(13, students.get(i).isConfirm());
                pstmt.setString(14, students.get(i).getEmail());
                pstmt.setString(15, students.get(i).getCategory());
                pstmt.setString(16, "yes");
                pstmt.executeUpdate();
                pstmt.close();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    Context ctx;
    private DataSource ds;
}
