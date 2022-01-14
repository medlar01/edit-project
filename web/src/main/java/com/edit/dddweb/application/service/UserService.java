package com.edit.dddweb.application.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edit.dddweb.infrastructure.entity.User;
import com.edit.dddweb.interfaces.common.Result;
import com.edit.dddweb.interfaces.dto.UserDTO;

import java.io.Serializable;
import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-01-11 02:47:25
 */
public interface UserService extends IService<User> {

    String login(String user, String passwd);

    UserDTO getUser(Serializable id);

    Result<List<UserDTO>> findLimit(Page<User> of, User user);
}

