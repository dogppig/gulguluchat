package com.XZY.mallchat.common.user.service.Impl;

import com.XZY.mallchat.common.common.annotation.RedissionLock;
import com.XZY.mallchat.common.common.domain.enums.YesorNoenum;
import com.XZY.mallchat.common.common.service.LockService;
import com.XZY.mallchat.common.common.utils.AssertUtil;
import com.XZY.mallchat.common.user.dao.UserBackpackDao;
import com.XZY.mallchat.common.user.domain.entity.UserBackpack;
import com.XZY.mallchat.common.user.domain.enums.IdempotentEum;
import com.XZY.mallchat.common.user.service.IUserBackpackService;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/3 13:49
 */
@Service
public class UserBackpackService implements IUserBackpackService {

    /**
     * 代理对象
     */
    @Autowired
    @Lazy
    UserBackpackService userBackpackService;
    @Autowired
    UserBackpackDao userBackpackDao;
    @Autowired
    private LockService lockService;
    @Override

    public void acquireItem(Long uid, Long itemId, IdempotentEum idempotentEum, String busineesId) {
        String idempotent = getidempotentEum(itemId, idempotentEum, busineesId);
        userBackpackService.doAcquireItem(uid,itemId,idempotent);
    }

    @RedissionLock(key = "#idempotent",waitTime = 5000)//等五秒
    public void doAcquireItem(Long uid, Long itemId,String idempotent){
        UserBackpack userBackpack = userBackpackDao.getByIdempotent(idempotent);
        if (Objects.nonNull(userBackpack)) {
            return;
        }
        //发物品
        UserBackpack inset = UserBackpack.builder()
                .uid(uid)
                .itemId(itemId)
                .status(YesorNoenum.NOENUM.getStatus())
                .idempotent(idempotent).build();
        userBackpackDao.save(inset);
    }

    private String getidempotentEum(Long itemId, IdempotentEum idempotentEum, String busineesId) {
        return String.format("%d_%d_%S", itemId, idempotentEum.getType(), busineesId);
    }
}
