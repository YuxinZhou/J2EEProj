package DAO;

import entity.ExamPlace;

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
public class    ExamPlaceDao {
    public ExamPlaceDao() {
        try {
            ctx = new InitialContext();
            if (ctx == null)
                throw new Exception("No Context");
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/autonomyEnrollment");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ExamPlace getExamPlaceInfo(String examplace) {
        ExamPlace examPlace = new ExamPlace();
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select * from examplace where ExamPlaceLocation='" + examplace + "'");
            if (rs.next()) {
                examPlace.setExamPlaceId(rs.getInt(1));
                examPlace.setExamPlaceLocation(rs.getString(2));
                examPlace.setPermitNumber(rs.getInt(3));
                examPlace.setExamPlaceName(rs.getString(4));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return examPlace;
    }

    public void addAllExamPlace(ArrayList<ExamPlace> examplaces) {
        try {
            Connection con = ds.getConnection();
            PreparedStatement pstmt;
            for (int i = 0; i < examplaces.size(); i++) {
                pstmt = con.prepareStatement("insert into examplace values(?,?,?,?)");
                pstmt.setInt(1, examplaces.get(i).getExamPlaceId());
                pstmt.setString(2, examplaces.get(i).getExamPlaceLocation());
                pstmt.setInt(3, examplaces.get(i).getPermitNumber());
                pstmt.setString(4, examplaces.get(i).getExamPlaceName());
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
