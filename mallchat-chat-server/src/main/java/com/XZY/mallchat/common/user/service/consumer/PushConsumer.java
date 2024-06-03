//package com.XZY.mallchat.common.user.service.consumer;
//
//import com.XZY.mallchat.common.common.constant.MQConstant;
//import com.XZY.mallchat.common.common.domain.dto.PushMessageDTO;
//import com.XZY.mallchat.common.websocket.service.WebsocketServices;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * Description: 用户消息接收类
// * Author:戏中言
// * @date: 2024/5/23 0:24
// */
//@RocketMQMessageListener(topic = MQConstant.SEND_MSG_TOPIC)
//public class PushConsumer implements RocketMQListener<PushMessageDTO> {
//    @Autowired
//    private WebsocketServices websocketServices;
//
//
//    @Override
//    public void onMessage(PushMessageDTO pushMessageDTO) {
//
//    }
//}
