package com.anyview.yjy.dao;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.entity.Orders;
import com.anyview.yjy.utils.DBconnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.anyview.yjy.utils.code.*;

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
                    order.setEndTime(rs.getObject("end_time", LocalDateTime.class));
                    order.setCreateTime(rs.getObject("create_time", LocalDateTime.class));
                    order.setStatus(rs.getLong("status"));
                    order.setPrice(rs.getInt("price"));
                    list.add(order);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else if(userId != null) {
            String sql = "select * from orders where user_id = ?";
            try {
                conn = DBconnection.getConnection();

                ps = conn.prepareStatement(sql);
                ps.setLong(1, userId);

                rs = ps.executeQuery();
                while(rs.next()) {
                    Orders order = new Orders();
                    order.setId(rs.getLong("id"));
                    order.setUserId(rs.getLong("user_id"));
                    order.setMovie(rs.getLong("movie"));
                    order.setHall(rs.getLong("hall"));
                    order.setSeat(rs.getLong("seat"));
                    order.setShowTime(rs.getObject("show_time", LocalDateTime.class));
                    order.setEndTime(rs.getObject("end_time", LocalDateTime.class));
                    order.setCreateTime(rs.getObject("create_time", LocalDateTime.class));
                    order.setStatus(rs.getLong("status"));
                    order.setPrice(rs.getInt("price"));
                    list.add(order);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    /**
     * 购票
     * @param userId
     * @param movieId
     * @param seatId
     * @return
     */
    public Integer add(Long userId, Long movieId, Long seatId) {

        if(userId == null || movieId == null || seatId == null) {
            return BUY_FAIL;
        }

        String sql = "insert into orders(user_id, movie, hall, seat, show_time, end_time, create_time, status, price) values(?,?,?,?,?,?,?,?,?)";
        Movie movie = movieDao.adminGetById(movieId);
        // 电影不存在
        if(movie.getId() == null) {
            return MOVIE_NO_FIND;
        }

        //库存不足
        if(movie.getAmount() <= 0) {
            return LACK;
        }

        // 检查座位是否空闲
        boolean empty = isEmpty(movieId, seatId, movie.getShowTime());
        if(!empty) {
            return SEAT_NOT_NULL;
        }

        // 创建订单
        Orders order = new Orders();
        order.setUserId(userId);
        order.setMovie(movieId);
        order.setHall(movie.getHall());
        order.setSeat(seatId);
        order.setShowTime(movie.getShowTime());
        order.setEndTime(movie.getEndTime());
        order.setCreateTime(LocalDateTime.now());
        order.setPrice(movie.getPrice());
        order.setStatus(ORDER_UNPAID); // 订单状态未支付

        movie.setAmount(movie.getAmount() - 1); // 库存-1
        movieDao.updateNoLock(movie);

        try {
//            添加属性,获取订单主键 id
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, order.getUserId());
            ps.setLong(2, order.getMovie());
            ps.setLong(3, order.getHall());
            ps.setLong(4, order.getSeat());
            ps.setString(5,order.getShowTime().toString());
            ps.setString(6,order.getEndTime().toString());
            ps.setString(7,order.getCreateTime().toString());
            ps.setLong( 8, order.getStatus());
            ps.setLong(9, order.getPrice());
            ps.executeUpdate();
//          Mybatis -> useGeneratedKeys = true
            rs = ps.getGeneratedKeys();
            if(rs.next()) {
                return rs.getInt(1);
            } else {
                return BUY_FAIL;
            }

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
        String sql = "select count(1) from orders where movie = ? and seat = ? and show_time = ? and status != ?";

        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setLong(1, movieId);
            ps.setLong(2, seatId);
            ps.setString(3, show_time.toString());
            ps.setLong(4, ORDER_CANCEL);

            rs = ps.executeQuery();

            if(rs.next()) {
                if(rs.getLong(1) > 0) {return false;}
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    /**
     * 获取所有订单
     * @return
     */
    public List<Orders> getAll() {
        List<Orders> list = new ArrayList<>();

        String sql = "select * from orders";

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
                order.setEndTime(rs.getObject("end_time", LocalDateTime.class));
                order.setCreateTime(rs.getObject("create_time", LocalDateTime.class));
                order.setStatus(rs.getLong("status"));
                order.setPrice(rs.getInt("price"));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取订单详细
     * @param orderId
     * @return
     */
    public Orders getById(Long orderId) {
        String sql = "select * from orders where id = ?";
        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, orderId);
            rs = ps.executeQuery();
            if(rs.next()) {
                Orders order = new Orders();
                order.setId(rs.getLong("id"));
                order.setUserId(rs.getLong("user_id"));
                order.setMovie(rs.getLong("movie"));
                order.setHall(rs.getLong("hall"));
                order.setSeat(rs.getLong("seat"));
                order.setShowTime(rs.getObject("show_time", LocalDateTime.class));
                order.setEndTime(rs.getObject("end_time", LocalDateTime.class));
                order.setStatus(rs.getLong("status"));
                order.setPrice(rs.getInt("price"));
                order.setCreateTime(rs.getObject("create_time", LocalDateTime.class));
                return order;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 更新订单状态
     * @param order
     */
    public void update(Orders order) {
        String sql = "update orders set user_id = ?, create_time = ?, movie = ?, hall = ?, " +
                "seat = ?, show_time = ?, end_time = ?, status = ?, price = ? where id = ?";
        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, order.getUserId());
            ps.setString(2, order.getCreateTime().toString());
            ps.setLong(3, order.getMovie());
            ps.setLong(4, order.getHall());
            ps.setLong(5, order.getSeat());
            ps.setString(6,order.getShowTime().toString());
            ps.setString(7,order.getEndTime().toString());
            ps.setLong(8, order.getStatus());
            ps.setLong(9, order.getPrice());
            ps.setLong(10, order.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取即将放映的电影数量, 用于提醒用户观影
     * @param userId
     * @return
     */
    public Integer getMovieShow(Long userId) {
        String sql = "select count(1) from orders where date_sub(show_time, interval 10 minute) < now() " +
                "and end_time > now() and status in (1, 3) and user_id = ?";
        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, userId);
            rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取申请取消的订单
     * @return
     */
    public List<Orders> getCancelApply() {
        String sql = "select * from orders where status = 4";
        List<Orders> list = new ArrayList<>();
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
                order.setStatus(rs.getLong("status"));
                order.setPrice(rs.getInt("price"));
                order.setShowTime(rs.getObject("show_time", LocalDateTime.class));
                order.setEndTime(rs.getObject("end_time", LocalDateTime.class));
                order.setCreateTime(rs.getObject("create_time", LocalDateTime.class));
                list.add(order);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 电影结束，更新已支付订单状态
     */
    public void finishTicket() {
        String sql = "update orders set status = ? where end_time < now() and status = ?";
        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1,ORDER_COMPLETE);   // 设置状态为已完成
            ps.setLong(2,ORDER_PAID);   // 更新对象是已支付且观影结束的订单
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 电影结束, 更新未支付订单状态
     */
    public void CancelTicket() {
        String sql = "update orders set status = ? where end_time < now() and status = ?";
        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1,ORDER_CANCEL);   // 设置状态为已完成
            ps.setLong(2,ORDER_UNPAID);   // 更新对象是已支付且观影结束的订单
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
