package com.edit.dddweb.application.service;

import com.edit.dddweb.interfaces.common.Result;
import org.activiti.engine.repository.Model;

import java.util.List;

public interface FlowService {
    /**
     * 流程定义查询分页
     * @param current 当前页
     * @param size 数据量
     * @param name 流程名称
     * @return 流程定义数据集
     */
    Result<List<Model>> modelLimit(int current, int size, String name);

    boolean removeModelByIds(List<String> ids);
}
