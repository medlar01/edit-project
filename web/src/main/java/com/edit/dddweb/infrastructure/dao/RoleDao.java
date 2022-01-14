package com.edit.dddweb.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edit.dddweb.infrastructure.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Role)表数据库访问层
 *
 * @author bingco
 * @since 2022-01-15 01:59:07
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {

}

