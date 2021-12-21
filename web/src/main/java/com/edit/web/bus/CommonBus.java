package com.edit.web.bus;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.edit.web.dao.UserMapper;
import com.edit.web.po.UserPO;
import com.google.common.collect.ImmutableMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Calendar;
import java.util.List;

@Service
public class CommonBus {
    private static final String SIGN = "@Q1W2S##1XZC";
    private static final Logger log = LogManager.getLogger(CommonBus.class);
    private UserMapper userMapper;

    /**
     * 系统登录
     */
    public String login(String user, String passwd) {
        List<UserPO> list = userMapper.selectByMap(ImmutableMap.<String, Object>builder()
                .put("user", user)
                .build());
        if (list.size() == 0) {
            log.error("登录失败: 用户不存在~");
            return null;
        }

        UserPO dbUser = list.get(0);
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

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
