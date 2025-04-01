package com.anyview.yjy.service;

import com.anyview.yjy.dao.AdminDao;
import com.anyview.yjy.entity.Admin;
import com.anyview.yjy.utils.DTO.AdminLoginDTO;
import com.anyview.yjy.utils.VO.AdminLoginVO;
import com.anyview.yjy.utils.VO.AdminRegisterVO;

public class AdminService {
    private AdminDao adminDao = new AdminDao();

    /**
     * 用户登录
     * @param admin
     * @return
     */
    public AdminLoginVO login(AdminLoginDTO admin) {
        AdminLoginVO vo = adminDao.login(admin);
        return vo;
    }

    /**
     * 用户注册
     * @param admin
     * @return
     */
    public AdminRegisterVO register(Admin admin) {

        adminDao.saveAdmin(admin);

        AdminRegisterVO vo = new AdminRegisterVO();
        vo.setName(admin.getName());
        vo.setPhone(admin.getPhone());

        return vo;
    }

    /**
     * 根据id获取管理员信息
     * @param adminId
     * @return
     */
    public Admin getById(Long adminId) {
        return adminDao.getById(adminId);
    }

    /**
     * 更新管理员信息
     * @param admin
     */
    public void update(Admin admin) {
        adminDao.update(admin);
    }

    public AdminLoginVO getByPhone(String phone) {
        return adminDao.getByPhone(phone);
    }
}
