package com.edit.dddweb.application.service.impl;

import com.edit.dddweb.application.service.FlowService;
import com.edit.dddweb.interfaces.common.Result;
import com.google.common.collect.Lists;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("flowService")
public class FlowServiceImpl implements FlowService {

    @Resource
    public ProcessEngine engine;

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
}
