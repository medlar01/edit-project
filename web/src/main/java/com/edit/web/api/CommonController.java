package com.edit.web.api;

import com.edit.web.bus.CommonBus;
import com.edit.web.vo.JsonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    private CommonBus commonBus;

    @PostMapping("login")
    public JsonVO<String> login(@RequestParam String user, @RequestParam String passwd) {
        String token = commonBus.login(user, passwd);
        if (token == null) {
            return JsonVO.fail("登录失败");
        }
        return JsonVO.success(token);
    }

    @Autowired
    public void setCommonBus(CommonBus commonBus) {
        this.commonBus = commonBus;
    }
}
