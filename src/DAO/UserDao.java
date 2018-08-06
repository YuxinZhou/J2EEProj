package DAO;

import entity.MobileAdmin;
import entity.PadAdmin;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class UserDao {
    public UserDao() {
        try {
            ctx = new InitialContext();
            if (ctx == null)
                throw new Exception("No Context");
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/autonomyEnrollment");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean hasUser_madmin(String username) {

        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select * from mobile_admin where account='" + username + "'");
            if (rs.next()) {
                rs.close();
                stmt.close();
                con.close();
                return true;
            } else {
                rs.close();
                stmt.close();
                con.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean judgePassword_madmin(String username, String password) {
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select password from mobile_admin where account='" + username + "'");
            if (rs.next()) {
                if (rs.getString(1).equals(password)) {
                    rs.close();
                    stmt.close();
                    con.close();
                    return true;
                } else {
                    rs.close();
                    stmt.close();
                    con.close();
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean hasUser_padmin(String username) {

        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select * from pad_admin where account='" + username + "'");
            if (rs.next()) {
                rs.close();
                stmt.close();
                con.close();
                return true;
            } else {
                rs.close();
                stmt.close();
                con.close();
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean judgePassword_padmin(String username, String password) {
        try {
            ResultSet rs;
            Connection con = ds.getConnection();
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("select password from pad_admin where account='" + username + "'");
            if (rs.next()) {
                if (rs.getString(1).equals(password)) {
                    rs.close();
                    stmt.close();
                    con.close();
                    return true;
                } else {
                    rs.close();
                    stmt.close();
                    con.close();
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void addAllMobileAdmin(ArrayList<MobileAdmin> mobileadmins) {
        try {
            Connection con = ds.getConnection();
            PreparedStatement pstmt;
            for (int i = 0; i < mobileadmins.size(); i++) {
                pstmt = con.prepareStatement("insert into mobile_admin values(?,?,?)");
                pstmt.setInt(1, mobileadmins.get(i).getAdminId());
                pstmt.setString(2, mobileadmins.get(i).getAccount());
                pstmt.setString(3, mobileadmins.get(i).getPassword());
                pstmt.executeUpdate();
                pstmt.close();
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addAllPadAdmin(ArrayList<PadAdmin> padadmins) {
        try {
            Connection con = ds.getConnection();
            PreparedStatement pstmt;
            for (int i = 0; i < padadmins.size(); i++) {
                pstmt = con.prepareStatement("insert into mobile_admin values(?,?,?)");
                pstmt.setInt(1, padadmins.get(i).getAdminId());
                pstmt.setString(2, padadmins.get(i).getAccount());
                pstmt.setString(3, padadmins.get(i).getPassword());
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
