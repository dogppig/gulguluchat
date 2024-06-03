package com.XZY.mallchat.common.user.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.XZY.mallchat.common.user.dao.GroupMemberDao;
import com.XZY.mallchat.common.user.dao.UserDao;
import com.XZY.mallchat.common.user.domain.entity.User;
import com.XZY.mallchat.common.user.service.WXMsgService;
import com.XZY.mallchat.common.user.service.adapter.TextBuilder;
import com.XZY.mallchat.common.user.service.adapter.UserAdapter;
import com.XZY.mallchat.common.websocket.cache.GroupMemberCache;
import com.XZY.mallchat.common.websocket.service.WebsocketServices;
import com.abin.frequencycontrol.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
public class WXMsgServiceImpl implements WXMsgService {


    /**
     *
     * openid和登录code的关系map
     */
    private static final ConcurrentHashMap<String ,Integer> WAIT_AUTHORIZE_MAP = new ConcurrentHashMap<>();


    @Autowired
    private WebsocketServices websocketServices;
    @Value("${wx.mp.callback}")
    private String callback;
    @Autowired
    @Lazy
    private WxMpService wxMpService;

    public String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserServiceImpl userService;

    @Override
    @Transactional
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage) {
        String openid = wxMpXmlMessage.getFromUser();
        Integer code = getEventKey(wxMpXmlMessage);
        if(Objects.isNull(code)){
            return null;
        }
      User user = userDao.getByOpenId(openid);
        boolean registed = Objects.nonNull(user);
        boolean autorized =registed && StrUtil.isNotBlank(user.getAvatar());
        //用户已经注册并且授权
        if( registed&& autorized){
            //走登陆成功逻辑，通过code找到给channel推送消息todo
            websocketServices.scanLoginSuccess(code,user.getId());
            return null;
        }
        /**
         * 这里进行了判断如果数据库没有这个用户进行注册
         */
        if(!registed){
            User insert = UserAdapter.buildUserSave(openid);
            userService.register(insert);
        }
        WAIT_AUTHORIZE_MAP.put(openid,code);
        websocketServices.waitAuthorize(code);
        //推送链接让用户授权
        String authorizeURL = String.format(url, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callBack"));//回调地址
        return TextBuilder.build("请点击登录:<a href=\""+authorizeURL+"\">登录</a>",wxMpXmlMessage);
    }

    @Autowired
    private GroupMemberDao groupMember;
    @Autowired
    private GroupMemberCache groupMemberCache;
    /**
     * 回调成功会调用这个函数
     * @param userInfo
     */
    @Override
    @Transactional
    public void authorize(WxOAuth2UserInfo userInfo) {
        String openid = userInfo.getOpenid();
        User user = userDao.getByOpenId(openid);
        //更新用户信息
        if(StringUtils.isBlank(user.getAvatar()))//查看是否有头像
        {
            user = fillUserInfo(user.getId(), userInfo);
        }
        Integer code = WAIT_AUTHORIZE_MAP.remove(openid);
        if(groupMember.NewUseraddgroup(user)){
            groupMemberCache.evictMemberUidList(1l);
        }else{
            throw new BusinessException();
        }
        websocketServices.scanLoginSuccess(code,user.getId());
    }

    private User fillUserInfo(Long uid, WxOAuth2UserInfo userInfo) {
        User user = UserAdapter.buildAuthorizeUser(uid,userInfo);
        userDao.updateById(user);
        return user;
    }

    /**
     * 这里是获取事件的key
     * @param wxMpXmlMessage
     * @return
     */
    private Integer getEventKey(WxMpXmlMessage wxMpXmlMessage) {
        try{
            String eventKey = wxMpXmlMessage.getEventKey();
            String code = eventKey.replace("qrscene_","");
            return Integer.parseInt(code);
        }catch (Exception e)
        {
           log.error("getEventKey error eventKey:{}",wxMpXmlMessage.getEventKey(),e);
           return null;
                }
    }
}
