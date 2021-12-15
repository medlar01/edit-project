package com.edit.lib.bus.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("edit_node_info")
public class NodeInfo {
    private Integer id;
    private String actId;
    private String nodeId;
    private String events;
    private String dialogs;
    private String content;
    private String template;

    public NodeInfo() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActId() {
        return this.actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getEvents() {
        return this.events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getDialogs() {
        return this.dialogs;
    }

    public void setDialogs(String dialogs) {
        this.dialogs = dialogs;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
