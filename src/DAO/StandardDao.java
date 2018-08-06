package DAO;

import entity.Standard;

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
public class StandardDao {
    public StandardDao() {
        try {
            ctx = new InitialContext();
            if (ctx == null)
                throw new Exception("No Context");
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/autonomyEnrollment");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> getAllKinds() {
        ArrayList<String> kinds = new ArrayList<String>();
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select distinct Kind from standard");
            while (rs.next())
                kinds.add(rs.getString(1));
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return kinds;
    }

    public ArrayList<Standard> getStandards(String kind) {
        ArrayList<Standard> standards = new ArrayList<Standard>();
        try {
            Standard standard = new Standard();
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select StandardId,StandardName,GradeSection1,GradeSection2,GradeSection3 from standard where Kind='" + kind + "'");
            while (rs.next()) {
                standard.setStandardId(rs.getInt(1));
                standard.setStandardName(rs.getString(2));
                standard.setGradeSection1(rs.getInt(3));
                standard.setGradeSection2(rs.getInt(4));
                standard.setGradeSection3(rs.getInt(5));
                standard.setKind(kind);
                standards.add(standard);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return standards;
    }

    public ArrayList<Standard> getStandards() {
        ArrayList<Standard> standards = new ArrayList<Standard>();
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select * from standard");
            while (rs.next()) {
                Standard standard = new Standard();
                standard.setStandardId(rs.getInt(1));
                standard.setStandardName(rs.getString(2));
                standard.setKind(rs.getString(3));
                standard.setGradeSection1(rs.getInt(4));
                standard.setGradeSection2(rs.getInt(5));
                standard.setGradeSection3(rs.getInt(6));
                standards.add(standard);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return standards;
    }

    public void addAllStandard(ArrayList<Standard> standards) {
        try {
            Connection con = ds.getConnection();
            PreparedStatement pstmt;
            for (int i = 0; i < standards.size(); i++) {
                pstmt = con.prepareStatement("insert into standard (StandardId,StandardName,Kind,GradeSection1,GradeSection2,GradeSection3)  values(?,?,?,?,?,?)");
                pstmt.setInt(1, standards.get(i).getStandardId());
                pstmt.setString(2, standards.get(i).getStandardName());
                pstmt.setString(3, standards.get(i).getKind());
                pstmt.setInt(4, standards.get(i).getGradeSection1());
                pstmt.setInt(5, standards.get(i).getGradeSection2());
                pstmt.setInt(6, standards.get(i).getGradeSection3());
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
