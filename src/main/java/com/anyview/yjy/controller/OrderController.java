package com.anyview.yjy.controller;

import com.anyview.yjy.entity.Orders;
import com.anyview.yjy.service.OrderService;
import com.anyview.yjy.utils.jsonUtils;
import com.anyview.yjy.utils.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/order/*")
public class OrderController extends HttpServlet {
    OrderService orderService = new OrderService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        Long adminId = (Long) req.getSession().getAttribute("adminId");

        if((userId == null && adminId == null) || (userId != null && adminId != null)) {
            req.getSession().invalidate();
            resp.sendRedirect("index.html");
            return;
        }

        List<Orders> list = orderService.list(userId, adminId);
        String json = jsonUtils.toOrderJson(list);
        resp.getWriter().write(json);
        req.setAttribute("orders", json);

        if(userId != null && userId > 0) {
//            System.out.println("Test of User...... " + userId);
            req.getRequestDispatcher("/WEB-INF/UserOrder.jsp").forward(req, resp);
        }
        else if(adminId != null && adminId > 0) {
//            System.out.println("Test of Admin...... " + adminId);
            req.getRequestDispatcher("/WEB-INF/AdminOrder.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if(path == null || path.equals("")) {
            resp.getWriter().write(Result.error("未知错误"));
            return;
        }

        switch (path) {
            case "/buy":
                buyTicket(req, resp);
                break;
            default:
                resp.getWriter().write(Result.error("域名错误"));
                break;
        }

    }

    private void buyTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");

        if(userId == null) {
            resp.getWriter().write(Result.error("请先登录"));
            return;
        }

        Long movieId = Long.parseLong(req.getParameter("movieId"));
        Long seatId = Long.parseLong(req.getParameter("seatId"));

        Long number = orderService.add(userId, movieId, seatId);

        switch (number.toString()) {
            case "0":
                resp.getWriter().write(Result.error("电影信息未找到"));
                break;
            case "1":
                resp.getWriter().write(Result.error("座位已被占用"));
                break;
            case "2":
                resp.getWriter().write(Result.success());
                break;
            default:
                resp.getWriter().write(Result.error("未知错误"));
                break;
        }

//        if(number > 0) {
//            resp.getWriter().write(Result.success());
//        } else if (number == -1){
//            resp.getWriter().write(Result.error("请更换座位号"));
//        } else {
//            resp.getWriter().write(Result.error("购票失败"));
//        }
    }
}
