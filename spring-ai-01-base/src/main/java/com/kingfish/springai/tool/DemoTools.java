package com.kingfish.springai.tool;

import java.time.LocalDateTime;

/**
 * @author : haowl
 * @date : 2026/9/9 19:40
 * @desc : Tool 示例的方法实现
 */
public class DemoTools {

    /**
     * 返回当前日期时间。
     */
    public String getCurrentDateTime() {
        return LocalDateTime.now().toString();
    }

    /**
     * 按城市返回示例天气。
     */
    public String getWeather(WeatherQuery query) {
        return query.getCity() + " 当前晴，气温 24°C（示例数据）";
    }
}
