package com.XZY.mallchat.common.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/4 14:56
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpDetail {
    //ip地址
    private String ip;
    //供应商对象
    private String isp;
    //供应商id
    private String isp_id;
    //城市
    private String city;
    //城市ID
    private String city_id;
    //国家名字
    private String county;
    //国家id
    private String county_id;
    //省份的时候
    private String region;
    //省份的时候id
    private String region_id;



    //    {
//        "data": {
//        "area": "",
//                "country": "中国",
//                "isp_id": "100026",
//                "queryIp": "112.96.166.230",
//                "city": "广州",
//                "ip": "112.96.166.230",
//                "isp": "联通",
//                "county": "",
//                "region_id": "440000",
//                "area_id": "",
//                "county_id": null,
//                "region": "广东",
//                "country_id": "CN",
//                "city_id": "440100"
//    },
//        "msg": "query success",
//            "code": 0
//    }

}
