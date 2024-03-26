package com.shark.shortlink.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shark.shortlink.admin.dao.entity.UserDO;
import com.shark.shortlink.admin.dto.req.UserRegisterReqDTO;
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
}
