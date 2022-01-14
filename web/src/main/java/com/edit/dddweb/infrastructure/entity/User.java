package com.edit.dddweb.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * (User)实体类
 *
 * @author bingco
 * @since 2022-01-13 19:28:27
 */
@Data
@TableName("t_user")
public class User {

    private Integer id;

    private String user;
    
    private String passwd;
    
    private Integer roleId;
    
    private String nickname;

}

