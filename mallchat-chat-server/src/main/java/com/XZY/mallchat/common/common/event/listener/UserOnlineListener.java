package com.XZY.mallchat.common.common.event.listener;

import com.XZY.mallchat.common.common.event.UserOnlineEvent;
import com.XZY.mallchat.common.common.event.UserRegisterEvent;
import com.XZY.mallchat.common.user.dao.UserDao;
import com.XZY.mallchat.common.user.domain.entity.User;
import com.XZY.mallchat.common.user.domain.enums.IdempotentEum;
import com.XZY.mallchat.common.user.domain.enums.ItemEnum;
import com.XZY.mallchat.common.user.service.IUserBackpackService;
import com.XZY.mallchat.common.user.service.IpService;
import com.XZY.mallchat.common.websocket.domain.enums.UserActiveStatusEnum;
import org.checkerframework.checker.units.qual.A;
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
 * @date: 2024/4/4 14:27
 */
@Component
public class UserOnlineListener {

    @Autowired
    private IpService ipService;

    @Autowired
    private IUserBackpackService userBackpackService;

    @Autowired
    private UserDao userDao;


    @Async
    @TransactionalEventListener(classes = UserOnlineEvent.class, phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true)
    public void saveDB(UserOnlineEvent event) {
        User user = event.getUser();
        User update = new User();
        update.setId(user.getId());
        update.setLastOptTime(user.getLastOptTime());
        update.setIpInfo(user.getIpInfo());
        update.setActiveStatus(UserActiveStatusEnum.ONLINE.getStatus());
        userDao.updateById(update);
        //用户iP详情解析
        ipService.refreshIpDetailAsync(user.getId());
    }

}
