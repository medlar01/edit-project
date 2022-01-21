package com.edit.dddweb.interfaces.controller;

import com.edit.dddweb.application.service.FlowService;
import com.edit.dddweb.interfaces.common.Result;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
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

    /**
     * 删除流程定义
     * @param ids 流程Id
     * @return 执行结果
     */
    @DeleteMapping("deploy")
    public Result<String> removeDeployByIds(@RequestParam List<String> ids) {
        return this.flowService.removeDeployByIds(ids) ? Result.SUCCESS : Result.ERROR;
    }

    /**
     * 创建流程实例
     * @param id 流程定义Id
     * @return 执行结果
     */
    @GetMapping("proc-inst/{id}")
    public Result<String> createProcInstance(@PathVariable String id) {
        String taskId = this.flowService.createProcInstance(id);
        return taskId != null ? Result.success(taskId) : Result.ERROR;
    }

    /**
     * 分页查询我的流程实例
     * @param current 当前页
     * @param size 数据量
     * @return 执行结果
     */
    @GetMapping("proc-inst/self")
    public Result<List<ProcessInstance>> procInstanceSelf(Integer current, Integer size) {
        return this.flowService.procInstanceSelf(current, size);
    }

    /**
     * 删除流程实例
     * @param ids 流程实例Id
     * @return 执行结果
     */
    @DeleteMapping("proc-inst")
    public Result<String> removeProcInstanceByIds(@RequestParam List<String> ids) {
        return this.flowService.removeProcInstanceByIds(ids) ? Result.SUCCESS : Result.ERROR;
    }
}
