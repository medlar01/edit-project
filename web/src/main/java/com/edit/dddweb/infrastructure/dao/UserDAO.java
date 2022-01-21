package com.edit.dddweb.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edit.dddweb.infrastructure.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)表数据库访问层
 *
 * @author bingco
 * @since 2022-01-11 01:55:37
 */
@Mapper
public interface UserDAO extends BaseMapper<User> {

}

