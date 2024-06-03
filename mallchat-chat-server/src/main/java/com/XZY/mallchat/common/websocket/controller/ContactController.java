package com.XZY.mallchat.common.websocket.controller;



import com.XZY.mallchat.common.common.domain.vo.request.ContactFriendReq;
import com.XZY.mallchat.common.common.domain.vo.request.IdReqVO;
import com.XZY.mallchat.common.common.domain.vo.response.ChatRoomResp;
import com.XZY.mallchat.common.common.utils.RequestHolderUtils;
import com.XZY.mallchat.common.user.domain.vo.req.CursorPageBaseReq;
import com.XZY.mallchat.common.user.domain.vo.resp.ApiResult;
import com.XZY.mallchat.common.user.domain.vo.resp.CursorPageBaseResp;
import com.XZY.mallchat.common.websocket.service.ChatService;
import com.XZY.mallchat.common.websocket.service.RoomAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 会话相关接口
 * </p>
 *
 */
@RestController
@RequestMapping("/capi/chat")
@Api(tags = "聊天室相关接口")
@Slf4j
public class ContactController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private RoomAppService roomService;

    @GetMapping("/public/contact/page")
    @ApiOperation("会话列表")
    public ApiResult<CursorPageBaseResp<ChatRoomResp>> getRoomPage(@Valid CursorPageBaseReq request) {
        Long uid = RequestHolderUtils.get().getUid();
        return ApiResult.success(roomService.getContactPage(request, uid));
    }

    @GetMapping("/public/contact/detail")
    @ApiOperation("会话详情")
    public ApiResult<ChatRoomResp> getContactDetail(@Valid IdReqVO request) {
        Long uid = RequestHolderUtils.get().getUid();
        return ApiResult.success(roomService.getContactDetail(uid, request.getId()));
    }

    @GetMapping("/public/contact/detail/friend")
    @ApiOperation("会话详情(联系人列表发消息用)")
    public ApiResult<ChatRoomResp> getContactDetailByFriend(@Valid ContactFriendReq request) {
        Long uid = RequestHolderUtils.get().getUid();
        return ApiResult.success(roomService.getContactDetailByFriend(uid, request.getUid()));
    }
}

