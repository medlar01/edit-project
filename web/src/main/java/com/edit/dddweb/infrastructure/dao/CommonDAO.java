package com.edit.dddweb.infrastructure.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edit.dddweb.interfaces.dto.BasicSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommonDAO {
    @SelectProvider(type = CommonProvider.class, method = "tables")
    IPage<BasicSearchDTO> tableLimit(Page<Object> page, @Param("prams") Map<String, Serializable> prams);


    final class CommonProvider {
        public String tables(Map<String, Object> pw) {
            @SuppressWarnings("unchecked")
            Map<String, Serializable> prams = (Map<String, Serializable>) pw.get("prams");
            final StringBuilder sbr = new StringBuilder("select table_name as code, table_comment as name, '' as `desc` from " +
                    "information_schema.tables where table_schema = 'editjs' and table_name not like 'ACT_%'");
            if (isNotBlank(prams.get("code"))) {
                sbr.append(" and table_name like '%${prams.code}%'");
            }
            if (isNotBlank(prams.get("name"))) {
            sbr.append(" and table_comment like '%${prams.name}%'");
            }
            return sbr.toString();
        }
    }

    static boolean isNotBlank(Serializable val) {
        if (val == null) return false;
        return (!(val instanceof String) || ((String) val).trim().isBlank());
    }
}
