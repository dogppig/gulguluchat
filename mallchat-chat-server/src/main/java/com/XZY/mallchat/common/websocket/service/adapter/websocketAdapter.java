package com.XZY.mallchat.common.websocket.service.adapter;

import com.XZY.mallchat.common.common.domain.enums.YesorNoenum;
import com.XZY.mallchat.common.user.domain.entity.User;
import com.XZY.mallchat.common.user.domain.enums.RoleEnum;
import com.XZY.mallchat.common.websocket.domain.enums.WSRespTypeEnum;
import com.XZY.mallchat.common.websocket.domain.vo.resp.WSBaseResp;
import com.XZY.mallchat.common.websocket.domain.vo.resp.WSBlack;
import com.XZY.mallchat.common.websocket.domain.vo.resp.WSLoginSuccess;
import com.XZY.mallchat.common.websocket.domain.vo.resp.WSLoginUrl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class websocketAdapter {


    public static WSBaseResp<?> buildResp(WxMpQrCodeTicket wxMpQrCodeTicket) {

        WSBaseResp<WSLoginUrl>resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_URL.getType());
        resp.setData(new WSLoginUrl(wxMpQrCodeTicket.getUrl()));
        return resp;
    }

    public static WSBaseResp<?> buildResp(User user, String token) {

        WSBaseResp<WSLoginSuccess>resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_SUCCESS.getType());
        WSLoginSuccess build = WSLoginSuccess.builder()
                .avatar(user.getAvatar())
                .name(user.getName())
                .token(token)
                .uid(user.getId())
                .build();
        resp.setData(build);
        return resp;

    }


    public static WSBaseResp<?> buildResp(User user, String token,Boolean power) {

        WSBaseResp<WSLoginSuccess>resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_SUCCESS.getType());
        WSLoginSuccess build = WSLoginSuccess.builder()
                .avatar(user.getAvatar())
                .name(user.getName())
                .token(token)
                .power(power? YesorNoenum.YESOR_NOENUM.getStatus() : YesorNoenum.NOENUM.getStatus())
                .uid(user.getId())
                .build();
        resp.setData(build);
        return resp;

    }


    public static WSBaseResp<?> buildResp(User user) {

        WSBaseResp<WSBlack>resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.BLACK.getType());
        WSBlack build = WSBlack.builder()
                .uid(user.getId())
                .build();
        resp.setData(build);
        return resp;

    }

    public static WSBaseResp<?> waitAuthorizebuildResp() {

        WSBaseResp<WSLoginUrl>resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_SCAN_SUCCESS.getType());
        return resp;
    }


    public static WSBaseResp<?> buildIvalidTokenResp() {
        WSBaseResp<WSLoginUrl>resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.INVALIDATE_TOKEN.getType());
        return resp;
    }
}
