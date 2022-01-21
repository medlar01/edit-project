package com.edit.dddweb.application.service;

import com.edit.dddweb.interfaces.common.Result;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.task.Task;

import java.util.List;

public interface FlowService {
    /**
     * 模型定义查询分页
     * @param current 当前页
     * @param size 数据量
     * @param name 模型名称
     * @return 模型定义数据集
     */
    Result<List<Model>> modelLimit(int current, int size, String name);

    boolean removeModelByIds(List<String> ids);

    /**
     * 部署流程
     * @param id 流程Id
     * @return 部署结果
     */
    boolean deploy(String id);

    /**
     * 流程定义查询分页
     * @param current 当前页
     * @param size 数据量
     * @param name 流程名称
     * @return 流程定义结果集
     */
    Result<List<Deployment>> deployLimit(Integer current, Integer size, String name);

    /**
     * 删除流程定义
     * @param ids 流程定义Id
     * @return 执行结果
     */
    boolean removeDeployByIds(List<String> ids);

    /**
     * 申请流程
     * @param id 刘晨定义Id
     * @return 申请结果
     */
    String createTask(String id);

    Result<List<Task>> mineTask(Integer current, Integer size);

    /**
     * 删除任务
     * @param ids 任务id
     * @return 删除结果
     */
    boolean removeTaskByIds(List<String> ids);
}
