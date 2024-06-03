package com.XZY.mallchat.common.websocket.service.consumer;

import com.XZY.mallchat.common.common.constant.MQConstant;
import com.XZY.mallchat.common.common.domain.dto.PushMessageDTO;
import com.XZY.mallchat.common.common.domain.enums.RoomTypeEnum;
import com.XZY.mallchat.common.common.domain.vo.response.ChatMessageResp;
import com.XZY.mallchat.common.user.dao.ContactDao;
import com.XZY.mallchat.common.user.dao.MessageDao;
import com.XZY.mallchat.common.user.dao.RoomDao;
import com.XZY.mallchat.common.user.dao.RoomFriendDao;
import com.XZY.mallchat.common.user.domain.entity.Message;
import com.XZY.mallchat.common.user.domain.entity.Room;
import com.XZY.mallchat.common.user.domain.entity.RoomFriend;
import com.XZY.mallchat.common.user.service.Impl.PushService;
import com.XZY.mallchat.common.user.service.adapter.WSAdapter;
import com.XZY.mallchat.common.user.service.cache.Usercache;
import com.XZY.mallchat.common.websocket.cache.GroupMemberCache;
import com.XZY.mallchat.common.websocket.cache.HotRoomCache;
import com.XZY.mallchat.common.websocket.cache.RoomCache;
import com.XZY.mallchat.common.websocket.domain.dto.MsgSendMessageDTO;
import com.XZY.mallchat.common.websocket.service.ChatService;
import com.XZY.mallchat.common.websocket.service.WebsocketServices;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Description:发送消息更新房间收信箱，并同步给房间成员信箱
 * Author:戏中言
 *
 * @date: 2024/5/23 0:38
 */
@RocketMQMessageListener(topic = MQConstant.SEND_MSG_TOPIC,consumerGroup = MQConstant.SEND_MSG_GROUP)
//这里有两种格式 ，一种是group 消息会论法监听了所有group的成员 ，一个是这个单独的队列，抢资源
@Component
public class MsgSendConsumer implements RocketMQListener<MsgSendMessageDTO> {
//    @Autowired
//    private WebsocketServices webSocketService;
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
//    @Autowired
//    private Usercache userCache;
    @Autowired
    private RoomFriendDao roomFriendDao;
//    @Autowired
//    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private HotRoomCache hotRoomCache;
    @Autowired
    private PushService pushService;
    @Override
    public void onMessage(MsgSendMessageDTO dto) {
        Message message = messageDao.getById(dto.getMsgId());
        Room room = roomCache.get(message.getRoomId());//先去缓存里面找这个room存不存在
        ChatMessageResp msgResp = chatService.getMsgResp(message,null);//这里uid设置为null是因为群聊消息不需要指定发送者
        //所有房间更新最新消息
        roomDao.refreshActiveTime(room.getId(),message.getId(),message.getCreateTime());
        roomCache.delete(room.getId());//req是roomid
        if(room.isHotRoom()){//热门群聊推送所有在线的人
            //更新热门群聊的时间-redis里面
            hotRoomCache.refreshActiveTime(room.getId(),message.getCreateTime());
            //推送给所有人
            pushService.sendPushMsg(WSAdapter.buildMsgSend(msgResp));
        }else{//不是热门群聊的话
            List<Long> memberUidList = new ArrayList<>();//先创建一个预备获取群聊中成员的List链表
            //先判断是单聊还是群聊
            if (Objects.equals(room.getType(), RoomTypeEnum.GROUP.getType())) {//普通群聊推送所有群成员
                memberUidList = groupMemberCache.getMemberUidList(room.getId());//这个是通过caffeine缓存的群聊对象
            } else if (Objects.equals(room.getType(), RoomTypeEnum.FRIEND.getType())) {//单聊对象
                //对单人推送
                RoomFriend roomFriend = roomFriendDao.getByRoomId(room.getId());
                memberUidList = Arrays.asList(roomFriend.getUid1(), roomFriend.getUid2());
            }
            //更新所有群成员的会话时间
            contactDao.refreshOrCreateActiveTime(room.getId(), memberUidList, message.getId(), message.getCreateTime());
            //推送房间成员
            pushService.sendPushMsg(WSAdapter.buildMsgSend(msgResp), memberUidList);
        }

    }
}
