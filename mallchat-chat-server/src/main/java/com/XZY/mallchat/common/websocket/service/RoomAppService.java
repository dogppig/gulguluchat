package com.XZY.mallchat.common.websocket.service;

import com.XZY.mallchat.common.common.domain.vo.request.ChatMessageMemberReq;
import com.XZY.mallchat.common.common.domain.vo.request.GroupAddReq;
import com.XZY.mallchat.common.common.domain.vo.request.member.MemberAddReq;
import com.XZY.mallchat.common.common.domain.vo.request.member.MemberDelReq;
import com.XZY.mallchat.common.common.domain.vo.request.member.MemberReq;
import com.XZY.mallchat.common.common.domain.vo.response.ChatMemberListResp;
import com.XZY.mallchat.common.common.domain.vo.response.ChatRoomResp;
import com.XZY.mallchat.common.common.domain.vo.response.MemberResp;
import com.XZY.mallchat.common.user.domain.vo.req.CursorPageBaseReq;
import com.XZY.mallchat.common.user.domain.vo.resp.CursorPageBaseResp;
import com.XZY.mallchat.common.websocket.domain.vo.resp.ChatMemberResp;


import java.util.List;

/**
 * Description:

 */
public interface RoomAppService {
    /**
     * 获取会话列表--支持未登录态
     */
    CursorPageBaseResp<ChatRoomResp> getContactPage(CursorPageBaseReq request, Long uid);

    /**
     * 获取群组信息
     */
    MemberResp getGroupDetail(Long uid, long roomId);

    CursorPageBaseResp<ChatMemberResp> getMemberPage(MemberReq request);

    List<ChatMemberListResp> getMemberList(ChatMessageMemberReq request);

    void delMember(Long uid, MemberDelReq request);

    void addMember(Long uid, MemberAddReq request);

    Long addGroup(Long uid, GroupAddReq request);

    ChatRoomResp getContactDetail(Long uid, Long roomId);

    ChatRoomResp getContactDetailByFriend(Long uid, Long friendUid);
}
