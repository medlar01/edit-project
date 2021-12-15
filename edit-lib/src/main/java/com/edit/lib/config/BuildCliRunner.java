package com.edit.lib.config;

import cn.hutool.core.util.ZipUtil;
import com.edit.lib.bus.ScriptUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

public class BuildCliRunner implements ApplicationRunner {
    @Value("${edit.cli-dir}")
    private String dir;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String os = System.getProperties().getProperty("os.name");
        System.out.println("系统：" + os + ", " + System.getProperties().getProperty("os.arch"));
        System.out.println("==== 开始创建 build-cli ====");
        File file = new File(this.dir);
        if (!file.exists()) {
            boolean var4 = file.mkdirs();
        }

        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream ism = classLoader.getResourceAsStream("cli.zip");

        assert ism != null;

        ZipUtil.unzip(ism, file, Charset.defaultCharset());
        ism.close();
        if (os.startsWith("Window")) {
            ScriptUtil.exec(Arrays.asList("cmd /c", "cd /d", this.dir + "/build-cli", "&&", "npm install"));
        }

        if (os.equals("Linux") || os.startsWith("Mac")) {
            ScriptUtil.exec(Arrays.asList("/bin/bash", this.dir + "/build-cli/install.sh " + this.dir + "/build-cli"));
        }

        System.out.println("==== 结束创建 build-cli ====");
    }
}
