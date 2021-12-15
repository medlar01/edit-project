package com.edit.lib.api;

import com.edit.lib.bus.EditBus;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${edit.base-url:editjs}")
public class EditApi {

    private EditBus editBus;

    @GetMapping({"mata-info/{actId}/{nodeId}"})
    public Map<String, Serializable> mataInfo(@PathVariable String actId, @PathVariable String nodeId) {
        Map<String, Serializable> ret = new HashMap<>();
        ret.put("tableInfo", this.editBus.getMataInfo(actId));
        ret.put("behavior", this.editBus.getBehavior(actId, nodeId));
        return ret;
    }

    @PutMapping({"/mata-info/save/{actId}/{nodeId}"})
    public void save(@PathVariable String actId, @PathVariable String nodeId, @RequestBody Map<String, ?> params) {
        this.editBus.save(actId, nodeId, params);
    }

    @PostMapping({"/dialog-info/{id}"})
    public Map<String, ?> dialogInfo(@CookieValue("act-id") String actId, @CookieValue("node-id") String nodeId, @PathVariable String id) throws JsonProcessingException {
        return this.editBus.dialogInfo(actId, nodeId, id);
    }

    @PostMapping({"execSql/{id}"})
    public Map<String, ?> execSql(@CookieValue("act-id") String actId, @CookieValue("node-id") String nodeId, @PathVariable String id, @RequestBody Map<String, Serializable> params) throws JsonProcessingException {
        return this.editBus.execSql(actId, nodeId, id, params);
    }

    @PostMapping({"gen"})
    public String gen(@RequestBody Map<String, String> params) throws IOException {
        this.editBus.gen(params);
        return "OK";
    }

    @GetMapping({"gen/{actId}/{nodeId}.js"})
    public void loadLib(@PathVariable String actId, @PathVariable String nodeId, HttpServletResponse response) throws IOException {
        this.editBus.loadLib(actId, nodeId, response);
    }

    @Autowired
    public final void setEditBus(EditBus bus) {
        this.editBus = bus;
    }
}
