package com.XZY.mallchat.common.common.event.listener;

import com.XZY.mallchat.common.common.event.UserOfflineEvent;
import com.XZY.mallchat.common.user.dao.UserDao;
import com.XZY.mallchat.common.user.domain.entity.User;
import com.XZY.mallchat.common.user.service.adapter.WSAdapter;
import com.XZY.mallchat.common.user.service.cache.Usercache;
import com.XZY.mallchat.common.websocket.domain.enums.ChatActiveStatusEnum;
import com.XZY.mallchat.common.websocket.service.WebsocketServices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户下线监听器
 *
 * @author zhongzb create on 2022/08/26
 */
@Slf4j
@Component
public class UserOfflineListener {
    @Autowired
    private WebsocketServices webSocketService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private Usercache userCache;
    @Autowired
    private WSAdapter wsAdapter;

    @Async
    @EventListener(classes = UserOfflineEvent.class)
    public void saveRedisAndPush(UserOfflineEvent event) {
        User user = event.getUser();
        userCache.offline(user.getId(), user.getLastOptTime());
        //推送给所有在线用户，该用户下线
        webSocketService.sendToAllOnline(wsAdapter.buildOfflineNotifyResp(event.getUser()), event.getUser().getId());
    }

    @Async
    @EventListener(classes = UserOfflineEvent.class)
    public void saveDB(UserOfflineEvent event) {
        User user = event.getUser();
        User update = new User();
        update.setId(user.getId());
        update.setLastOptTime(user.getLastOptTime());
        update.setActiveStatus(ChatActiveStatusEnum.OFFLINE.getStatus());
        userDao.updateById(update);
    }

}
