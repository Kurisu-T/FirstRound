package com.anyview.yjy.controller;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.service.MovieService;
import com.anyview.yjy.service.OrderService;
import com.anyview.yjy.utils.VO.MovieVO;
import com.anyview.yjy.utils.jsonUtils;
import com.anyview.yjy.utils.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/movie/*")
public class MovieController extends HttpServlet {
    MovieService movieService =  new MovieService();
    OrderService orderService =  new OrderService();
    OrderController orderController = new OrderController();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if(path.equals("/list")){
            getMovieList(req, resp);
            return;
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

        switch (pathInfo) {
            case "/admin":
                doGet(req, resp);
                break;
            case "/user":
                doGet(req, resp);
                break;
//            case "/buy":
//                buyTicket(req, resp);
//                break;
            case "/update":
                getMovieById(req, resp);
                break;
            case "/modify":
                update(req, resp);
                break;
            default:
                req.getRequestDispatcher("/WEB-INF/movieOfUser.jsp").forward(req, resp);
                break;
        }
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
            resp.getWriter().write(Result.error("获取用户信息失败"));
            return;
        }

        if(userId != null && userId > 0) {
            List<MovieVO> list = movieService.list();
//            String movieJSON = jsonUtils.toJson(list);
//            if(movieJSON.equals("error")){
//                resp.getWriter().write(Result.error("error"));
//                return;
//            }
            resp.getWriter().write(Result.success(list));
//            req.setAttribute("movie", movieJSON);
//            req.getRequestDispatcher("/WEB-INF/movieOfUser.jsp").forward(req, resp);
        }
        else if(adminId != null && adminId > 0) {
            List<Movie> list = movieService.listByAdmin();
//            String movieJSON = jsonUtils.toMovieJson(list);
            resp.getWriter().write(Result.success(list));
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

        Long adminId =(Long) req.getAttribute("adminId");

        if(adminId == null) {
            try {
                resp.getWriter().write(Result.error("权限不足"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String movieUpdateId = req.getParameter("movieUpdateId");
        Long movieId = Long.parseLong(movieUpdateId);
        Movie movie = movieService.adminGetById(movieId);
        System.out.println(movie);
        req.removeAttribute("movie");
        req.setAttribute("movie", movie);
        resp.getWriter().write(Result.success(movie));
        req.getRequestDispatcher("/WEB-INF/MovieUpdate.jsp").forward(req, resp);
    }

    /**
     * 更新电影信息
     * @param req
     * @param resp
     */
    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Long adminId =(Long) req.getAttribute("adminId");

        if(adminId == null) {
            try {
                resp.getWriter().write(Result.error("权限不足"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        Long movieId = Long.parseLong(req.getParameter("id"));
        String movieName = req.getParameter("name");
        Long hall = Long.parseLong(req.getParameter("hall"));
        LocalDateTime showTime = LocalDateTime.parse(req.getParameter("show_time"));
        String description = req.getParameter("description");

        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setName(movieName);
        movie.setHall(hall);
        movie.setShowTime(showTime);
        movie.setDescription(description);
        movieService.update(movie);
        req.removeAttribute("movie");
//        resp.getWriter().write(jsonUtils.toJson(movie));
        resp.getWriter().write(Result.success(movie));
        try {
            doGet(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
//            resp.getWriter().write(Result.success());
//            orderController.doGet(req, resp);
//        } else {
//            resp.getWriter().write(Result.error("购票失败"));
//        }
//    }

}
