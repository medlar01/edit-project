package com.edit.lib.bus;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.util.JdbcUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edit.lib.bus.entity.EditInfo;
import com.edit.lib.bus.entity.FieldInfo;
import com.edit.lib.bus.entity.NodeInfo;
import com.edit.lib.bus.mapper.EditInfoMapper;
import com.edit.lib.bus.mapper.FieldInfoMapper;
import com.edit.lib.bus.mapper.NodeInfoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class EditBus {

    @Value("${edit.cli-dir}")
    private String dir;

    private EditInfoMapper editInfoMapper;
    private FieldInfoMapper fieldInfoMapper;
    private NodeInfoMapper nodeInfoMapper;

    public HashMap<String, Serializable> getMataInfo(String actId) {
        List<EditInfo> list = this.editInfoMapper.selectList(new QueryWrapper<EditInfo>()
                .eq("act_id", actId));
        EditInfo editInfo = list.stream().filter((it) -> it.getIsMain() == 1)
                .findFirst()
                .orElse(null);
        Assert.notNull(editInfo, "未找到主表信息~");
        HashMap<String, Serializable> map = new HashMap<>();
        map.put("main", this.loadFieldInfo(editInfo));
        ArrayList<Serializable> lines = new ArrayList<>();
        map.put("lines", lines);
        list.stream().filter((it) -> it.getIsMain() != 1)
                .forEach((it) -> lines.add(this.loadFieldInfo(it)));
        return map;
    }

    private HashMap<String, Serializable> loadFieldInfo(EditInfo editInfo) {
        HashMap<String, Serializable> map = new HashMap<>();
        map.put("_", editInfo.getId());
        map.put("id", editInfo.getUid());
        map.put("table_name", editInfo.getTable());
        map.put("table_comment", editInfo.getTitle());
        List<FieldInfo> fieldList = this.fieldInfoMapper.selectList(new QueryWrapper<FieldInfo>()
                .eq("edit_info_id", editInfo.getId()));
        map.put("fields", new ArrayList<>(fieldList.stream().map(this::fieldToMap).collect(Collectors.toList())));
        return map;
    }

    private HashMap<String, Serializable> fieldToMap(FieldInfo i) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Serializable> field = new HashMap<>();
        field.put("_", i.getId());
        field.put("id", i.getUid());
        field.put("pk", i.getPk());
        field.put("name", i.getField());
        field.put("comment", i.getComment());
        field.put("category", i.getCategory());
        field.put("format", i.getFormat());
        if (i.getCategory().equals("dialog")) {
            try {
                field.put("options", objectMapper.readValue(i.getOptions(), LinkedHashMap.class));
            } catch (Exception ignore) {}
        } else {
            field.put("options", i.getOptions());
        }
        return field;
    }

    public Serializable getBehavior(String actId, String nodeId) {
        NodeInfo nodeInfo = this.nodeInfoMapper.selectOne(new QueryWrapper<NodeInfo>()
                .eq("act_id", actId)
                .eq("node_id", nodeId));
        HashMap<String, Serializable> map = new HashMap<>();
        map.put("events", nodeInfo.getEvents());
        map.put("dialogs", nodeInfo.getDialogs());
        map.put("content", nodeInfo.getContent());
        return map;
    }

    @SuppressWarnings("unchecked")
    public void save(String actId, String nodeId, Map<String, ?> params) {
        this.saveTableInfo((Map<String, ?>) params.get("tableInfo"));
        this.saveBehavior(actId, nodeId, (Map<String, ?>) params.get("behavior"));
    }

    @SuppressWarnings("unchecked")
    private void saveTableInfo(Map<String, ?> tableInfo) {
        List<Map<String, Serializable>> mainFields = (List<Map<String, Serializable>>)
                ((Map<String, ?>) tableInfo.get("main")).get("fields");
        this.saveFields(mainFields);
        List<Map<String, ?>> lines = (List<Map<String, ?>>)tableInfo.get("lines");
        lines.forEach((it) -> {
            Integer id = (Integer)it.get("_");
            EditInfo editInfo = this.editInfoMapper.selectById(id);
            editInfo.setUid((String)it.get("id"));
            this.editInfoMapper.updateById(editInfo);
            List<Map<String, Serializable>> lineFields = (List<Map<String, Serializable>>) it.get("fields");
            this.saveFields(lineFields);
        });
    }

    private void saveFields(List<Map<String, Serializable>> fields) {
        ObjectMapper objectMapper = new ObjectMapper();
        int[] ids = fields.stream().mapToInt((it) -> (Integer)it.get("_"))
                .toArray();
        List<FieldInfo> list = this.fieldInfoMapper.selectBatchIds(Arrays.asList(ArrayUtils.toObject(ids)));
        fields.forEach((it) -> {
            Integer id = (Integer)it.get("_");
            FieldInfo fieldInfo = list.stream()
                    .filter((i) -> Objects.equals(i.getId(), id))
                    .findAny()
                    .orElse(null);
            Assert.notNull(fieldInfo, "字段【" + it.get("name") + "】不存在数据库中~");
            fieldInfo.setUid(it.get("id").toString());
            fieldInfo.setCategory(it.get("category").toString());
            fieldInfo.setFormat((String) it.get("format"));
            if (fieldInfo.getCategory().equals("dialog")) {
                try {
                    fieldInfo.setOptions(objectMapper.writeValueAsString(it.get("options")));
                } catch (Exception ignore) {}
            } else {
                fieldInfo.setOptions((String)it.get("options"));
            }
            this.fieldInfoMapper.updateById(fieldInfo);
        });
    }

    private void saveBehavior(String actId, String nodeId, Map<String, ?> behavior) {
        NodeInfo nodeInfo = this.nodeInfoMapper.selectOne(new QueryWrapper<NodeInfo>()
                .eq("act_id", actId)
                .eq("node_id", nodeId));
        Assert.notNull(nodeInfo, "该节点【" + nodeId + "】不存在数据库中~");
        nodeInfo.setContent((String)behavior.get("content"));
        nodeInfo.setEvents((String)behavior.get("events"));
        nodeInfo.setDialogs((String)behavior.get("dialogs"));
        this.nodeInfoMapper.updateById(nodeInfo);
    }

    public Map<String, ?> dialogInfo(String actId, String nodeId, String id) throws JsonProcessingException {
        return this.getDialog(actId, nodeId, id);
    }

    @SuppressWarnings("unchecked")
    private Map<String, ?> getDialog(String actId, String nodeId, String id) throws JsonProcessingException {
        NodeInfo nodeInfo = this.nodeInfoMapper.selectOne(new QueryWrapper<NodeInfo>()
                .eq("act_id", actId)
                .eq("node_id", nodeId));
        Assert.notNull(nodeInfo, "该节点【" + nodeId + "】不存在数据库中~");
        String dialogs = nodeInfo.getDialogs();
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, ?>> list = (List<Map<String, ?>>)mapper.readValue(dialogs, List.class);
        return list.stream()
                .filter((it) -> it.get("id").equals(id))
                .findAny()
                .orElse(new HashMap<>());
    }

    public Map<String, ?> execSql(String actId, String nodeId, String id, Map<String, Serializable> params) throws JsonProcessingException {
        Map<String, ?> dialogsMap = this.getDialog(actId, nodeId, id);
        String sql = (String)dialogsMap.get("sql");
        @SuppressWarnings("unchecked")
        List<String> attrs = (List<String>)params.get("params");
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, JdbcUtils.MYSQL);
        List<SQLStatement> stmtList = parser.parseStatementList();
        SQLSelectStatement statement = (SQLSelectStatement)stmtList.get(0);
        SQLSelectQueryBlock query = (SQLSelectQueryBlock)statement.getSelect().getQuery();
        SQLExpr sqlExpr = query.getWhere();
        List<Sqler> cached = new ArrayList<>();
        this.parseSql(sqlExpr, cached);

        for(int index = 0; index < cached.size(); ++index) {
            Sqler sqler = cached.get(index);
            String attr = attrs.get(index);
            if (sqler.matches("['|\"]?[$][%]?(arg1|arg2)[%]?[$]['|\"]?") && (StringUtils.isBlank(attr) || attr.matches("[%]{1,2}"))) {
                sqler.remove();
            } else {
                attr = attr == null ? "null" : String.format("'%s'", attr);
                SQLExprParser spr = SQLParserUtils.createExprParser(attr, JdbcUtils.MYSQL);
                SQLExpr expr = spr.expr();
                sqler.replace(expr);
            }
        }

        final IPage<Map<String, ?>> page = this.nodeInfoMapper.execSql(query.toString(), Page.of((long) params.get("current"), (long) params.get("pageSize")));
        return new HashMap<String, Serializable>() {
            {
                this.put("total", page.getTotal());
                this.put("list", page.getRecords().size() > 0 ? (ArrayList<Map<String, ?>>) page.getRecords() : null);
            }
        };
    }

    private void parseSql(SQLExpr sqlExpr, List<Sqler> cached) {
        for (SQLObject child : sqlExpr.getChildren()) {
            Sqler sqler;
            if (child instanceof SQLIdentifierExpr) {
                sqler = new Sqler(child, sqlExpr);
                if (sqler.matches("[$].+[$]")) {
                    cached.add(sqler);
                }
            } else if (child instanceof SQLCharExpr) {
                sqler = new Sqler(child, sqlExpr);
                if (sqler.matches("[$][%]?.+[%]?[$]")) {
                    cached.add(sqler);
                }
            } else {
                this.parseSql((SQLExpr) child, cached);
            }
        }
    }

    public void gen(Map<String, String> params) throws IOException {
        String os = System.getProperties().getProperty("os.name");
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.dir + "/build-cli/src/gen.vue"));
        writer.write(params.get("html"));
        writer.close();
        if (os.startsWith("Window")) {
            ScriptUtil.exec(Arrays.asList("cmd /c", "cd /d", this.dir + "/build-cli", "&&", "npm run lib"));
        }

        if (os.equals("Linux") || os.startsWith("Mac")) {
            ScriptUtil.exec(Arrays.asList("/bin/bash", this.dir + "/build-cli/build.sh " + this.dir + "/build-cli"));
        }
    }

    public void loadLib(String actId, String nodeId, HttpServletResponse response) throws IOException {
        response.addHeader("content-type", "application/javascript; charset=utf-8");
        PrintWriter writer = response.getWriter();
        BufferedReader reader = new BufferedReader(new FileReader(this.dir + "/build-cli/lib/lib.umd.js"));
        String tmp;
        while((tmp = reader.readLine()) != null) {
            writer.println(tmp);
        }
        reader.close();
        writer.flush();
    }

    @Autowired
    public void setEditInfoMapper(EditInfoMapper editInfoMapper) {
        this.editInfoMapper = editInfoMapper;
    }

    @Autowired
    public void setFieldInfoMapper(FieldInfoMapper fieldInfoMapper) {
        this.fieldInfoMapper = fieldInfoMapper;
    }

    @Autowired
    public void setNodeInfoMapper(NodeInfoMapper nodeInfoMapper) {
        this.nodeInfoMapper = nodeInfoMapper;
    }
}
