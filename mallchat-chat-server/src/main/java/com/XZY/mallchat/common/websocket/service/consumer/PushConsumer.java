package com.XZY.mallchat.common.websocket.service.consumer;

import com.XZY.mallchat.common.common.constant.MQConstant;
import com.XZY.mallchat.common.common.domain.dto.PushMessageDTO;
import com.XZY.mallchat.common.websocket.domain.enums.WSPushTypeEnum;
import com.XZY.mallchat.common.websocket.service.WebsocketServices;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description:

 */
@RocketMQMessageListener(topic = MQConstant.PUSH_TOPIC,
        consumerGroup = MQConstant.PUSH_GROUP,
        messageModel = MessageModel.BROADCASTING)
@Component
public class PushConsumer implements RocketMQListener<PushMessageDTO> {
    @Autowired
    private WebsocketServices webSocketService;

    @Override
    public void onMessage(PushMessageDTO message) {
        WSPushTypeEnum wsPushTypeEnum = WSPushTypeEnum.of(message.getPushType());
        switch (wsPushTypeEnum) {
            case USER:
                message.getUidList().forEach(uid -> {
                    webSocketService.sendToUid(message.getWsBaseMsg(), uid);//发送给某些用户 这里一般用户发给热点群聊
                });
                break;
            case ALL:
                webSocketService.sendToAllOnline(message.getWsBaseMsg(), null);//发送给全体 这里一般是用于发送给普通群聊
                break;
        }
    }
}
