package com.edit.dddweb.application.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edit.dddweb.infrastructure.dao.RoleDao;
import com.edit.dddweb.infrastructure.entity.Role;
import com.edit.dddweb.application.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2022-01-15 01:59:31
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

}

