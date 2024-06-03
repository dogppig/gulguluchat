package com.XZY.mallchat.common.common.event.listener;

import com.XZY.mallchat.common.common.event.UserBlackEvent;
import com.XZY.mallchat.common.user.dao.UserDao;
import com.XZY.mallchat.common.user.domain.entity.User;
import com.XZY.mallchat.common.user.service.cache.Usercache;
import com.XZY.mallchat.common.websocket.domain.vo.resp.WSBaseResp;
import com.XZY.mallchat.common.websocket.service.WebsocketServices;
import com.XZY.mallchat.common.websocket.service.adapter.websocketAdapter;
import lombok.extern.slf4j.Slf4j;
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
 * @date: 2024/4/5 10:32
 */

@Slf4j
@Component
public class UserBlackListener {

//    @Autowired
//    private MessageDao messageDao;
    @Autowired
    private WebsocketServices webSocketService;
    @Autowired
    private Usercache userCache;

    @Autowired
    private UserDao userDao;

    @Autowired
    private Usercache cache;
    @Async
    @TransactionalEventListener(classes = UserBlackEvent.class,phase = TransactionPhase.AFTER_COMMIT,fallbackExecution = true)
    public void sendMSGToAll(UserBlackEvent event) {
            User user = event.getUser();
            webSocketService.sendMsgToAll(websocketAdapter.buildResp(user));
    }

    @Async
    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void changeUserStatus(UserBlackEvent event) {
        User user = event.getUser();
        userDao.invalidUid(user.getId());
    }

    @Async
    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void eviCache(UserBlackEvent event) {
        cache.evictBlackMap();
    }
//    @Async
//    @EventListener(classes = UserBlackEvent.class)
//    public void refreshRedis(UserBlackEvent event) {
//        userCache.evictBlackMap();
//        userCache.remove(event.getUser().getId());
//    }

//    @Async
//    @EventListener(classes = UserBlackEvent.class)
//    public void deleteMsg(UserBlackEvent event) {
//        messageDao.invalidByUid(event.getUser().getId());
//    }
//
//    @Async
//    @EventListener(classes = UserBlackEvent.class)
//    public void sendPush(UserBlackEvent event) {
//        Long uid = event.getUser().getId();
//        WSBaseResp<WSBlack> resp = new WSBaseResp<>();
//        WSBlack black = new WSBlack(uid);
//        resp.setData(black);
//        resp.setType(WSRespTypeEnum.BLACK.getType());
//        webSocketService.sendToAllOnline(resp, uid);
//    }

}
