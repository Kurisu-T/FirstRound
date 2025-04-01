package com.anyview.yjy.controller;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.service.MovieService;
import com.anyview.yjy.service.OrderService;
import com.anyview.yjy.utils.DataUtils.ParseData;
import com.anyview.yjy.utils.TimeUtils.TimeJSON;
import com.anyview.yjy.utils.VO.MovieVO;
import com.anyview.yjy.utils.result.MyResult;
import com.anyview.yjy.utils.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@WebServlet("/movie/*")
public class MovieController extends HttpServlet {
    MovieService movieService =  new MovieService();
    OrderService orderService =  new OrderService();
    OrderController orderController = new OrderController();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

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
//        Long userId = (Long) req.getSession().getAttribute("userId");
//        Long adminId = (Long) req.getSession().getAttribute("adminId");
//
//        if((userId == null && adminId == null) || (userId != null && adminId != null)) {
//            req.getSession().invalidate();
//            resp.sendRedirect("index.jsp");
//            return;
//        }
//
//        if(userId != null && userId > 0) {
//            List<MovieVO> list = movieService.list();
//            String movieJSON = jsonUtils.toJson(list);
//            resp.getWriter().write(movieJSON);
//            req.setAttribute("movie", movieJSON);
//            req.getRequestDispatcher("/WEB-INF/movieOfUser.jsp").forward(req, resp);
//        }
//        else if(adminId != null && adminId > 0) {
//            List<Movie> list = movieService.listByAdmin();
//            String movieJSON = jsonUtils.toMovieJson(list);
//            resp.getWriter().write(movieJSON);
//            req.setAttribute("movie", movieJSON);
//            req.getRequestDispatcher("/WEB-INF/movieOfAdmin.jsp").forward(req, resp);
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        // TODO jsp 改 html
        /*System.out.println(pathInfo);
        if(pathInfo.equals("/buy")) {
            System.out.println("join in movie/buy");
            req.getRequestDispatcher("//WEB-INF/user.html").forward(req, resp);
        }*/
        Long userId = (Long) req.getSession().getAttribute("userId");
        Long adminId = (Long) req.getSession().getAttribute("adminId");

        if((userId == null && adminId == null) || (userId != null && adminId != null)) {
            req.getSession().invalidate();
            resp.sendRedirect("index.html");
            return;
        }

        System.out.println(pathInfo);

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
                req.getRequestDispatcher("/WEB-INF/movieOfUser.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long adminId = (Long) req.getSession().getAttribute("adminId");
        if(adminId == null) {
            resp.getWriter().write(Result.error("权限不足"));
            return;
        }

        String path = req.getPathInfo();

        if(path.startsWith("/modify/")) {
            update(req, resp);
        }
        else {
            resp.getWriter().write(Result.error("域名错误"));
        }
    }

    /**
     * 添加电影
     * @param req
     * @param resp
     * @throws IOException
     */
    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {

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
//            String movieJSON = jsonUtils.toJson(list);
//            if(movieJSON.equals("error")){
//                resp.getWriter().write(MyResult.error("error"));
//                return;
//            }
            resp.getWriter().write(MyResult.success(list));
//            System.out.println(MyResult.success(list));
//            req.setAttribute("movie", movieJSON);
//            req.getRequestDispatcher("/WEB-INF/movieOfUser.jsp").forward(req, resp);
        }
        else if(adminId != null && adminId > 0) {
            List<Movie> list = movieService.listByAdmin();
//            String movieJSON = jsonUtils.toMovieJson(list);
            resp.getWriter().write(MyResult.success(list));
//            req.setAttribute("movie", movieJSON);
//            req.getRequestDispatcher("/WEB-INF/movieOfAdmin.jsp").forward(req, resp);
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

        if(adminId == null) {
            try {
                resp.getWriter().write(MyResult.error("权限不足"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String movieUpdateId = req.getParameter("movieId");
//        System.out.println(movieUpdateId);
        Long movieId = Long.parseLong(movieUpdateId);
        Movie movie = movieService.adminGetById(movieId);
//        System.out.println(movie);
        req.removeAttribute("movie");
//        req.setAttribute("movie", movie);
        resp.getWriter().write(MyResult.success(movie));
    }

    /**
     * 更新电影信息
     * @param req
     * @param resp
     */
    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        Long movieId = Long.parseLong(path.split("/")[2]);
        Map<String, Object> data = ParseData.getData(req);

        String name = (String) data.get("name");
        Long hall = Long.parseLong((String) data.get("hall"));
        String dateTime = (String) data.get("showTime");
        LocalDateTime showTime = TimeJSON.JSONtoTime(dateTime);
        dateTime = (String)data.get("endTime");
        LocalDateTime endTime = TimeJSON.JSONtoTime(dateTime);
        String description = (String) data.get("description");
        Integer amount = Integer.parseInt(data.get("amount").toString());
        System.out.println(data.get("price"));
        Integer price = Integer.parseInt(data.get("price").toString());
        System.out.println(8);


//        String movieName = req.getParameter("name");
//        Long hall = Long.parseLong(req.getParameter("hall"));
//        System.out.println("get hall ok");
//        LocalDateTime showTime = LocalDateTime.parse(req.getParameter("showTime"));
//        System.out.println("get show time ok");
//        String description = req.getParameter("description");
//        System.out.println("get description ok");

        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setName(name);
        movie.setHall(hall);
        movie.setShowTime(showTime);
        movie.setEndTime(endTime);
        movie.setAmount(amount);
        movie.setPrice(price);
        movie.setDescription(description);

        System.out.println(MyResult.success(movie));
        movieService.update(movie);
//        req.removeAttribute("movie");
//        resp.getWriter().write(jsonUtils.toJson(movie));
        resp.getWriter().write(MyResult.success(movie));
//        try {
//            doGet(req, resp);
//        } catch (ServletException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    /**
     * 购票
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
//    private void buyTicket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String movie = req.getParameter("movieID");
//        String seat = req.getParameter("seat");
//        Long movieId = Long.parseLong(movie);
//        Long seatId = Long.parseLong(seat);
//        Long userId = (Long) req.getSession().getAttribute("userId");
//        int res = orderService.add(userId, movieId, seatId);
//        if(res > 0) {
//            resp.getWriter().write(MyResult.success());
//            orderController.doGet(req, resp);
//        } else {
//            resp.getWriter().write(MyResult.error("购票失败"));
//        }
//    }

}
