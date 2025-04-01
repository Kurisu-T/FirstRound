package com.anyview.yjy.service;

import com.anyview.yjy.dao.MovieDao;
import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.utils.VO.MovieVO;

import java.util.List;

public class MovieService {
    MovieDao movieDao = new MovieDao();

    /**
     * 用户端获取电影列表
     * @return
     */
    public List<MovieVO> list() {
        return movieDao.list();
    }

    /**
     * 管理端获取电影列表
     * @return
     */
    public List<Movie> listByAdmin() {
        return movieDao.listByAdmin();
    }

    /**
     * 管理端根据id查询电影信息
     * @param movieId
     * @return
     */
    public Movie adminGetById(Long movieId) {
        return movieDao.adminGetById(movieId);
    }

    /**
     * 管理端更新电影信息
     * @param movie
     */
    public void update(Movie movie) {
        movieDao.update(movie);
    }

    /**
     * 添加电影
     * @param movie
     */
    public void add(Movie movie) {
        movieDao.add(movie);
    }

    /**
     * 更新电影信息（无锁）
     * @param movie
     */
    public void updateNoLock(Movie movie) {
        movieDao.updateNoLock(movie);
    }
}
