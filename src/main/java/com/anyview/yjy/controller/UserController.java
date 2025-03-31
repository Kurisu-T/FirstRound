package com.anyview.yjy.controller;

import com.anyview.yjy.entity.User;
import com.anyview.yjy.service.UserService;
import com.anyview.yjy.utils.DTO.UserLoginDTO;
import com.anyview.yjy.utils.VO.UserLoginVO;
import com.anyview.yjy.utils.VO.UserRegisterVO;
import com.anyview.yjy.utils.result.MyResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
// TODO 身份验证拦截器
@WebServlet("/users/*")
public class UserController extends HttpServlet {

    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();
        if(path.equals("/data")){
            getById(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if(userId == null){
            resp.sendRedirect("index.html");
            return;
        }
        User user = userService.getById(userId);
        if(user == null){
            resp.sendRedirect("index.html");
            return;
        }
        req.getSession().setAttribute("user", user);
        req.getRequestDispatcher("/user.html").forward(req, resp);
    }

    private void getById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        if(userId == null){
            resp.getWriter().write(MyResult.error("未登录"));
            return;
        }
        User user = userService.getById(userId);
        if(user == null){
            resp.getWriter().write(MyResult.error("用户不存在"));
            return;
        }
        resp.getWriter().write(MyResult.success(user));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();    //获取子路径
//
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
     * 修改用户信息
     * @param req
     * @param resp
     */
    private void userUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        User user = (User) req.getSession().getAttribute("user");
//        req.getSession().invalidate();
//
//        String name = req.getParameter("name");
//        String phone = req.getParameter("phone");
//        String password = req.getParameter("password");
//
//        user.setName(name);
//        user.setPhone(phone);
//        user.setPassword(password);

        Long id = (Long) req.getSession().getAttribute("userId");
        User user = userService.getById(id);

        if(user == null){
            resp.getWriter().write(MyResult.error("用户不存在"));
            return;
        }

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        user.setName(name);
        user.setPhone(phone);
        user.setPassword(password);

        if(user.getName().isEmpty() || user.getPhone().isEmpty() || user.getPassword().isEmpty()){
            resp.getWriter().write(MyResult.error("修改失败"));
            return;
        } else {
            userService.update(user);
            req.getSession().setAttribute("userId", user.getId());
            resp.getWriter().write(MyResult.success());
        }
//        resp.getWriter().write(MyResult.success());
//        resp.sendRedirect("/users");
    }


    /**
     * 注册接口
     * @param req
     * @param resp
     */
    private void userRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User();

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        if(userService.getByPhone(phone) != null){
            resp.getWriter().write(MyResult.error("用户已存在"));
            return;
        }

        user.setName(name);
        user.setPhone(phone);
        user.setPassword(password);

        UserRegisterVO vo = userService.register(user);

        resp.getWriter().write(vo == null ? MyResult.error("注册失败") : MyResult.success(vo));
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

        UserLoginDTO user = new UserLoginDTO();
        user.setPhone(phone);
        user.setPassword(password);

        UserLoginVO userVO = userService.login(user);
        if(userVO == null){
            resp.getWriter().write(MyResult.error("手机号或密码错误"));
//            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            Long userId = userVO.getId();

            req.getSession().invalidate();
            req.getSession().setAttribute("userId", userId);

            resp.getWriter().write(MyResult.success(userVO));
//            resp.sendRedirect("/users");
        }
    }
}
