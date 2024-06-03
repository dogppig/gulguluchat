package com.XZY.mallchat.common.common.event.listener;

import com.XZY.mallchat.common.common.event.UserRegisterEvent;
import com.XZY.mallchat.common.user.dao.UserDao;
import com.XZY.mallchat.common.user.domain.entity.User;
import com.XZY.mallchat.common.user.domain.enums.IdempotentEum;
import com.XZY.mallchat.common.user.domain.enums.ItemEnum;
import com.XZY.mallchat.common.user.service.Impl.UserBackpackService;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/3 19:53
 */

@Component
public class UserRegisterListener {

    @Autowired
    private UserBackpackService userBackpackService;
    @Autowired
    private UserDao userDao;

    @Async
    @TransactionalEventListener(classes = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sencCard(UserRegisterEvent event) {
        //这里获取user
        User user = event.getUser();
        userBackpackService.acquireItem(user.getId(), ItemEnum.MODIFY_NAME_CARD.getId(), IdempotentEum.UID, user.getId().toString());
    }

    @Async
    @TransactionalEventListener(classes = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendbBadge(UserRegisterEvent event) {
        //前一百名注册用户拿到徽章
        User user = event.getUser();
        int registercount = userDao.count();
        if (registercount <= 100) {
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP100_BADGE.getId(), IdempotentEum.UID, user.getId().toString());
        } else if (registercount <= 10) {
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP10_BADGE.getId(), IdempotentEum.UID, user.getId().toString());
        }
    }


}
