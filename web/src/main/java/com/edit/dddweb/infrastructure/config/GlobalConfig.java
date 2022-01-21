package com.edit.dddweb.infrastructure.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Configuration
public class GlobalConfig implements InitializingBean {
    @Resource
    private ObjectMapper objectMapper;
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Deployment.class, new DeploySerializer());
        simpleModule.addSerializer(ProcessDefinition.class, new ProcessDefSerializer());
        simpleModule.addSerializer(ProcessInstance.class, new ProcessInstSerializer());
        simpleModule.addSerializer(Task.class, new TaskSerializer());
        objectMapper.registerModule(simpleModule);
    }

    public static final class DeploySerializer extends JsonSerializer<Deployment> {
        @Override
        public void serialize(Deployment d, JsonGenerator g, SerializerProvider s) throws IOException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            g.writeStartObject();
            g.writeStringField("id", d.getId());
            g.writeStringField("key", d.getKey());
            g.writeStringField("name", d.getName());
            g.writeStringField("category", d.getCategory());
            g.writeStringField("tenantId", d.getTenantId());
            g.writeStringField("deploymentTime", format.format(d.getDeploymentTime()));
            g.writeEndObject();
        }
    }

    public static final class ProcessDefSerializer extends JsonSerializer<ProcessDefinition> {
        @Override
        public void serialize(ProcessDefinition p, JsonGenerator g, SerializerProvider s) throws IOException {
            g.writeStartObject();
            g.writeStringField("id", p.getId());
            g.writeStringField("key", p.getKey());
            g.writeStringField("name", p.getName());
            g.writeNumberField("version", p.getVersion());
            g.writeStringField("category", p.getCategory());
            g.writeStringField("tenantId", p.getTenantId());
            g.writeStringField("deploymentId", p.getDeploymentId());
            g.writeStringField("description", p.getDescription());
            g.writeStringField("diagramResourceName", p.getDiagramResourceName());
            g.writeStringField("engineVersion", p.getEngineVersion());
            g.writeStringField("resourceName", p.getResourceName());
            g.writeEndObject();
        }
    }

    public static final class ProcessInstSerializer extends JsonSerializer<ProcessInstance> {
        @Override
        public void serialize(ProcessInstance p, JsonGenerator g, SerializerProvider s) throws IOException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            g.writeStartObject();
            g.writeStringField("id", p.getId());
            g.writeStringField("name", p.getName());
            g.writeStringField("businessKey", p.getBusinessKey());
            g.writeStringField("initiator", p.getInitiator());
            g.writeStringField("processDefinitionId", p.getProcessDefinitionId());
            g.writeStringField("parentId", p.getParentId());
            g.writeStringField("processDefinitionKey", p.getProcessDefinitionKey());
            g.writeNumberField("processDefinitionVersion", p.getProcessDefinitionVersion());
            g.writeStringField("startDate", format.format(p.getStartDate()));
            g.writeNumberField("status", p.getStatus().ordinal());
            g.writeEndObject();
        }
    }

    public static final class TaskSerializer extends JsonSerializer<Task> {
        @Override
        public void serialize(Task t, JsonGenerator g, SerializerProvider s) throws IOException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            g.writeStartObject();
            g.writeStringField("id", t.getId());
            g.writeStringField("name", t.getName());
            g.writeStringField("category", t.getCategory());
            g.writeStringField("description", t.getDescription());
            g.writeStringField("tenantId", t.getTenantId());
            g.writeStringField("assignee", t.getAssignee());
            g.writeStringField("executionId", t.getExecutionId());
            g.writeStringField("formKey", t.getFormKey());
            g.writeStringField("owner", t.getOwner());
            g.writeStringField("parentTaskId", t.getParentTaskId());
            g.writeStringField("processDefinitionId", t.getProcessDefinitionId());
            g.writeStringField("processInstanceId", t.getProcessInstanceId());
            g.writeStringField("taskDefinitionKey", t.getTaskDefinitionKey());
            g.writeNumberField("delegationState", Optional.ofNullable(t.getDelegationState())
                    .map(Enum::ordinal)
                    .orElse(-1));
            g.writeStringField("claimTime", Optional.ofNullable(t.getClaimTime())
                    .map(format::format)
                    .orElse(null));
            g.writeStringField("dueDate", Optional.ofNullable(t.getDueDate())
                    .map(format::format)
                    .orElse(null));
            g.writeStringField("createTime", Optional.ofNullable(t.getCreateTime())
                    .map(format::format)
                    .orElse(null));
            g.writeEndObject();
        }
    }
}
