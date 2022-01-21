package com.edit.dddweb.application.service;

import com.edit.dddweb.interfaces.common.Result;
import com.edit.dddweb.interfaces.dto.BasicSearchDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CommonService {
    /**
     * 分页查询表信息
     * @param current 当前页
     * @param size 数据量
     * @param prams 查询参数
     * @return 结果集
     */
    Result<List<BasicSearchDTO>> tableLimit(Long current, Long size, Map<String, Serializable> prams);

}
