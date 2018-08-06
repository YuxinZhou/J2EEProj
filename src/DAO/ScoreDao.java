package DAO;

import entity.Score;

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
public class ScoreDao {
    public ScoreDao() {
        try {
            ctx = new InitialContext();
            if (ctx == null)
                throw new Exception("No Context");
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/autonomyEnrollment");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Score> searchAllScore() {
        ArrayList<Score> allScores = new ArrayList<Score>();
        try {
            int examId, studentId, examPlaceId = 0;
            ResultSet rs, rs1, rs2, rs3;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select ScoreId,ExamId,StudentId,FirstGrade,FinalGrade from score");
            while (rs.next()) {
                Statement _stmt = con.createStatement();
                Score score = new Score();
                score.setScoreId(rs.getInt(1));
                examId = rs.getInt(2);
                score.setExamId(examId);
                studentId = rs.getInt(3);
                score.setStudentId(studentId);
                score.setFirstGrade(rs.getInt(4));
                score.setFinalGrade(rs.getInt(5));
                rs1 = _stmt.executeQuery("select ExamPlaceId,Subject,Category from exam where ExamId=" + examId);
                if (rs1.next()) {
                    examPlaceId = rs.getInt(1);
                    score.setSubject(rs1.getString(2));
                    score.setCategory(rs1.getString(3));
                }
                rs1.close();
                rs2 = _stmt.executeQuery("select ExamPlaceLocation from examplace where ExamPlaceId=" + examPlaceId);
                if (rs2.next())
                    score.setExamPlaceLocation(rs2.getString(1));
                rs2.close();
                rs3 = _stmt.executeQuery("select StudentName,TicketNumber from student where StudentId=" + studentId);
                if (rs3.next()) {
                    score.setStudentName(rs3.getString(1));
                    score.setTicketNumber(rs3.getString(2));
                }
                rs3.close();
                _stmt.close();
                allScores.add(score);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return allScores;
    }

    public void addFirstScore(Score score) {
        try {
            int examId, studentId, expertId, firstGrade, examNumber;
            examId = score.getExamId();
            expertId = score.getExpertId();
            firstGrade = score.getFirstGrade();
            examNumber = score.getExamNumber();
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select StudentId from examnumber where ExamNumber=" + examNumber);
            if (rs.next())
                studentId = rs.getInt(1);
            else {
                rs.close();
                stmt.close();
                con.close();
                return;
            }
            rs = stmt.executeQuery("select * from score where ExpertId=" + expertId + " and StudentId=" + studentId);
            if (!rs.next()) {
                PreparedStatement pstmt = con
                        .prepareStatement("insert into score (ExamId,StudentId,ExpertId,FirstGrade) values(?,?,?,?)");
                pstmt.setInt(1, examId);
                pstmt.setInt(2, studentId);
                pstmt.setInt(3, expertId);
                pstmt.setInt(4, firstGrade);
                pstmt.executeUpdate();
                pstmt.close();
            } else
                stmt.executeUpdate("update score set FirstGrade=" + firstGrade + " where ExpertId=" + expertId + " and StudentId=" + studentId);
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addFinalScore(Score score) {
        try {
            int examId, studentId, expertId, firstGrade, finalGrade, examNumber;
            examId = score.getExamId();
            expertId = score.getExpertId();
            firstGrade = score.getFirstGrade();
            finalGrade = score.getFinalGrade();
            examNumber = score.getExamNumber();
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select StudentId from examnumber where ExamNumber=" + examNumber);
            if (rs.next())
                studentId = rs.getInt(1);
            else {
                rs.close();
                stmt.close();
                con.close();
                return;
            }
            rs = stmt.executeQuery("select * from score where ExpertId=" + expertId + " and StudentId=" + studentId);
            if (!rs.next()) {
                PreparedStatement pstmt = con
                        .prepareStatement("insert into score (ExamId,StudentId,ExpertId,FirstGrade,FinalGrade) values(?,?,?,?,?)");
                pstmt.setInt(1, examId);
                pstmt.setInt(2, studentId);
                pstmt.setInt(3, expertId);
                pstmt.setInt(4, firstGrade);
                pstmt.setInt(5, finalGrade);
                pstmt.executeUpdate();
                pstmt.close();
            } else
                stmt.executeUpdate("update score set FinalGrade=" + finalGrade + " where ExpertId=" + expertId + " and StudentId=" + studentId);
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addRetrialScore(Score score) {
        try {
            int studentId, expertId, retrialGrade, examNumber;
            expertId = score.getExpertId();
            retrialGrade = score.getRetrialGrade();
            examNumber = score.getExamNumber();
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select StudentId from examnumber where ExamNumber=" + examNumber);
            if (rs.next())
                studentId = rs.getInt(1);
            else {
                rs.close();
                stmt.close();
                con.close();
                return;
            }
            rs = stmt.executeQuery("select * from score where ExpertId=" + expertId + " and StudentId=" + studentId);
            if (rs.next())
                stmt.executeUpdate("update score set RetrialGrade=" + retrialGrade + " where ExpertId=" + expertId + " and StudentId=" + studentId);
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addScore(Score score) {
        try {
            int examId, studentId, expertId, firstGrade, finalGrade;
            examId = score.getExamId();
            studentId = score.getStudentId();
            expertId = score.getExpertId();
            firstGrade = score.getFirstGrade();
            finalGrade = score.getFinalGrade();
            Connection con = ds.getConnection();
            PreparedStatement pstmt = con
                    .prepareStatement("insert into score (ExamId,StudentId,ExpertId,FirstGrade,FinalGrade) values(?,?,?,?,?)");
            pstmt.setInt(1, examId);
            pstmt.setInt(2, studentId);
            pstmt.setInt(3, expertId);
            pstmt.setInt(4, firstGrade);
            pstmt.setInt(5, finalGrade);
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addScoreList(ArrayList<Score> allScores) {
        try {
            Connection con = ds.getConnection();
            PreparedStatement pstmt;
            int examId, studentId, expertId, firstGrade, finalGrade;
            for (int i = 0; i < allScores.size(); i++) {
                examId = allScores.get(i).getExamId();
                studentId = allScores.get(i).getStudentId();
                expertId = allScores.get(i).getExpertId();
                firstGrade = allScores.get(i).getFirstGrade();
                finalGrade = allScores.get(i).getFinalGrade();
                pstmt = con.prepareStatement("insert into score (ExamId,StudentId,ExpertId,FirstGrade,FinalGrade) values(?,?,?,?,?)");
                pstmt.setInt(1, examId);
                pstmt.setInt(2, studentId);
                pstmt.setInt(3, expertId);
                pstmt.setInt(4, firstGrade);
                pstmt.setInt(5, finalGrade);
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
