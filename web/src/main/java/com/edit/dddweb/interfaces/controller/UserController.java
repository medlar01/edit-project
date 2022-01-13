package com.edit.dddweb.interfaces.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edit.dddweb.application.service.UserService;
import com.edit.dddweb.infrastructure.entity.User;
import com.edit.dddweb.interfaces.common.Result;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2022-01-11 02:14:06
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @PostMapping("login")
    public Result<String> login(@RequestParam String user, @RequestParam String passwd) {
        String token = userService.login(user, passwd);
        return token == null ? Result.error("登录失败") : Result.success(token);
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param user 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result<List<User>> page(Page<User> page, User user) {
        IPage<User> p = this.userService.page(page, new QueryWrapper<>(user));
        return Result.page(p.getTotal(), p.getRecords());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<User> one(@PathVariable Serializable id) {
        return Result.success(this.userService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param user 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result<Integer> insert(@RequestBody User user) {
        return this.userService.save(user) ? Result.success(user.getId()) : Result.error("新增失败");
    }

    /**
     * 修改数据
     *
     * @param user 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result<String> update(@RequestBody User user) {
        return this.userService.updateById(user) ? Result.SUCCESS : Result.ERROR;
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam("idList") List<Long> idList) {
        return this.userService.removeByIds(idList) ? Result.SUCCESS : Result.ERROR;
    }
}

