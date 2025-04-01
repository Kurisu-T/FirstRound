package com.anyview.yjy.dao;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.utils.DBconnection;
import com.anyview.yjy.utils.DTO.MovieDTO;
import com.anyview.yjy.utils.TimeUtils.TimeJSON;
import com.anyview.yjy.utils.VO.MovieVO;
import com.anyview.yjy.utils.result.MyResult;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovieDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    /**
     * 用户端获取电影列表
     * @return
     */
    public List<MovieVO> list() {
        String sql = "select * from movie where show_time > now() ";
        List<MovieVO> list = new ArrayList<MovieVO>();
        try {
            conn = DBconnection.getConnection();

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MovieVO vo = new MovieVO();
                vo.setName(rs.getString("name"));
                vo.setId(rs.getLong("id"));
                vo.setShowTime(rs.getObject("show_time", LocalDateTime.class));
                vo.setEndTime(rs.getObject("end_time", LocalDateTime.class));
                vo.setAmount(rs.getInt("amount"));
                vo.setHall(rs.getLong("hall"));
                vo.setPrice(rs.getInt("price"));
                vo.setDescription(rs.getString("description"));
                list.add(vo);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 管理端获取电影列表
     * @return
     */
    public List<Movie> listByAdmin() {
        String sql = "select * from movie";
        List<Movie>list = new ArrayList<>();
        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie();
//                Long id = rs.getLong("id");
//                String name = rs.getString("name");
//                Long hall = rs.getLong("hall");
//                LocalDateTime showTime = rs.getObject("show_time", LocalDateTime.class);
//                LocalDateTime createTime = rs.getObject("create_time", LocalDateTime.class);
//                String description = rs.getString("description");
//                System.out.println(id + "->" + name + "->" + hall + "->" + showTime + "->" + createTime + "->" + description);
                movie.setId(rs.getLong("id"));
                movie.setName(rs.getString("name"));
                movie.setHall(rs.getLong("hall"));
                movie.setShowTime(rs.getObject("show_time", LocalDateTime.class));
                movie.setEndTime(rs.getObject("end_time", LocalDateTime.class));
                movie.setCreateTime(rs.getObject("create_time", LocalDateTime.class));
                movie.setDescription(rs.getString("description"));
                movie.setPrice(rs.getInt("price"));
                movie.setAmount(rs.getInt("amount"));
                list.add(movie);

            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 用户端根据 id 查询电影信息
     * @param movieId
     * @return
     */
    public MovieDTO getById(Long movieId) {
        MovieDTO movie = new MovieDTO();
        String sql = "select * from movie where id = ? and show_time > now()";
        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, movieId);
            rs = ps.executeQuery();
            if (rs.next()) {
                movie.setId(rs.getLong("id"));
                movie.setHall(rs.getLong("hall"));
                movie.setPrice(rs.getInt("price"));
                movie.setShowTime(rs.getObject("show_time", LocalDateTime.class));
                movie.setEndTime(rs.getObject("end_time", LocalDateTime.class));
            }
            return movie;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 管理端根据id查询电影信息
     * @param movieId
     * @return
     */
    public Movie adminGetById(Long movieId) {
        Movie movie = new Movie();
        String sql = "select * from movie where id = ?";

        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setLong(1, movieId);
            rs = ps.executeQuery();
            if (rs.next()) {
                movie.setId(rs.getLong("id"));
                movie.setHall(rs.getLong("hall"));
                movie.setName(rs.getString("name"));
                movie.setDescription(rs.getString("description"));
                movie.setCreateTime(rs.getObject("create_time", LocalDateTime.class));
                movie.setShowTime(rs.getObject("show_time", LocalDateTime.class));
                movie.setEndTime(rs.getObject("end_time", LocalDateTime.class));
                movie.setAmount(rs.getInt("amount"));
                movie.setPrice(rs.getInt("price"));

                System.out.println(MyResult.success(movie));

                return movie;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movie;
    }

    /**
     * 更新电影信息（有锁）
     * @param movie
     */
    public boolean update(Movie movie) {
        String sql = "update movie set name = ?, hall = ?, show_time = ?, end_time = ?, amount = ?, price = ?, description = ? where id = ? and amount = ?";

        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, movie.getName());
            ps.setLong(2, movie.getHall());
            ps.setObject(3, movie.getShowTime());
            ps.setObject(4, movie.getEndTime());
            ps.setInt(5, movie.getAmount());
            ps.setInt(6,movie.getPrice());
            ps.setString(7, movie.getDescription());
            ps.setLong(8, movie.getId());
            //乐观锁
            ps.setLong(9, movie.getAmount() + 1);

            int number = ps.executeUpdate();
            if(number > 0) return true;
            else return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新电影信息（无锁）
     * @param movie
     * @return
     */
    public boolean updateNoLock(Movie movie) {
        String sql = "update movie set name = ?, hall = ?, show_time = ?, end_time = ?, amount = ?, price = ?, description = ? where id = ?";

        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, movie.getName());
            ps.setLong(2, movie.getHall());
            ps.setObject(3, movie.getShowTime());
            ps.setObject(4, movie.getEndTime());
            ps.setInt(5, movie.getAmount());
            ps.setInt(6,movie.getPrice());
            ps.setString(7, movie.getDescription());
            ps.setLong(8, movie.getId());

            int number = ps.executeUpdate();
            if(number > 0) return true;
            else return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加电影
     * @param movie
     */
    public void add(Movie movie) {
        String sql = "insert into movie(name, show_time, end_time, hall, amount, description, create_time) values(?,?,?,?,?,?,?)";

        try {
            conn = DBconnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, movie.getName());
            ps.setString(2, movie.getShowTime().toString());
            ps.setString(3, movie.getEndTime().toString());
            ps.setLong(4, movie.getHall());
            ps.setInt(5, movie.getAmount());
            ps.setString(6, movie.getDescription());
            ps.setString(7, movie.getCreateTime().toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
