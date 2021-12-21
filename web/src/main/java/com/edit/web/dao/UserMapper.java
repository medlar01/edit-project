package com.edit.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edit.web.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
}
