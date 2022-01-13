package com.edit.dddweb.application.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edit.dddweb.infrastructure.entity.User;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-01-11 02:47:25
 */
public interface UserService extends IService<User> {

    String login(String user, String passwd);
}

