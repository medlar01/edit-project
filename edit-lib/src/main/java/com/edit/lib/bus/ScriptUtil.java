package com.edit.lib.bus;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ScriptUtil {
    public static void exec(List<String> others) throws IOException {
        String cmd = StringUtils.join(others, " ");
        System.out.println("指令：" + cmd);
        Process process = Runtime.getRuntime().exec(cmd);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String str;
        while((str = reader.readLine()) != null) {
            System.out.println("编译信息：" + str);
        }

        try {
            int i = process.waitFor();
            System.out.println("执行结果：" + i);
        } catch (InterruptedException var6) {
            var6.printStackTrace();
        }

        reader.close();
    }
}
