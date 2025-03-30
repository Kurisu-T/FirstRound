package com.anyview.yjy.dao;

import com.anyview.yjy.entity.Orders;
import com.anyview.yjy.utils.DBconnection;
import com.anyview.yjy.utils.DTO.MovieDTO;
import com.anyview.yjy.utils.code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    MovieDao movieDao = new MovieDao();

    /**
     * 查询订单列表
     * @return
     */
    public List<Orders> list(Long userId, Long adminId) {
        List<Orders> list = new ArrayList<Orders>();
        if(adminId != null) {
            String sql = "select * from orders";
//            System.out.println("Test of adminId order " + adminId);

            try {
                conn = DBconnection.getConnection();

                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                while(rs.next()) {
                    Orders order = new Orders();
                    order.setId(rs.getLong("id"));
                    order.setUserId(rs.getLong("user_id"));
                    order.setMovie(rs.getLong("movie"));
                    order.setHall(rs.getLong("hall"));
                    order.setSeat(rs.getLong("seat"));
                    order.setShowTime(rs.getObject("show_time", LocalDateTime.class));
                    order.setCreateTime(rs.getObject("create_time", LocalDateTime.class));
                    order.setStatus(rs.getLong("status"));
                    list.add(order);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else if(userId != null) {
            String sql = "select id, movie, hall, seat, show_time, status, create_time from orders where user_id = ?";
//            System.out.println("Test of user order " + userId);
            try {
                conn = DBconnection.getConnection();

//                System.out.println("Test of user order " + userId);
                ps = conn.prepareStatement(sql);
                ps.setLong(1, userId);

                rs = ps.executeQuery();
//                System.out.println("Test of user order " + userId);
                while(rs.next()) {
                    Orders order = new Orders();
                    order.setId(rs.getLong("id"));
                    order.setMovie(rs.getLong("movie"));
                    order.setHall(rs.getLong("hall"));
                    order.setSeat(rs.getLong("seat"));
                    order.setShowTime(rs.getObject("show_time", LocalDateTime.class));
                    order.setCreateTime(rs.getObject("create_time", LocalDateTime.class));
                    order.setStatus(rs.getLong("status"));
                    list.add(order);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
//        System.out.println(list);
        return list;
    }

    /**
     * 购票
     * @param userId
     * @param movieId
     * @param seatId
     * @return
     */
    public int add(Long userId, Long movieId, Long seatId) {
        System.out.println(userId + " " + movieId + " " + seatId);
        if(userId == null || movieId == null || seatId == null) {
            return 0;
        }
        String sql = "insert into orders(user_id, movie, hall, seat, show_time, create_time, status) values(?,?,?,?,?,?,?)";
        MovieDTO movie = movieDao.getById(movieId);

        boolean empty = isEmpty(movieId, seatId, movie.getShowTime());
        if(!empty) {
            return 0;
        }

        if(movie == null) { return 0; }
        Orders order = new Orders();
        order.setUserId(userId);
        order.setMovie(movieId);
        order.setHall(movie.getHall());
        order.setSeat(seatId);
        order.setShowTime(movie.getShowTime());
        order.setCreateTime(LocalDateTime.now());
        System.out.println(order.toString());
        order.setStatus(code.ORDER_UNPAID);

        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, order.getUserId());
            ps.setLong(2, order.getMovie());
            ps.setLong(3, order.getHall());
            ps.setLong(4, order.getSeat());
            ps.setString(5,order.getShowTime().toString());
            ps.setString(6,order.getCreateTime().toString());
            ps.setLong(7, order.getStatus());

            ps.executeUpdate();

            return 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断座位是否空闲
     * @param movieId
     * @param seatId
     * @return
     */
    public boolean isEmpty(Long movieId, Long seatId, LocalDateTime show_time) {
        String sql = "select count(1) from orders where movie = ? and seat = ? and show_time = ?";

        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setLong(1, movieId);
            ps.setLong(2, seatId);
            ps.setString(3, show_time.toString());

            rs = ps.executeQuery();

            if(rs.next()) {
                if(rs.getLong(1) > 0) {return false;}
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
