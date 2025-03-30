package com.anyview.yjy.service;

import com.anyview.yjy.dao.UserDao;
import com.anyview.yjy.entity.User;
import com.anyview.yjy.utils.DTO.UserLoginDTO;
import com.anyview.yjy.utils.VO.UserLoginVO;
import com.anyview.yjy.utils.VO.UserRegisterVO;

public class UserService {
    private UserDao userDao = new UserDao();

    /**
     * 用户登录
     * @param user
     * @return
     */
    public UserLoginVO login(UserLoginDTO user) {
        return userDao.login(user);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    public UserRegisterVO register(User user) {

        userDao.saveUser(user);

        UserRegisterVO userVO = new UserRegisterVO();
        userVO.setName(user.getName());
        userVO.setPhone(user.getPhone());

        return userVO;
    }

    /**
     * 根据id获取用户
     * @param userId
     * @return
     */
    public User getById(Long userId) {
        return userDao.getById(userId);
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void update(User user) {
        userDao.update(user);
    }

    public UserLoginVO getByPhone(String phone) {
        return userDao.getByPhone(phone);
    }
}
