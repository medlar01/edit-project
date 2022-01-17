package com.edit.dddweb.interfaces.controller;

import com.edit.dddweb.application.service.FlowService;
import com.edit.dddweb.interfaces.common.Result;
import org.activiti.engine.repository.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("flowable")
public class FlowController {

    @Resource
    private FlowService flowService;

    /**
     * 分页查询流程定义数据
     *
     * @param current 当前页
     * @param size    数据量
     * @return 所有数据
     */
    @GetMapping("model")
    public Result<List<Model>> modelLimit(Integer current, Integer size) {
        return this.flowService.modelLimit(current, size);
    }

    /**
     * 删除流程定义数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("model")
    public Result<String> delete(@RequestParam("ids") List<String> ids) {
        return this.flowService.removeModelByIds(ids) ? Result.SUCCESS : Result.ERROR;
    }
}
