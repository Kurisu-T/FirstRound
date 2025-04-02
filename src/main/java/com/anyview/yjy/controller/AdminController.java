package com.anyview.yjy.controller;

import com.anyview.yjy.entity.Admin;
import com.anyview.yjy.entity.Movie;
import com.anyview.yjy.entity.Orders;
import com.anyview.yjy.entity.User;
import com.anyview.yjy.service.AdminService;
import com.anyview.yjy.service.MovieService;
import com.anyview.yjy.service.OrderService;
import com.anyview.yjy.service.UserService;
import com.anyview.yjy.utils.DTO.AdminLoginDTO;
import com.anyview.yjy.utils.DataUtils.ParseData;
import com.anyview.yjy.utils.VO.AdminLoginVO;
import com.anyview.yjy.utils.VO.AdminRegisterVO;
import com.anyview.yjy.utils.result.MyResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.anyview.yjy.utils.code.USER_BAN;
import static com.anyview.yjy.utils.code.USER_NORMAL;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {

    AdminService adminService = new AdminService();
    UserService userService = new UserService();
    OrderService orderService = new OrderService();
    MovieService movieService = new MovieService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(); // 获取session

        /*
            获取 session 存储的用户和管理员id，这里我通过获取的id判断权限，代替请求头
            后面不再赘述
         */
        Long adminId = (Long) session.getAttribute("adminId");
        Long userId = (Long) session.getAttribute("userId");
        if(adminId == null){
            resp.sendRedirect("index.html");
            return;
        }
        Admin admin = adminService.getById(adminId);
        if(admin == null){
            resp.sendRedirect("index.html");
            return;
        }

        String path = req.getPathInfo();
        switch (path) {
            case "/data":
                getById(req, resp);
                return;
            case "/userList":
                getUserList(req, resp);
                break;
            case "/orderList":
                getOrderList(req, resp, userId, adminId);
                break;
            case "/movieList":
                getMovieList(req, resp);
                break;
            default:
                // MyResult: 自己写的一个返回响应结果的工具类，没有使用Jackson
                resp.getWriter().write(MyResult.error("域名错误"));
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();

        if(path == null || path.isEmpty()){
            /*
                域名错误，跳转404页面
                这里只是个人的一个尝试，为了了解resp中的api
                后面的代码会通过响应 ERROR 的 JSON 数据代替这种方式
             */
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        switch (path){
            case "/login":
                userLogin(req, resp);
                break;
            case "/register":
                userRegister(req, resp);
                break;
            case "/update":
                userUpdate(req, resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        switch (path){
            case "/updateStatus":
                updateUserStatus(req, resp);
                break;
            default:
                resp.getWriter().write(MyResult.error("域名错误"));
                break;
        }
    }

    /**
     * 更改用户状态
     * @param req
     * @param resp
     */
    private void updateUserStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = ParseData.getData(req);
        Long userId = Long.parseLong(data.get("userId").toString());
        Integer status = Integer.parseInt(data.get("status").toString());

        User user = userService.getById(userId);
        if(user == null){
            resp.getWriter().write(MyResult.error("用户信息不存在"));
            return;
        }

        if(status == 0) user.setStatus(USER_BAN);   // 拉黑
        else user.setStatus(USER_NORMAL);           // 正常

        userService.update(user);

        resp.getWriter().write(MyResult.success());
    }

    /**
     * 根据id查询管理员
     * @param req
     * @param resp
     * @throws IOException
     */
    private void getById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long adminId = (Long) req.getSession().getAttribute("adminId");
        if(adminId == null){
            resp.getWriter().write(MyResult.error("未登录"));
            return;
        }
        Admin admin = adminService.getById(adminId);
        if(admin == null){
            resp.getWriter().write(MyResult.error("管理员不存在"));
            return;
        }
        resp.getWriter().write(MyResult.success(admin));
    }

    /**
     * 更新管理员信息
     * @param req
     * @param resp
     * @throws IOException
     */
    private void userUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Long adminId = (Long) req.getSession().getAttribute("adminId");
        Admin admin = adminService.getById(adminId);
        if(admin == null){
            resp.getWriter().write(MyResult.error("管理员不存在"));
            return ;
        }

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        admin.setName(name);
        admin.setPhone(phone);
        admin.setPassword(password);

        if(admin.getName().isEmpty() || admin.getPhone().isEmpty() || admin.getPassword().isEmpty()){
            resp.getWriter().write(MyResult.error("修改失败"));
        } else {
            adminService.update(admin);
            req.getSession().setAttribute("adminId", admin.getId());
            resp.getWriter().write(MyResult.success());
        }
    }

    /**
     * 注册接口
     * @param req
     * @param resp
     */
    private void userRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Admin admin = new Admin();

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        if(adminService.getByPhone(phone) != null) {
            resp.getWriter().write(MyResult.error("管理员已存在"));
            return;
        }

        admin.setName(name);
        admin.setPhone(phone);
        admin.setPassword(password);

        AdminRegisterVO vo = adminService.register(admin);

        resp.getWriter().write(vo == null ? MyResult.error("注册失败") : MyResult.success(vo));
    }

    /**
     * 登录接口
     * @param req
     * @param resp
     * @throws IOException
     */
    private void userLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        AdminLoginDTO admin = new AdminLoginDTO();
        admin.setPhone(phone);
        admin.setPassword(password);

        AdminLoginVO vo = adminService.login(admin);
        if(vo == null){
            resp.getWriter().write(MyResult.error("手机号或密码错误"));
        } else {
            Long adminId = vo.getId();

            /* 清空session，刷新权限，同样的，在用户登录时也会进行这个操作
                  确保只 session 只有管理员或用户有一个id
                  防止产生权限冲突，后面不再赘述
             */
            req.getSession().invalidate();
            req.getSession().setAttribute("adminId", adminId);

            resp.getWriter().write(MyResult.success(vo));
        }
    }

    /**
     * 获取用户信息
     * @param req
     * @param resp
     */
    private void getUserList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<User> list = userService.list();
        resp.getWriter().write(MyResult.success(list));
    }

    /**
     * 获取订单信息
     * @param req
     * @param resp
     */
    private void getOrderList(HttpServletRequest req, HttpServletResponse resp, Long userId, Long adminId) throws IOException {
        List<Orders>list = orderService.list(userId, adminId);
        resp.getWriter().write(MyResult.success(list));
    }

    /**
     * 获取电影信息
     * @param req
     * @param resp
     */
    private void getMovieList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Movie> list = movieService.listByAdmin();
        resp.getWriter().write(MyResult.success(list));
    }
}
