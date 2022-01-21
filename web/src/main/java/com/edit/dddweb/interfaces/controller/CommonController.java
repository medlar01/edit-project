package com.edit.dddweb.interfaces.controller;

import com.edit.dddweb.application.service.CommonService;
import com.edit.dddweb.interfaces.common.Result;
import com.edit.dddweb.interfaces.dto.BasicSearchDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RestController
public class CommonController {

    @Resource
    private CommonService commonService;

    @GetMapping("table")
    public Result<List<BasicSearchDTO>> tableLimit(@RequestParam Long current, @RequestParam Long size,
               @RequestParam Map<String, Serializable> prams) {
        return commonService.tableLimit(current, size, prams);
    }
}
