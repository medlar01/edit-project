package com.edit.dddweb.interfaces.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edit.dddweb.application.service.RoleService;
import com.edit.dddweb.infrastructure.entity.Role;
import com.edit.dddweb.interfaces.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Role)表控制层
 *
 * @author bingco
 * @since 2022-01-15 02:04:32
 */
@RestController
@RequestMapping("role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;

    /**
     * 分页查询所有数据
     *
     * @param current 当前页
     * @param size 数据量
     * @param role 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result<List<Role>> page(Long current, Long size, Role role) {
        IPage<Role> pg = this.roleService.page(Page.of(current, size), new QueryWrapper<>(role));
        return Result.page(pg.getTotal(), pg.getRecords());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<Role> get(@PathVariable Serializable id) {
        return Result.success(this.roleService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param role 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result<Integer> insert(@RequestBody Role role) {
        return this.roleService.save(role) ? Result.success(role.getId()) : Result.error("新增失败");
    }

    /**
     * 修改数据
     *
     * @param role 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result<String> update(@RequestBody Role role) {
        return this.roleService.updateById(role) ? Result.SUCCESS : Result.ERROR;
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result<String> delete(@RequestParam("ids") List<Long> ids) {
        return this.roleService.removeByIds(ids) ? Result.SUCCESS : Result.ERROR;
    }
}

