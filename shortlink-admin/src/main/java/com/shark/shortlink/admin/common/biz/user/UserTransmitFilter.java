package com.shark.shortlink.admin.common.biz.user;


import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;

import static com.shark.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

/**
 * 用户信息传输过滤器
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String username = httpServletRequest.getHeader("username");
        String token = httpServletRequest.getHeader("token");
        Object  userInfoJsonStr= stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY + username,token);
        if (userInfoJsonStr !=null ){
            UserInfoDTO userInfoDTO = JSON.parseObject(userInfoJsonStr.toString(), UserInfoDTO.class);
            UserContext.setUser(userInfoDTO);
        }
        //String userId = httpServletRequest.getHeader(UserConstant.USER_ID_KEY);
//        if (StringUtils.hasText(userId)) {
//            String userName = httpServletRequest.getHeader(UserConstant.USER_NAME_KEY);
//            String realName = httpServletRequest.getHeader(UserConstant.REAL_NAME_KEY);
//            if (StringUtils.hasText(userName)) {
//                userName = URLDecoder.decode(userName, UTF_8);
//            }
//            if (StringUtils.hasText(realName)) {
//                realName = URLDecoder.decode(realName, UTF_8);
//            }
//            String token = httpServletRequest.getHeader(UserConstant.USER_TOKEN_KEY);
//            UserInfoDTO userInfoDTO = UserInfoDTO.builder()
//                    .userId(userId)
//                    .username(userName)
//                    .realName(realName)
//                    .token(token)
//                    .build();
//            UserContext.setUser(userInfoDTO);
//        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            UserContext.removeUser();
        }
    }
}