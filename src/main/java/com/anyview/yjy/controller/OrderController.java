package com.anyview.yjy.controller;

import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.entity.Orders;
import com.anyview.yjy.entity.User;
import com.anyview.yjy.service.MovieService;
import com.anyview.yjy.service.OrderService;
import com.anyview.yjy.service.UserService;
import com.anyview.yjy.utils.DataUtils.ParseData;
import com.anyview.yjy.utils.RedisUtils.JedisUtils;
import com.anyview.yjy.utils.result.MyResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.anyview.yjy.utils.code.*;

@WebServlet("/order/*")
public class OrderController extends HttpServlet {
    OrderService orderService = new OrderService();
    UserService userService = new UserService();
    MovieService movieService = new MovieService();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        Long adminId = (Long) req.getSession().getAttribute("adminId");

        if((userId == null && adminId == null) || (userId != null && adminId != null)) {
            req.getSession().invalidate();
            resp.getWriter().write(MyResult.error("请尝试重新登录"));
            return;
        }

        String path = req.getPathInfo();

        switch (path) {
            case "/list":
                userOrderList(req, resp, userId, adminId);
                break;
            case "/manage":
                adminOrderList(req, resp, userId, adminId);
                break;
            case "/detail":
                getDetail(req, resp);
                break;
            case "/cancel":
                getCancelApply(req, resp);
                break;
            default:
                resp.getWriter().write(MyResult.error("域名错误"));
                break;
        }

//        if(userId != null && userId > 0) {
//            System.out.println("Test of User...... " + userId);
//            req.getRequestDispatcher("/WEB-INF/UserOrder.jsp").forward(req, resp);
//        }
//        else if(adminId != null && adminId > 0) {
//            System.out.println("Test of Admin...... " + adminId);
//            req.getRequestDispatcher("/WEB-INF/AdminOrder.jsp").forward(req, resp);
//        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if(path == null || path.equals("")) {
            resp.getWriter().write(MyResult.error("未知错误"));
            return;
        }

        switch (path) {
            case "/buy":
                buyTicket(req, resp);
                break;
            case "/pay":
                pay(req, resp);
                break;
            case "/cancel":
                cancel(req, resp);
                break;
            default:
                resp.getWriter().write(MyResult.error("域名错误"));
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if(path == null || path.equals("")) {
            resp.getWriter().write(MyResult.error("未知错误"));
            return;
        }

        if(path.startsWith("/cancelOrder")) {
            manageOrder(req, resp);
        } else {
            resp.getWriter().write(MyResult.error("域名错误"));
        }
    }

    /**
     * 处理退款申请
     * @param req
     * @param resp
     */
    private void manageOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = ParseData.getData(req);
        Long orderId = Long.parseLong(data.get("orderId").toString());
        Long status = Long.parseLong(data.get("status").toString());
        if(orderId == null || status == null) {
            resp.getWriter().write(MyResult.error("请求获取失败"));
            return;
        }
        Orders order = orderService.getById(orderId);
        if(order == null) {
            resp.getWriter().write(MyResult.error("订单获取失败"));
            return;
        }

        if(order.getStatus() != ORDER_WAIT) {
            resp.getWriter().write(MyResult.error("订单未支付或已处理"));
            return;
        }
        if(status == 0) {
            order.setStatus(ORDER_CANCEL);
            User user = userService.getById(order.getUserId());
            user.setMoney(user.getMoney() + order.getPrice());
            userService.update(user);

            Movie movie = movieService.adminGetById(order.getMovie());
            movie.setAmount(movie.getAmount() + 1);
            movieService.update(movie);

        } else if(status == 1) {
            order.setStatus(ORDER_REJECT);
        }
        orderService.update(order);
        resp.getWriter().write(MyResult.success());
    }

    /**
     * 获取所有申请取消的订单
     * @param req
     * @param resp
     */
    private void getCancelApply(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Orders>list = orderService.getCancelApply();
        resp.getWriter().write(MyResult.success(list));
    }

    /**
     * 用户申请取消订单
     * @param req
     * @param resp
     */
    private void cancel(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long orderId = Long.parseLong(req.getParameter("orderId"));
        Orders order = orderService.getById(orderId);
        if(order == null) {
            resp.getWriter().write(MyResult.error("未查询到订单消息"));
            return;
        }
        if(order.getStatus() != ORDER_PAID) {
            resp.getWriter().write(MyResult.error("订单已处理或无法退款"));
            return;
        }
        order.setStatus(ORDER_WAIT);
        orderService.update(order);
        resp.getWriter().write(MyResult.success());
    }

    /**
     * 支付订单
     * @param req
     * @param resp
     */
    private void pay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long UserId = (Long) req.getSession().getAttribute("userId");
        Long orderId = Long.parseLong(req.getParameter("orderId"));
        if(UserId == null || orderId == null) {
            resp.getWriter().write(MyResult.error("订单或用户信息有误"));
            return;
        }
        Orders order = new Orders();
        order = orderService.getById(orderId);
        if(order == null) {
            resp.getWriter().write(MyResult.error("未查询到订单信息"));
            return;
        }
        if(order.getStatus() != ORDER_UNPAID) {
            resp.getWriter().write(MyResult.error("订单无需支付"));
            return;
        }
        User user = new User();
        user = userService.getById(UserId);
        if(user == null) {
            resp.getWriter().write(MyResult.error("未查询到用户信息"));
            return;
        }
        if(user.getMoney() < order.getPrice()) {
            resp.getWriter().write(MyResult.error("账户余额不足"));
            return;
        }
        user.setMoney(user.getMoney() - order.getPrice());
        order.setStatus(ORDER_PAID);
        userService.update(user);
        orderService.update(order);
        resp.getWriter().write(MyResult.success());
    }

    /**
     * 获取订单详细
     * @param req
     * @param resp
     */
    private void getDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long orderId = Long.parseLong(req.getParameter("orderId"));
        Orders order = orderService.getById(orderId);
        if(order == null) {
            resp.getWriter().write(MyResult.error("未查询到订单信息"));
        } else {
            resp.getWriter().write(MyResult.success(order));
        }
    }

    /**
     * 用户获取订单历史
     * @param req
     * @param resp
     * @param userId
     * @param adminId
     * @throws IOException
     */
    private void userOrderList(HttpServletRequest req, HttpServletResponse resp, Long userId, Long adminId) throws IOException {
        List<Orders> list = orderService.list(userId, adminId);
        resp.getWriter().write(MyResult.success(list));
//        req.setAttribute("orders", json);
    }

    /**
     * 管理订单
     * @param req
     * @param resp
     * @param userId
     * @param adminId
     */
    private void adminOrderList(HttpServletRequest req, HttpServletResponse resp, Long userId, Long adminId) throws IOException {
        List<Orders> list = orderService.list(userId, adminId);
//        String json = jsonUtils.toOrderJson(list);
        resp.getWriter().write(MyResult.success(list));
    }


    /**
     * 购票
     * @param req
     * @param resp
     * @throws IOException
     */
    private void buyTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");

        if(userId == null) {
            resp.getWriter().write(MyResult.error("请先登录"));
            return;
        }

        Long movieId = Long.parseLong(req.getParameter("movieId"));
        Long seatId = Long.parseLong(req.getParameter("seatId"));

        System.out.println("create lock");
        Jedis jedis = JedisUtils.getJedis();
        System.out.println("lock had created");
        Long lock = jedis.setnx(LOCK + movieId, "");
        if(lock == null || lock <= 0) {
            resp.getWriter().write(MyResult.error("购票失败"));
            return;
        }
        jedis.expire(LOCK + movieId, 10);

        Integer number = orderService.add(userId, movieId, seatId);

        if(number.equals(MOVIE_NO_FIND)) {
            resp.getWriter().write(MyResult.error("电影信息未找到"));
        } else if(number.equals(SEAT_NOT_NULL)) {
            resp.getWriter().write(MyResult.error("座位已被占用"));
        } else if(number.equals(BUY_FAIL)) {
            resp.getWriter().write(MyResult.error("未知错误"));
        } else {
            resp.getWriter().write(MyResult.success(number));
        }
        jedis.del(LOCK + movieId);

//        if(number > 0) {
//            resp.getWriter().write(MyResult.success());
//        } else if (number == -1){
//            resp.getWriter().write(MyResult.error("请更换座位号"));
//        } else {
//            resp.getWriter().write(MyResult.error("购票失败"));
//        }
    }
}
