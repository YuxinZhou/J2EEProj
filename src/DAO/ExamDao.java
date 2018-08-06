package DAO;

import entity.Exam;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by DL on 2016/3/17.
 */
public class ExamDao {
    public ExamDao() {
        try {
            ctx = new InitialContext();
            if (ctx == null)
                throw new Exception("No Context");
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/autonomyEnrollment");
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public String getSubject(String category) {
        String subject = "";
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select Subject from exam where Category='" + category + "'");
            if (rs.next())
                subject = rs.getString(1);
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return subject;
    }

    public String getCategory(String subject) {
        String category = "";
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select Category from exam where Subject='" + subject + "'");
            if (rs.next())
                subject = rs.getString(1);
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return subject;
    }

    public void addAllExam(ArrayList<Exam> exams) {
        try {
            Connection con = ds.getConnection();
            PreparedStatement pstmt;
            for (int i = 0; i < exams.size(); i++) {
                pstmt = con.prepareStatement("insert into exam values(?,?,?,?,?)");
                pstmt.setInt(1, exams.get(i).getExamId());
                java.sql.Date sqlDate = new java.sql.Date(exams.get(i).getTime().getTime());
                pstmt.setDate(2, sqlDate);
                pstmt.setInt(3, exams.get(i).getExamPlaceId());
                pstmt.setString(4, exams.get(i).getSubject());
                pstmt.setString(5, exams.get(i).getCategory());
                pstmt.executeUpdate();
                pstmt.close();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Exam getExamInfo(int examid) {
        Exam exam = new Exam();
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select * from exam where ExamId=" + examid);
            if (rs.next()) {
                exam.setExamId(rs.getInt(1));
                exam.setTime(rs.getTime(2));
                exam.setExamPlaceId(rs.getInt(3));
                exam.setSubject(rs.getString(4));
                exam.setCategory(rs.getString(5));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return exam;
    }

    Context ctx;
    private DataSource ds;

    public static void main(String[] args) {
        ExamDao test = new ExamDao();
    }
}
