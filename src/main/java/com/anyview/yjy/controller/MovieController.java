package com.anyview.yjy.controller;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.service.MovieService;
import com.anyview.yjy.service.OrderService;
import com.anyview.yjy.utils.DataUtils.ParseData;
import com.anyview.yjy.utils.TimeUtils.TimeJSON;
import com.anyview.yjy.utils.VO.MovieVO;
import com.anyview.yjy.utils.result.MyResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@WebServlet("/movie/*")
public class MovieController extends HttpServlet {
    MovieService movieService =  new MovieService();
    OrderService orderService =  new OrderService();
    OrderController orderController = new OrderController();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = "/" + req.getPathInfo().split("/")[1];

        switch (path) {
            case "/list":
                getMovieList(req, resp);
                break;
            case "/info":
                getMovieById(req, resp);
                break;
            default:
                resp.getWriter().write(MyResult.error("域名错误"));
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        Long userId = (Long) req.getSession().getAttribute("userId");
        Long adminId = (Long) req.getSession().getAttribute("adminId");

        if((userId == null && adminId == null) || (userId != null && adminId != null)) {
            req.getSession().invalidate();
            resp.sendRedirect("index.html");
            return;
        }

        switch (pathInfo) {
            case "/admin":
                doGet(req, resp);
                break;
            case "/user":
                doGet(req, resp);
                break;
            case "/add":
                add(req, resp);
                break;
            case "/update":
                getMovieById(req, resp);
                break;
            default:
                resp.getWriter().write(MyResult.error("域名错误"));
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long adminId = (Long) req.getSession().getAttribute("adminId");
        if(adminId == null) {
            resp.getWriter().write(MyResult.error("权限不足"));
            return;
        }

        String path = req.getPathInfo();

        if(path.startsWith("/modify/")) {
            update(req, resp);
        }
        else {
            resp.getWriter().write(MyResult.error("域名错误"));
        }
    }

    /**
     * 添加电影
     * @param req
     * @param resp
     * @throws IOException
     */
    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // 这里用到了一个获取请求参数的一个函数
        Map<String, Object> data = ParseData.getData(req);
        Movie movie = new Movie();
        movie.setCreateTime(LocalDateTime.now());
        movie.setName(data.get("name").toString());
        movie.setDescription(data.get("description").toString());
        movie.setHall(Long.parseLong(data.get("hall").toString()));
        movie.setShowTime(TimeJSON.JSONtoTime(data.get("showTime").toString()));
        movie.setEndTime(TimeJSON.JSONtoTime(data.get("endTime").toString()));
        movie.setAmount(Integer.parseInt(data.get("amount").toString()));
        movie.setPrice(Integer.parseInt(data.get("price").toString()));
        movieService.add(movie);

        resp.getWriter().write(MyResult.success());
    }

    /**
     * 获取电影列表
     * @param req
     * @param resp
     */
    private void getMovieList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        Long adminId = (Long) req.getSession().getAttribute("adminId");
        if((userId == null && adminId == null) || (userId != null && adminId != null)) {
            req.getSession().invalidate();
            resp.getWriter().write(MyResult.error("获取用户信息失败"));
            return;
        }

        if(userId != null && userId > 0) {
            List<MovieVO> list = movieService.list();
            resp.getWriter().write(MyResult.success(list));
        }
        else if(adminId != null && adminId > 0) {
            List<Movie> list = movieService.listByAdmin();
            resp.getWriter().write(MyResult.success(list));
        }
    }

    /**
     * 获取电影信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void getMovieById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long adminId =(Long) req.getSession().getAttribute("adminId");
        Long userId = (Long) req.getSession().getAttribute("userId");

        if(adminId == null && userId == null) {
            try {
                resp.getWriter().write(MyResult.error("权限不足"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String movieUpdateId = req.getParameter("movieId");
        Long movieId = Long.parseLong(movieUpdateId);
        Movie movie = movieService.adminGetById(movieId);
        req.removeAttribute("movie");
        resp.getWriter().write(MyResult.success(movie));
    }

    /**
     * 更新电影信息
     * @param req
     * @param resp
     */
    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        // 获取url中的电影id
        Long movieId = Long.parseLong(path.split("/")[2]);
        Map<String, Object> data = ParseData.getData(req);

        String name = (String) data.get("name");
        Long hall = Long.parseLong((String) data.get("hall"));
        String dateTime = (String) data.get("showTime");
        // 自己写的格式转换工具类，将前端的 "yyyy-MM-dd HH:mm:ss" 转成 LocalDateTime 格式
        LocalDateTime showTime = TimeJSON.JSONtoTime(dateTime);
        dateTime = (String)data.get("endTime");
        LocalDateTime endTime = TimeJSON.JSONtoTime(dateTime);
        String description = (String) data.get("description");
        Integer amount = Integer.parseInt(data.get("amount").toString());
        Integer price = Integer.parseInt(data.get("price").toString());

        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setName(name);
        movie.setHall(hall);
        movie.setShowTime(showTime);
        movie.setEndTime(endTime);
        movie.setAmount(amount);
        movie.setPrice(price);
        movie.setDescription(description);

        movieService.updateNoLock(movie);
        resp.getWriter().write(MyResult.success(movie));
    }

}
