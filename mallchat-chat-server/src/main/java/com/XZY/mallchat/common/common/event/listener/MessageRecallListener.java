package com.XZY.mallchat.common.common.event.listener;

import com.XZY.mallchat.common.common.event.MessageRecallEvent;
import com.XZY.mallchat.common.user.service.Impl.PushService;
import com.XZY.mallchat.common.user.service.adapter.WSAdapter;
import com.XZY.mallchat.common.websocket.cache.MsgCache;
import com.XZY.mallchat.common.websocket.domain.dto.ChatMsgRecallDTO;
import com.XZY.mallchat.common.websocket.service.ChatService;
import com.XZY.mallchat.common.websocket.service.WebsocketServices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 消息撤回监听器
 */
@Slf4j
@Component
public class MessageRecallListener {
    @Autowired
    private WebsocketServices webSocketService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MsgCache msgCache;
    @Autowired
    private PushService pushService;

    @Async
    @TransactionalEventListener(classes = MessageRecallEvent.class, fallbackExecution = true)
    public void evictMsg(MessageRecallEvent event) {
        ChatMsgRecallDTO recallDTO = event.getRecallDTO();
        msgCache.evictMsg(recallDTO.getMsgId());
    }

    @Async
    @TransactionalEventListener(classes = MessageRecallEvent.class, fallbackExecution = true)
    public void sendToAll(MessageRecallEvent event) {
        pushService.sendPushMsg(WSAdapter.buildMsgRecall(event.getRecallDTO()));
    }

}
