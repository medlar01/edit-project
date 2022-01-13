package com.edit.dddweb.application.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edit.dddweb.application.service.UserService;
import com.edit.dddweb.infrastructure.dao.UserDao;
import com.edit.dddweb.infrastructure.entity.User;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Calendar;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-01-11 02:47:25
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    private static final String SIGN = "@Q1W2S##1XZC";

    @Override
    public String login(String user, String passwd) {
        List<User> list = this.baseMapper.selectByMap(ImmutableMap.<String, Object>builder()
                .put("user", user)
                .build());
        if (list.size() == 0) {
            log.error("登录失败: 用户不存在~");
            return null;
        }

        User dbUser = list.get(0);
        String hexPasswd = DigestUtils.md5DigestAsHex(passwd.getBytes());
        if (!dbUser.getPasswd().equals(hexPasswd)) {
            log.error("登录失败: 用户密码错误~");
            return null;
        }

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR_OF_DAY, 3);
        return JWT.create()
                .withClaim("id", dbUser.getId())
                .withClaim("user", dbUser.getUser())
                .withClaim("roleId", dbUser.getRoleId())
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SIGN));
    }
}

