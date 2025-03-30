package com.anyview.yjy.controller;

import com.anyview.yjy.entity.Orders;
import com.anyview.yjy.service.OrderService;
import com.anyview.yjy.utils.jsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    OrderService orderService = new OrderService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        Long adminId = (Long) req.getSession().getAttribute("adminId");

        if((userId == null && adminId == null) || (userId != null && adminId != null)) {
            req.getSession().invalidate();
            resp.sendRedirect("index.jsp");
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
}
