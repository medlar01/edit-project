package com.edit.dddweb.infrastructure.entity;

import java.util.Date;
import lombok.Data;

/**
 * (Role)实体类
 *
 * @author bingco
 * @since 2022-01-15 03:28:29
 */
@Data
public class Role {
    /**
     * ID
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 创建人
     */
    private Integer createBy;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 修改人
     */
    private Integer modifyBy;
    /**
     * 修改日期
     */
    private Date modifyDate;
}

