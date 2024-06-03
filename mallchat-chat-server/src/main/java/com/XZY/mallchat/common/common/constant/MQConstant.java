package com.XZY.mallchat.common.common.constant;

/**
 */
public interface MQConstant {

    /**
     * 消息发送mq
     */
    String SEND_MSG_TOPIC = "chat_send_msg_OYBW";//这个是发出去的消息会到这个队列
    String SEND_MSG_GROUP = "chat_send_msg_group_OYBW";

    /**
     * push用户
     */
    String PUSH_TOPIC = "websocket_push_OYBW";
    String PUSH_GROUP = "websocket_push_group_OYBW";

    /**
     * (授权完成后)登录信息mq
     */
    String LOGIN_MSG_TOPIC = "user_login_send_msg_OYBW";
    String LOGIN_MSG_GROUP = "user_login_send_msg_group_OYBW";

    /**
     * 扫码成功 信息发送mq
     */
    String SCAN_MSG_TOPIC = "user_scan_send_msg_OYBW";
    String SCAN_MSG_GROUP = "user_scan_send_msg_group_OYBW";
}
