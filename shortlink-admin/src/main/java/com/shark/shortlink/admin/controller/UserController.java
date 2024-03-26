package com.shark.shortlink.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import com.shark.shortlink.admin.common.convention.result.Result;
import com.shark.shortlink.admin.common.convention.result.Results;
import com.shark.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.shark.shortlink.admin.dto.resp.UserActualRespDTO;
import com.shark.shortlink.admin.dto.resp.UserRespDTO;
import com.shark.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/short-link/admin/v1/")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;//相当于注入了
    /**
     * 根据用户名查询信息 敏感信息脱敏...
     * @param username
     * @return
     */
    @GetMapping("/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username){
        return Results.success(userService.getUserByUsername(username));
    }

    @GetMapping("/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username){
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username),UserActualRespDTO.class));
    }

    /**
     * 查询用户是否存在
     * @param username
     * @return
     */
    @GetMapping("/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUsername(username));
    }

    @PostMapping("/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO userRegisterReqDTO){
        userService.register(userRegisterReqDTO);
        return Results.success();
    }



}
