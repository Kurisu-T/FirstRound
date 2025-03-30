package com.anyview.yjy.controller;

import com.anyview.yjy.entity.Admin;
import com.anyview.yjy.entity.User;
import com.anyview.yjy.service.AdminService;
import com.anyview.yjy.service.UserService;
import com.anyview.yjy.utils.DTO.AdminLoginDTO;
import com.anyview.yjy.utils.VO.AdminLoginVO;
import com.anyview.yjy.utils.VO.AdminRegisterVO;
import com.anyview.yjy.utils.jsonUtils;
import com.anyview.yjy.utils.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/*")
public class AdminController extends HttpServlet {

    AdminService adminService = new AdminService();
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Long adminId = (Long) session.getAttribute("adminId");
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
            default:
                resp.getWriter().write(Result.error("域名错误"));
                break;
        }

//        req.getSession().setAttribute("admin", admin);
//        req.getRequestDispatcher("/admin.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();

        if(path == null || path.isEmpty()){
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

    /**
     * 根据id查询管理员
     * @param req
     * @param resp
     * @throws IOException
     */
    private void getById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long adminId = (Long) req.getSession().getAttribute("adminId");
        if(adminId == null){
            resp.getWriter().write(Result.error("未登录"));
            return;
        }
        Admin admin = adminService.getById(adminId);
        if(admin == null){
            resp.getWriter().write(Result.error("管理员不存在"));
            return;
        }
        resp.getWriter().write(Result.success(admin));
    }

    /**
     * 更新管理员信息
     * @param req
     * @param resp
     * @throws IOException
     */
    private void userUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        Admin admin = (Admin) req.getSession().getAttribute("admin");
//
//        String name = req.getParameter("name");
//        String phone = req.getParameter("phone");
//        String password = req.getParameter("password");
//
//        admin.setName(name);
//        admin.setPhone(phone);
//        admin.setPassword(password);

        Long adminId = (Long) req.getSession().getAttribute("adminId");
        Admin admin = adminService.getById(adminId);
        if(admin == null){
            resp.getWriter().write(Result.error("管理员不存在"));
            return ;
        }

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        admin.setName(name);
        admin.setPhone(phone);
        admin.setPassword(password);

        if(admin.getName().isEmpty() || admin.getPhone().isEmpty() || admin.getPassword().isEmpty()){
            resp.getWriter().write(Result.error("修改失败"));
            return;
        } else {
            adminService.update(admin);
            req.getSession().setAttribute("adminId", admin.getId());
            resp.getWriter().write(Result.success());
        }
//        resp.getWriter().write(jsonUtils.toJson(admin));
//        resp.sendRedirect("/admin");
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
            resp.getWriter().write(Result.error("管理员已存在"));
            return;
        }

        admin.setName(name);
        admin.setPhone(phone);
        admin.setPassword(password);

        AdminRegisterVO vo = adminService.register(admin);

        resp.getWriter().write(vo == null ? Result.error("注册失败") : Result.success(vo));
//        resp.sendRedirect("/");
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
            resp.getWriter().write(Result.error("手机号或密码错误"));
//            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            Long adminId = vo.getId();

            req.getSession().invalidate();
            req.getSession().setAttribute("adminId", adminId);

            resp.getWriter().write(Result.success(vo));
//            resp.sendRedirect("/admin");
        }
    }

    /**
     * 获取用户信息
     * @param req
     * @param resp
     */
    private void getUserList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<User> list = userService.list();
        resp.getWriter().write(Result.success(list));
    }
}
