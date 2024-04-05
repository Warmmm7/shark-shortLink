package com.shark.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shark.shortlink.admin.common.convention.exception.ClientException;
import com.shark.shortlink.admin.common.convention.exception.ServiceException;
import com.shark.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.shark.shortlink.admin.dao.entity.UserDO;
import com.shark.shortlink.admin.dao.mapper.UserMapper;
import com.shark.shortlink.admin.dto.req.UserLoginReqDTO;
import com.shark.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.shark.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.shark.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.shark.shortlink.admin.dto.resp.UserRespDTO;
import com.shark.shortlink.admin.service.GroupService;
import com.shark.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.shark.shortlink.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static com.shark.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;
import static com.shark.shortlink.admin.common.enums.UserErrorCodeEnum.*;

/**
 * 用户接口实现层
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    public final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    public final RedissonClient redissonClient;
    public final StringRedisTemplate stringRedisTemplate;
    public final GroupService groupService;

    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ServiceException(UserErrorCodeEnum.USER_NULL);
        }
        return BeanUtil.copyProperties(userDO, UserRespDTO.class);

    }

    @Override
    public Boolean hasUsername(String username) {
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO userRegisterReqDTO) {
        if (!hasUsername(userRegisterReqDTO.getUsername())) {
            throw new ClientException(USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + userRegisterReqDTO.getUsername());
        try {
            if (lock.tryLock()) {//防止短时间内大量用户同一注册
                try {
                    int inserted = baseMapper.insert(BeanUtil.toBean(userRegisterReqDTO, UserDO.class));
                    if (inserted < 1) {
                        throw new ClientException(USER_SAVE_ERROR);
                    }
                } catch (DuplicateKeyException ex) {
                    throw new ClientException(USER_EXIST);
                }
                userRegisterCachePenetrationBloomFilter.add(userRegisterReqDTO.getUsername());
                groupService.saveGroup(userRegisterReqDTO.getUsername(),"默认分组");
                return;
            }
            throw new ClientException(USER_NAME_EXIST);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 更新用户信息
     * @param userUpdateReqDTO
     */
    public void update(UserUpdateReqDTO userUpdateReqDTO) {
        //TODO 验证当前登陆用户是否为登陆用户
        LambdaQueryWrapper<UserDO> updateWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, userUpdateReqDTO.getUsername());
        baseMapper.update(BeanUtil.toBean(userUpdateReqDTO,UserDO.class),updateWrapper);
    }

    /**
     * 用户登陆
     * @param userLoginReqDTO
     * @return
     */
    public UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, userLoginReqDTO.getUsername())
                .eq(UserDO::getPassword, userLoginReqDTO.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null){
            throw new ClientException("用户不存在");
        }
        /**
         * Hashmap
         * key: 用户名...
         * VALUE:
         *  KEY:TOKE
         *  Val:用户信息 toJson
         */
        Map<Object, Object> hasLoginMap = stringRedisTemplate.opsForHash()
                .entries(USER_LOGIN_KEY + userLoginReqDTO.getUsername());
        if (CollUtil.isNotEmpty(hasLoginMap)) {//不为空 刷新时间
            stringRedisTemplate.expire(USER_LOGIN_KEY + userLoginReqDTO.getUsername(), 30L, TimeUnit.MINUTES);
            String token = hasLoginMap.keySet().stream()//获取 Hash 中的第一个键（即 token）
                    .findFirst()
                    .map(Object::toString)
                    .orElseThrow(() -> new ClientException("用户登录错误"));
            return new UserLoginRespDTO(token);
        }

        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put(USER_LOGIN_KEY + userLoginReqDTO.getUsername(), uuid, JSON.toJSONString(userDO));
        stringRedisTemplate.expire(USER_LOGIN_KEY + userLoginReqDTO.getUsername(), 30L, TimeUnit.MINUTES);
        return new UserLoginRespDTO(uuid);
    }

    /**
     * 用户是否登陆
     * @param username
     * @param token
     * @return
     */
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY + username, token) != null;
    }

    /**
     * 退出登录
     * @param username
     * @param token
     */
    public void logout(String username, String token) {
        if (checkLogin(username,token)){
            stringRedisTemplate.delete(USER_LOGIN_KEY + username);
            return;
        }
        throw new ClientException("用户Token不存在或用户未登录");
    }

}
