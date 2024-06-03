package com.XZY.mallchat.common.user.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.XZY.mallchat.common.common.domain.enums.NormalOrNoEnum;
import com.XZY.mallchat.common.common.domain.enums.YesorNoenum;
import com.XZY.mallchat.common.common.utils.CursorUtils;
import com.XZY.mallchat.common.user.domain.entity.User;
import com.XZY.mallchat.common.user.domain.vo.req.CursorPageBaseReq;
import com.XZY.mallchat.common.user.domain.vo.resp.CursorPageBaseResp;
import com.XZY.mallchat.common.user.mapper.UserMapper;
import com.XZY.mallchat.common.websocket.domain.enums.ChatActiveStatusEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">XZY</a>
 * @since 2024-03-28
 */
@Service

public class UserDao extends ServiceImpl<UserMapper, User> {

    public User getByOpenId(String openid) {
        return lambdaQuery()
                .eq(User::getOpenId,openid)
                .one();

    }

    public User getbyName(String  modifyName) {
        return lambdaQuery().eq(User::getName,modifyName).one();
    }

    public boolean modifyname(Long uid, String modifyName) {
        return lambdaUpdate().eq(User::getId,uid).set(User::getName,modifyName).update();
    }

    public boolean wearingBadge(Long uid, Long itemId) {
        return lambdaUpdate()
                .eq(User::getId, uid)
                .set(User::getItemId, itemId)
                .update();
    }

    public void invalidUid(Long id) {
        lambdaUpdate()
                .eq(User::getId, id)
                .set(User::getStatus, YesorNoenum.YESOR_NOENUM.getStatus())
                .update();
    }





    public void modifyName(Long uid, String name) {
        User update = new User();
        update.setId(uid);
        update.setName(name);
        updateById(update);
    }



    public User getByName(String name) {
        return lambdaQuery().eq(User::getName, name).one();
    }

    public List<User> getMemberList() {
        return lambdaQuery()
                .eq(User::getStatus, NormalOrNoEnum.NORMAL.getStatus())
                .orderByDesc(User::getLastOptTime)//最近活跃的1000个人，可以用lastOptTime字段，但是该字段没索引，updateTime可平替
                .last("limit 1000")//毕竟是大群聊，人数需要做个限制
                .select(User::getId, User::getName, User::getAvatar)
                .list();

    }

    public List<User> getFriendList(List<Long> uids) {
        return lambdaQuery()
                .in(User::getId, uids)
                .select(User::getId, User::getActiveStatus, User::getName, User::getAvatar)
                .list();

    }

    public Integer getOnlineCount() {
        return getOnlineCount(null);
    }

    public Integer getOnlineCount(List<Long> memberUidList) {
        return lambdaQuery()
                .eq(User::getActiveStatus, ChatActiveStatusEnum.ONLINE.getStatus())
                .in(CollectionUtil.isNotEmpty(memberUidList), User::getId, memberUidList)
                .count();
    }

    public CursorPageBaseResp<User> getCursorPage(List<Long> memberUidList, CursorPageBaseReq request, ChatActiveStatusEnum online) {
        return CursorUtils.getCursorPageByMysql(this, request, wrapper -> {
            wrapper.eq(User::getActiveStatus, online.getStatus());//筛选上线或者离线的
            wrapper.in(CollectionUtils.isNotEmpty(memberUidList), User::getId, memberUidList);//普通群对uid列表做限制
        }, User::getLastOptTime);
    }
}
