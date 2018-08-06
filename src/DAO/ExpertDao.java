package DAO;

import entity.Expert;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by DL on 2016/3/15.
 */
public class ExpertDao {
    public ExpertDao() {
        try {
            ctx = new InitialContext();
            if (ctx == null)
                throw new Exception("No Context");
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/autonomyEnrollment");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Expert> searchAllExpert() {
        ArrayList<Expert> allExperts = new ArrayList<Expert>();
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select ExpertId,ExpertName,MajorIn,ExamGroup from expert");
            while (rs.next()) {
                Expert expert = new Expert();
                expert.setExpertId(rs.getInt(1));
                expert.setExpertName(rs.getString(2));
                expert.setMajorIn(rs.getString(3));
                expert.setExamGroup(rs.getInt(4));
                allExperts.add(expert);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allExperts;
    }

    public void addExperExamGroup(int expertid, int examgroup) {
        try {
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("update expert set ExamGroup=" + examgroup + " where ExpertId=" + expertid);
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public int getExamId(int expertid) {
        int examId = -1;
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select ExamId from expert where ExpertId=" + expertid);
            if (rs.next())
                examId = rs.getInt(1);
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return examId;
    }

    public Expert getExpertInfo(String username) {
        Expert expert = new Expert();
        try {
            ResultSet rs, rs1;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            int examId;
            rs = stmt.executeQuery("select ExpertName,CertificateId,MajorIn,Password,ExamId,ExpertId,ExamGroup from expert where UserName='" + username + "'");
            if (rs.next()) {
                expert.setExpertName(rs.getString(1));
                expert.setCertificatedId(rs.getString(2));
                expert.setMajorIn(rs.getString(3));
                expert.setPassword(rs.getString(4));
                examId = rs.getInt(5);
                expert.setExamId(examId);
                expert.setExpertId(rs.getInt(6));
                expert.setExamGroup(rs.getInt(7));
                rs1 = stmt.executeQuery("select Subject from exam where ExamId=" + examId);
                if (rs1.next())
                    expert.setSubject(rs1.getString(1));
                rs1.close();
            } else {
                expert = null;
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return expert;
    }

    public void addAllExpert(List<Expert> experts) {
        try {
            Connection con = ds.getConnection();
            PreparedStatement pstmt;
            for (int i = 0; i < experts.size(); i++) {
                pstmt = con.prepareStatement("insert into expert (ExpertName,CertificateId,MajorIn,Phonenumber,Email,Address,UserName,Password,ExamId) values(?,?,?,?,?,?,?,?,?)");
                pstmt.setString(1, experts.get(i).getExpertName());
                pstmt.setString(2, experts.get(i).getCertificatedId());
                pstmt.setString(3, experts.get(i).getMajorIn());
                pstmt.setString(4, experts.get(i).getPhoneNumber());
                pstmt.setString(5, experts.get(i).getEmail());
                pstmt.setString(6, experts.get(i).getAddress());
                pstmt.setString(7, experts.get(i).getUserName());
                pstmt.setString(8, experts.get(i).getPassword());
                pstmt.setInt(9, experts.get(i).getExamId());
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
