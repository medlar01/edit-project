package com.edit.lib.bus.entity;

import com.baomidou.mybatisplus.annotation.TableField;

public class EditInfo {
    private Integer id;
    private String uid;
    @TableField("`table`")
    private String table;
    private String actId;
    private String title;
    private Byte isMain;

    public EditInfo() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getActId() {
        return this.actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getIsMain() {
        return this.isMain;
    }

    public void setIsMain(Byte isMain) {
        this.isMain = isMain;
    }
}
