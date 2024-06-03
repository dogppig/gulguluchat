package com.XZY.mallchat.common.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.XZY.mallchat.common.common.domain.enums.YesorNoenum;
import com.XZY.mallchat.common.user.domain.entity.ItemConfig;
import com.XZY.mallchat.common.user.domain.entity.User;
import com.XZY.mallchat.common.user.domain.entity.UserBackpack;
import com.XZY.mallchat.common.user.domain.vo.resp.BadgeRespResp;
import com.XZY.mallchat.common.user.domain.vo.resp.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserAdapter {
    public static User buildUserSave(String openid) {
     return User.builder().openId(openid).build();
    }

    public static User buildAuthorizeUser(Long uid, WxOAuth2UserInfo userInfo) {
        return User.builder().id(uid)
                        .name(userInfo.getNickname())
                                .avatar(userInfo.getHeadImgUrl()).build();
    }

    public static UserInfoResp buildUserInfo(User user, Integer modifyNameCount) {
        UserInfoResp vo = new UserInfoResp();
        BeanUtil.copyProperties(user,vo);
        vo.setModifyNameChance(modifyNameCount);
        return vo;
    }

    public static List<BadgeRespResp> buildBadgeResp(List<ItemConfig> itemconfigs, List<UserBackpack> backpacks, User user) {
        //排序  多表查询
        List<Long> obtainItemSet = backpacks.stream().map(UserBackpack::getItemId).collect(Collectors.toList());//此时拥有的徽章
//        return itemconfigs.stream().map(i -> {
//                    BadgeRespResp resp = new BadgeRespResp();
//                    BeanUtil.copyProperties(i, resp);
//                    resp.setObtain(obtainItemSet.contains(i.getId()) ? YesorNoenum.YESOR_NOENUM.getStatus() : YesorNoenum.NOENUM.getStatus());
//                    resp.setWearing(Objects.equals(i.getId(), user.getItemId()) ? YesorNoenum.YESOR_NOENUM.getStatus() : YesorNoenum.NOENUM.getStatus());
//                    return resp;
//                }).sorted(Comparator.comparing(BadgeRespResp::getWearing, Comparator.reverseOrder())// 如果佩戴了则倒叙的展示  展示在最前面
//                        .thenComparing(BadgeRespResp::getObtain, Comparator.reverseOrder()))        // 是否获得排序 如果获得也需要排到前面
//                .collect(Collectors.toList());
        return itemconfigs.stream().map(a -> {
            BadgeRespResp resp = new BadgeRespResp();
            BeanUtil.copyProperties(a, resp);
            resp.setObtain(obtainItemSet.contains(a.getId()) ? YesorNoenum.YESOR_NOENUM.getStatus() : YesorNoenum.NOENUM.getStatus());
            resp.setWearing(Objects.equals(a.getId(), user.getItemId()) ? YesorNoenum.YESOR_NOENUM.getStatus() : YesorNoenum.NOENUM.getStatus());//用户是否佩戴
            return resp;
        }).sorted(Comparator.comparing(BadgeRespResp::getWearing, Comparator.reverseOrder())
                .thenComparing(BadgeRespResp::getObtain, Comparator.reverseOrder())
        ).collect(Collectors.toList());

    }
}
