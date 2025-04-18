package com.anyview.yjy.controller;

import com.anyview.yjy.entity.User;
import com.anyview.yjy.service.OrderService;
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

import static com.anyview.yjy.utils.code.USER_NORMAL;


@WebServlet("/users/*")
public class UserController extends HttpServlet {

    UserService userService = new UserService();
    OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();
        if(path.equals("/data")){
            getById(req, resp);
            return;
        } else if(path.equals("/movieList")) {
            getMovieShow(req, resp);
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
     * 获取即将放映的电影
     *
     *  这里只会返回还没有放映的电影，已经放映过的电影不会展示出来
     *
     * @param req
     * @param resp
     */
    private void getMovieShow(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        Integer number = orderService.getMovieShow(userId);
        if(number == 0) resp.getWriter().write(MyResult.error("Zero"));
        else resp.getWriter().write(MyResult.success(number));
    }

    /**
     * 登录成功后展示用户信息
     * @param req
     * @param resp
     * @throws IOException
     */
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

    /**
     * 修改用户信息
     * @param req
     * @param resp
     */
    private void userUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Long id = (Long) req.getSession().getAttribute("userId");
        User user = userService.getById(id);

        if(user == null){
            resp.getWriter().write(MyResult.error("用户不存在"));
            return;
        }

        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        Integer money = Integer.valueOf(req.getParameter("money"));

        user.setName(name);
        user.setPhone(phone);
        user.setMoney(money);
        user.setPassword(password);
        user.setStatus(USER_NORMAL);

        if(user.getName().isEmpty() || user.getPhone().isEmpty() || user.getPassword().isEmpty()){
            resp.getWriter().write(MyResult.error("修改失败"));
        } else {
            userService.update(user);
            req.getSession().setAttribute("userId", user.getId());
            resp.getWriter().write(MyResult.success());
        }
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
        } else {
            Long userId = userVO.getId();

            req.getSession().invalidate();
            req.getSession().setAttribute("userId", userId);

            resp.getWriter().write(MyResult.success(userVO));
        }
    }
}
