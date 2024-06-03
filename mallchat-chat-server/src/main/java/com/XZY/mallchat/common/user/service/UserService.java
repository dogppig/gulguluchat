package com.XZY.mallchat.common.user.service;

import com.XZY.mallchat.common.user.domain.entity.User;
import com.XZY.mallchat.common.user.domain.vo.req.BlackReq;
import com.XZY.mallchat.common.user.domain.vo.req.ItemInfoReq;
import com.XZY.mallchat.common.user.domain.vo.req.ModifyNameReq;
import com.XZY.mallchat.common.user.domain.vo.req.SummeryInfoReq;
import com.XZY.mallchat.common.user.domain.vo.resp.BadgeRespResp;
import com.XZY.mallchat.common.user.domain.vo.resp.UserInfoResp;
import com.XZY.mallchat.common.websocket.domain.dto.ItemInfoDTO;
import com.XZY.mallchat.common.websocket.domain.dto.SummeryInfoDTO;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author XZY
 * @since 2024-03-28
 */
public interface UserService {
    Long register(User insert);

    UserInfoResp getUserinfo(Long uid);

    void modifyname(Long uid, ModifyNameReq modifyName);


    List<BadgeRespResp> badges(Long uid);

    void wearingBadge(Long uid, Long itemId);

    void black(BlackReq req);

    /**
     * 获取用户汇总信息
     *
     * @param req
     * @return
     */
    List<SummeryInfoDTO> getSummeryUserInfo(SummeryInfoReq req);

    List<ItemInfoDTO> getItemInfo(ItemInfoReq req);

}
