package com.anyview.yjy.dao;

import com.anyview.yjy.entity.Admin;
import com.anyview.yjy.utils.DBconnection;
import com.anyview.yjy.utils.DTO.AdminLoginDTO;
import com.anyview.yjy.utils.VO.AdminLoginVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    /**
     * 添加管理用户
     * @param admin
     * @return
     */
    public int saveAdmin(Admin admin) {
        String sql = "insert into admin(name, phone, password) values(?,?,?)";

        try {
            conn = DBconnection.getConnection();

            ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getName());
            ps.setString(2, admin.getPhone());
            ps.setString(3, admin.getPassword());

            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBconnection.close(conn, ps, rs);
        }
    }

    /**
     * 根据 phone 和 password 查询用户信息，用于登录
     * @param admin
     * @return
     */
    public AdminLoginVO login(AdminLoginDTO admin) {
        String sql = "select id, name from admin where phone = ? and password = ?";

        try {
            conn = DBconnection.getConnection();

            ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getPhone());
            ps.setString(2, admin.getPassword());

            rs = ps.executeQuery();

            if (rs.next()) {
                AdminLoginVO vo = new AdminLoginVO();
                vo.setId(rs.getLong("id"));
                vo.setName(rs.getString("name"));
                return vo;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBconnection.close(conn, ps, rs);
        }

        return null;
    }

    /**
     * 根据id查询管理员信息
     * @param adminId
     * @return
     */
    public Admin getById(Long adminId) {
        String sql = "select * from admin where id = ?";

        try {
            conn = DBconnection.getConnection();

            ps = conn.prepareStatement(sql);
            ps.setLong(1, adminId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getLong("id"));
                admin.setName(rs.getString("name"));
                admin.setPhone(rs.getString("phone"));
                admin.setPassword(rs.getString("password"));

                return admin;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 更新管理员信息
     * @param admin
     */
    public void update(Admin admin) {
        String sql = "update admin set name = ?, phone = ?, password = ? where id = ?";

        try {
            conn = DBconnection.getConnection();

            ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getName());
            ps.setString(2, admin.getPhone());
            ps.setString(3, admin.getPassword());
            ps.setLong(4, admin.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 登陆后返回基础信息
     * @param phone
     * @return
     */
    public AdminLoginVO getByPhone(String phone) {
        AdminLoginVO vo = new AdminLoginVO();
        String sql = "select id, name from admin where phone = ?";

        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                vo.setId(rs.getLong("id"));
                vo.setName(rs.getString("name"));
                return vo;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
