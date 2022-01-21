package com.edit.dddweb.interfaces.controller;

import com.edit.dddweb.application.service.FlowService;
import com.edit.dddweb.interfaces.common.Result;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("flowable")
public class FlowController {

    @Resource
    private FlowService flowService;

    /**
     * 分页查询模型定义数据
     *
     * @param current 当前页
     * @param size    数据量
     * @return 所有数据
     */
    @GetMapping("model")
    public Result<List<Model>> modelLimit(Integer current, Integer size, @RequestParam(required = false) String name) {
        return this.flowService.modelLimit(current, size, name);
    }

    /**
     * 删除模型定义数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("model")
    public Result<String> delete(@RequestParam("ids") List<String> ids) {
        return this.flowService.removeModelByIds(ids) ? Result.SUCCESS : Result.ERROR;
    }

    /**
     * 部署流程
     * @param id 模型Id
     * @return 部署结果
     */
    @GetMapping("deploy/{id}")
    public Result<String> deploy(@PathVariable String id) {
        return flowService.deploy(id) ? Result.SUCCESS : Result.ERROR;
    }

    /**
     * 分页查询流程定义数据
     *
     * @param current 当前页
     * @param size    数据量
     * @return 所有数据
     */
    @GetMapping("deploy")
    public Result<List<Deployment>> deployLimit(Integer current, Integer size, @RequestParam(required = false) String name) {
        return this.flowService.deployLimit(current, size, name);
    }

    @DeleteMapping("deploy")
    public Result<String> removeDeployByIds(@RequestParam List<String> ids) {
        return this.flowService.removeDeployByIds(ids) ? Result.SUCCESS : Result.ERROR;
    }

    @GetMapping("task/{id}")
    public Result<String> createTask(@PathVariable String id) {
        String taskId = this.flowService.createTask(id);
        return taskId != null ? Result.success(taskId) : Result.ERROR;
    }

    @GetMapping("task/self")
    public Result<List<Task>> myTask(Integer current, Integer size) {
        return this.flowService.mineTask(current, size);
    }

    @DeleteMapping("task")
    public Result<String> removeTaskByIds(@RequestParam List<String> ids) {
        return this.flowService.removeTaskByIds(ids) ? Result.SUCCESS : Result.ERROR;
    }
}
