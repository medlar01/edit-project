package com.edit.dddweb.application.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edit.dddweb.application.assembler.CommonConverter;
import com.edit.dddweb.application.service.CommonService;
import com.edit.dddweb.infrastructure.dao.CommonDAO;
import com.edit.dddweb.interfaces.common.Result;
import com.edit.dddweb.interfaces.dto.BasicSearchDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonDAO commonDAO;

    @Override
    public Result<List<BasicSearchDTO>> tableLimit(Long current, Long size, Map<String, Serializable> prams) {
        IPage<Map<String, Serializable>> pg = commonDAO.tableLimit(Page.of(current, size), prams);
        return Result.page(pg.getTotal(), CommonConverter.INST.toBsDTO(pg.getRecords()));
    }
}
