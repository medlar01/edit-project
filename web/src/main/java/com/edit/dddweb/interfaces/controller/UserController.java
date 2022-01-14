package com.edit.dddweb.interfaces.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edit.dddweb.application.service.UserService;
import com.edit.dddweb.infrastructure.entity.User;
import com.edit.dddweb.interfaces.common.Result;
import com.edit.dddweb.interfaces.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

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
     * @param current 当前页
     * @param size 数据量
     * @param user 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result<List<UserDTO>> page(Long current, Long size, User user) {
        return this.userService.findLimit(Page.of(current, size), user);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<UserDTO> one(@PathVariable Serializable id) {
        return Result.success(this.userService.getUser(id));
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
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam("ids") List<Long> ids) {
        return this.userService.removeByIds(ids) ? Result.SUCCESS : Result.ERROR;
    }
}

