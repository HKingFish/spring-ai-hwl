package com.kingfish.springai.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author : haowl
 * @date : 2026/9/12 11:26
 * @desc : 旅行场景演示工具，返回固定示例数据
 */
@Component
public class TravelTools {

    private static final Logger log = LoggerFactory.getLogger(TravelTools.class);

    /**
     * 查询指定城市天气。
     *
     * @param city 城市名
     * @return 天气概况
     */
    @Tool(description = "查询指定城市的当前天气，返回气温和天气概况")
    public String getWeather(@ToolParam(description = "城市名，如杭州、北京") String city) {
        log.info("[TravelTools][getWeather, city={}]", city);
        if (city.contains("杭州")) {
            return "杭州：多云转晴，气温 18~26°C，东南风 3 级";
        }
        if (city.contains("北京")) {
            return "北京：晴，气温 12~22°C，北风 2 级";
        }
        return city + "：晴，气温 20°C（示例数据）";
    }

    /**
     * 获取当前日期时间。
     *
     * @return 当前时间
     */
    @Tool(description = "获取当前日期和时间")
    public String getCurrentDateTime() {
        log.info("[TravelTools][getCurrentDateTime]");
        return LocalDateTime.now().toString();
    }

    /**
     * 查询指定城市热门景点。
     *
     * @param city 城市名
     * @return 景点列表
     */
    @Tool(description = "查询指定城市的热门景点")
    public String getAttractions(@ToolParam(description = "城市名，如杭州、北京") String city) {
        log.info("[TravelTools][getAttractions, city={}]", city);
        if (city.contains("杭州")) {
            return "杭州热门景点：西湖、灵隐寺、河坊街、宋城";
        }
        if (city.contains("北京")) {
            return "北京热门景点：故宫、天坛、颐和园、长城";
        }
        return city + "热门景点：市中心步行街、城市公园（示例数据）";
    }
}
