package com.edit.lib.bus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edit.lib.bus.entity.NodeInfo;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Map;

public interface NodeInfoMapper extends BaseMapper<NodeInfo> {

    @SelectProvider(type = ExecSqlProvider.class, method = "execSql")
    IPage<Map<String,?>> execSql(String sql, Page<Object> page);

    public final class ExecSqlProvider {
        public ExecSqlProvider() {
        }
        public String execSql(String sql) {
            return sql;
        }
    }
}
