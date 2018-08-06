package DAO;

import entity.Question;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by DL on 2016/3/31.
 */
public class QuestionDao {
    public QuestionDao() {
        try {
            ctx = new InitialContext();
            if (ctx == null)
                throw new Exception("No Context");
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/autonomyEnrollment");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Question> getQuestions(String kind) {
        ArrayList<Question> questions = new ArrayList<Question>();
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select Content,LimitTime,Subject from question where Kind='" + kind + "'");
            while (rs.next()) {
                Question question = new Question();
                question.setContent(rs.getString(1));
                question.setLimitTime(rs.getInt(2));
                question.setSubject(rs.getString(3));
                question.setKind(kind);
                questions.add(question);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return questions;
    }


    public void addAllQuestion(ArrayList<Question> questions) {
        try {
            Connection con = ds.getConnection();
            PreparedStatement pstmt;
            for (int i = 0; i < questions.size(); i++) {
                pstmt = con.prepareStatement("insert into question (QuestionId,Content,Kind,LimitTime,Subject)  values(?,?,?,?,?)");
                pstmt.setInt(1, questions.get(i).getQuestionId());
                pstmt.setString(2, questions.get(i).getContent());
                pstmt.setString(3, questions.get(i).getKind());
                pstmt.setInt(4, questions.get(i).getLimitTime());
                pstmt.setString(5, questions.get(i).getSubject());
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
