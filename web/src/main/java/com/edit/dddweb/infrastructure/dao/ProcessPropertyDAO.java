package com.edit.dddweb.infrastructure.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edit.dddweb.infrastructure.entity.ProcessProperty;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProcessPropertyDAO extends BaseMapper<ProcessProperty> {
}
