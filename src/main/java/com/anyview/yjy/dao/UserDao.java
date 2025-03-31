package com.anyview.yjy.dao;

import com.anyview.yjy.entity.User;
import com.anyview.yjy.utils.DBconnection;
import com.anyview.yjy.utils.DTO.UserLoginDTO;
import com.anyview.yjy.utils.VO.UserLoginVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    /**
     * 添加用户
     * @param user
     * @return
     */
    public int saveUser(User user) {
        String sql = "insert into user(name, phone, password) values(?,?,?)";

        try {
            conn = DBconnection.getConnection();

            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getPassword());

            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBconnection.close(conn, ps, rs);
        }
    }

    /**
     * 根据 phone 和 password 查询用户信息，用于登录
     * @param user
     * @return
     */
    public UserLoginVO login(UserLoginDTO user) {
        String sql = "select id, name from user where phone = ? and password = ? and status = 1";

        try {
            conn = DBconnection.getConnection();

            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getPhone());
            ps.setString(2, user.getPassword());

            rs = ps.executeQuery();

            if (rs.next()) {
                UserLoginVO userLoginVO = new UserLoginVO();
                userLoginVO.setId(rs.getLong("id"));
                userLoginVO.setName(rs.getString("name"));
                return userLoginVO;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBconnection.close(conn, ps, rs);
        }

        return null;
    }

    /**
     * 根据id获取用户
     * @param userId
     * @return
     */
    public User getById(Long userId) {
        String sql = "select * from user where id = ?";

        try {
            conn = DBconnection.getConnection();

            ps = conn.prepareStatement(sql);
            ps.setLong(1, userId);

            rs = ps.executeQuery();

            if(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void update(User user) {
        String sql = "update user set name = ?, phone = ?, password = ? where id = ?";
        try {
            conn = DBconnection.getConnection();

            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getPassword());
            ps.setLong(4, user.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过手机号获取用户信息
     * @param phone
     * @return
     */
    public UserLoginVO getByPhone(String phone) {
        String sql = "select * from user where phone = ?";

        UserLoginVO vo = new UserLoginVO();

        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if(rs.next()){
                vo.setId(rs.getLong("id"));
                vo.setName(rs.getString("name"));
                return vo;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * 获取所用用户信息
     * @return
     */
    public List<User> list() {
        String sql = "select * from user";
        List<User> list = new ArrayList<User>();
        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getInt("status"));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
