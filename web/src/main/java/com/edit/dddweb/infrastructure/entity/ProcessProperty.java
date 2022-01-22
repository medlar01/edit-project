package com.edit.dddweb.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ACT_RE_PROPERTY")
public class ProcessProperty {
    @TableId("ID_")
    private Integer id;
    @TableField("TABLE_")
    private String table;
    @TableField("INST_TITLE_")
    private String instTitle;
    @TableField("PROC_DEF_ID_")
    private String procDefinitionId;
    @TableField("DETAIL_TABLES_")
    private String detailTables;
}
