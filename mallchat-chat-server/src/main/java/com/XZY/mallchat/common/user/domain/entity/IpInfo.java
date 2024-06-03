package com.XZY.mallchat.common.user.domain.entity;

import cn.hutool.core.lang.func.VoidFunc0;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.nio.file.OpenOption;
import java.util.Objects;
import java.util.Optional;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/4 14:37
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IpInfo implements Serializable {
    //注册的时候ip
    private String createIp;

    //注册时候的Ip详情
    private IpDetail createIpDetail;

    //最新登录ip
    private String updateIp;

    //最新登录ip详情
    private IpDetail updateIpDetail;

    public void freshIp(String ip) {

        //防御性编程
        if (StringUtils.isBlank(ip)) {
            return;
        }
        if(StringUtils.isBlank(createIp)){
            //创建的iP是空的
            createIp = ip;
        }
        updateIp = ip;
    }

    public String  needRefreshIp(){
        boolean notNeedRefresh = Optional.ofNullable(updateIpDetail)
                .map(IpDetail::getIp)
                .filter(ip -> Objects.equals(ip, updateIp))
                .isPresent();

        return notNeedRefresh?null:updateIp;
    }

    public void refreshIpDetail(IpDetail ipDetail) {
        if (Objects.equals(createIp, ipDetail.getIp())) {
            createIpDetail = ipDetail;
        }
        if (Objects.equals(updateIp, ipDetail.getIp())) {
            updateIpDetail = ipDetail;
        }
    }
}
