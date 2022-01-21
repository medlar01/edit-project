package com.edit.dddweb.application.service.impl;

import com.edit.dddweb.application.service.FlowService;
import com.edit.dddweb.interfaces.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service("flowService")
public class FlowServiceImpl implements FlowService {

    @Resource
    private ProcessEngine engine;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public Result<List<Model>> modelLimit(int current, int size, String name) {
        ModelQuery query = engine.getRepositoryService().createModelQuery()
                .orderByCreateTime()
                .desc();
        if (StringUtils.isNotBlank(name)) {
            query.modelNameLike("%" + name + "%");
        }
        long count = query.count();
        List<Model> list = query.listPage(Math.max(0, (current - 1) * size), size);
        return Result.page(count, list);
    }

    @Override
    public boolean removeModelByIds(List<String> ids) {
        if (ids == null || ids.size() == 0) return true;
        RepositoryService service = engine.getRepositoryService();
        for (String id : ids) {
            service.deleteModel(id);
        }
        return true;
    }

    @Override
    public boolean deploy(String id) {
        RepositoryService repositoryService = engine.getRepositoryService();
        Model model = repositoryService.createModelQuery()
                .modelId(id)
                .singleResult();
        assert model != null : "模型不存在~";
        byte[] binary = repositoryService.getModelEditorSource(id);
        try {
            BpmnModel bpmnModel = new BpmnJsonConverter()
                    .convertToBpmnModel(objectMapper.readTree(binary));
            Deployment deploy = repositoryService.createDeployment()
                    .name(model.getName())
                    .enableDuplicateFiltering()
                    .addBpmnModel("bpmn20.xml", bpmnModel)
                    .deploy();
            return deploy != null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Result<List<Deployment>> deployLimit(Integer current, Integer size, String name) {
        DeploymentQuery query = engine.getRepositoryService()
                .createDeploymentQuery()
                .orderByDeploymenTime()
                .desc();
        if (StringUtils.isNotBlank(name)) {
            query.deploymentNameLike('%' + name + '%');
        }
        long count = query.count();
        List<Deployment> list = query.listPage(Math.max(0, (current - 1) * size), size);
        return Result.page(count, list);
    }

    @Override
    public boolean removeDeployByIds(List<String> ids) {
        if (ids == null || ids.size() == 0) return true;
        RepositoryService repositoryService = engine.getRepositoryService();
        for (String id : ids) {
            repositoryService.deleteDeployment(id);
        }
        return true;
    }

    @Override
    public String createTask(String id) {
        ProcessDefinition processDefinition = engine.getRepositoryService()
                .createProcessDefinitionQuery()
                .deploymentId(id)
                .singleResult();
        assert processDefinition != null : "未找到该流程定义~";
        ProcessInstance processInstance = engine.getRuntimeService()
                .startProcessInstanceByKey(processDefinition.getKey());
        return processInstance.getId();
    }

    @Override
    public Result<List<Task>> mineTask(Integer current, Integer size) {
        TaskQuery query = engine.getTaskService()
                .createTaskQuery()
                .orderByTaskCreateTime()
                .desc();
        long count = query.count();
        List<Task> list = query.listPage(Math.max(0, (current - 1) * size), size);
        return Result.page(count, list);
    }

    @Override
    public boolean removeTaskByIds(List<String> ids) {
        if (ids == null || ids.size() == 0) return true;
        TaskService taskService = engine.getTaskService();
        for (String id : ids) {
            taskService.complete(id);
            taskService.deleteTask(id, true);
        }
        return true;
    }
}
