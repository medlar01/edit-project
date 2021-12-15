package com.edit.lib.bus.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("edit_field_info")
public class FieldInfo {
    private Integer id;
    private String uid;
    private String field;
    private String comment;
    private Byte pk;
    private String category;
    private String format;
    private String options;
    private Integer editInfoId;

    public FieldInfo() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Byte getPk() {
        return this.pk;
    }

    public void setPk(Byte pk) {
        this.pk = pk;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getOptions() {
        return this.options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getEditInfoId() {
        return this.editInfoId;
    }

    public void setEditInfoId(Integer editInfoId) {
        this.editInfoId = editInfoId;
    }
}
