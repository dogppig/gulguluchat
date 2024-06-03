package com.XZY.mallchat.common.websocket.service;

import com.XZY.mallchat.common.user.domain.entity.Contact;
import com.XZY.mallchat.common.user.domain.entity.Message;
import com.XZY.mallchat.common.websocket.domain.dto.MsgReadInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会话列表 服务类
 * </p>
 *
 */
public interface ContactService {
    /**
     * 创建会话
     */
    Contact createContact(Long uid, Long roomId);

    Integer getMsgReadCount(Message message);

    Integer getMsgUnReadCount(Message message);

    Map<Long, MsgReadInfoDTO> getMsgReadInfo(List<Message> messages);
}
