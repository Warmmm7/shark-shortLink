package com.shark.shortlink.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shark.shortlink.admin.dao.entity.UserDO;
import com.shark.shortlink.admin.dto.req.UserLoginReqDTO;
import com.shark.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.shark.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.shark.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.shark.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return 用户返回实体
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 查询用户名是否存在...
     * @param username
     * @return
     */
    Boolean hasUsername(String username);

    /**
     * 注册用户请求
     * @param userRegisterReqDTO
     */
    void register(UserRegisterReqDTO userRegisterReqDTO);

    /**
     * 更新用户信息 根据用户名
     * @param userUpdateReqDTO
     */
    void update(UserUpdateReqDTO userUpdateReqDTO);

    /**
     * 登陆功能
     * @param userLoginReqDTO
     * @return 用户登陆返回token
     */
    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);

    /**
     * 检测用户是否登陆了
     * @param username
     * @param token
     * @return
     */
    Boolean checkLogin(String username, String token);

    /**
     * 退出登陆
     * @param username
     * @param token
     */
    void logout(String username, String token);

}
