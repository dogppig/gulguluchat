package com.XZY.mallchat.common.websocket.service;

import com.XZY.mallchat.common.user.domain.entity.RoomFriend;
import com.XZY.mallchat.common.user.domain.entity.RoomGroup;


import java.util.List;

/**
 * Description: 房间底层管理
 */
public interface  RoomService {

    /**
     * 创建一个单聊房间
     */
    RoomFriend createFriendRoom(List<Long> uidList);

    RoomFriend getFriendRoom(Long uid1, Long uid2);

    /**
     * 禁用一个单聊房间
     */
    void disableFriendRoom(List<Long> uidList);


    /**
     * 创建一个群聊房间
     */
    RoomGroup createGroupRoom(Long uid);


}
