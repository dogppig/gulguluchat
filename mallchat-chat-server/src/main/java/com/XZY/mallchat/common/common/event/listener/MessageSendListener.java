package com.XZY.mallchat.common.common.event.listener;


import com.XZY.mallchat.common.common.constant.MQConstant;
import com.XZY.mallchat.common.common.domain.enums.HotFlagEnum;
import com.XZY.mallchat.common.common.event.MessageSendEvent;
import com.XZY.mallchat.common.user.dao.ContactDao;
import com.XZY.mallchat.common.user.dao.MessageDao;
import com.XZY.mallchat.common.user.dao.RoomDao;
import com.XZY.mallchat.common.user.dao.RoomFriendDao;
import com.XZY.mallchat.common.user.domain.entity.Message;
import com.XZY.mallchat.common.user.domain.entity.Room;
import com.XZY.mallchat.common.user.service.cache.Usercache;
import com.XZY.mallchat.common.websocket.cache.GroupMemberCache;
import com.XZY.mallchat.common.websocket.cache.HotRoomCache;
import com.XZY.mallchat.common.websocket.cache.RoomCache;
import com.XZY.mallchat.common.websocket.domain.dto.MsgSendMessageDTO;
import com.XZY.mallchat.common.websocket.service.ChatService;
import com.XZY.mallchat.common.websocket.service.WebsocketServices;
import com.abin.mallchat.transaction.service.MQProducer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 消息发送监听器
 *
 */
@Slf4j
@Component
public class MessageSendListener {
    @Autowired
    private WebsocketServices webSocketService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageDao messageDao;
//    @Autowired
//    private IChatAIService openAIService;
//    @Autowired
//    WeChatMsgOperationService weChatMsgOperationService;
    @Autowired
    private RoomCache roomCache;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private GroupMemberCache groupMemberCache;
    @Autowired
    private Usercache userCache;
    @Autowired
    private RoomFriendDao roomFriendDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private HotRoomCache hotRoomCache;
    @Autowired
    private MQProducer mqProducer;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, classes = MessageSendEvent.class, fallbackExecution = true)
    public void messageRoute(MessageSendEvent event) {
        Long msgId = event.getMsgId();
        mqProducer.sendSecureMsg(MQConstant.SEND_MSG_TOPIC, new MsgSendMessageDTO(msgId), msgId);
    }
//这里开启了chatgpt 只有热点群聊才会有这个
//    @TransactionalEventListener(classes = MessageSendEvent.class, fallbackExecution = true)
//    public void handlerMsg(@NotNull MessageSendEvent event) {
//        Message message = messageDao.getById(event.getMsgId());
//        Room room = roomCache.get(message.getRoomId());
//        if (isHotRoom(room)) {
//            openAIService.chat(message);
//        }
//    }

    public boolean isHotRoom(Room room) {
        return Objects.equals(HotFlagEnum.YES.getType(), room.getHotFlag());
    }

//    /**
//     * 给用户微信推送艾特好友的消息通知
//     * （这个没开启，微信不让推）
//     */
//    @TransactionalEventListener(classes = MessageSendEvent.class, fallbackExecution = true)
//    public void publishChatToWechat(@NotNull MessageSendEvent event) {
//        Message message = messageDao.getById(event.getMsgId());
//        if (Objects.nonNull(message.getExtra().getAtUidList())) {
//            weChatMsgOperationService.publishChatMsgToWeChatUser(message.getFromUid(), message.getExtra().getAtUidList(),
//                    message.getContent());
//        }
//    }
}
