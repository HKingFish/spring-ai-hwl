package com.kingfish.springai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author : haowl
 * @Date : 2026/6/6 14:05
 * @Desc :
 */
@SpringBootApplication
public class SpringAI01BaseApplication {

    public static void main(String[] args) {
//        String apiKey = System.getenv("DEEPSEEK_API_KEY");
//        if (apiKey == null || apiKey.isBlank()) {
//            throw new IllegalStateException("[SpringAI01BaseApplication][未读取到环境变量 DEEPSEEK_API_KEY，请重启 IDE 或在运行配置中添加]");
//        }
//        System.setProperty("spring.ai.openai.api-key", apiKey);
        SpringApplication.run(SpringAI01BaseApplication.class, args);
    }
}
