package com.XZY.mallchat.common.websocket;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;

import com.XZY.mallchat.common.user.config.GetBeanUtil;
import com.XZY.mallchat.common.websocket.domain.enums.WSReqTypeEnum;
import com.XZY.mallchat.common.websocket.domain.vo.req.WSBaseReq;
import com.XZY.mallchat.common.websocket.service.WebsocketServices;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
@Sharable//这个表示处理器所有请求全局共用
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private WebsocketServices websocketService;

    @Override
    /**
     * 线程连接上以后会走这个函数
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        websocketService = SpringUtil.getBean(WebsocketServices.class);
//        System.out.println(websocketService);
        websocketService.connect(ctx.channel());
        websocketService.handleLoginReq(ctx.channel());
    }//链接

    /**
     * 客户端主动下线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        useroffline(ctx.channel());
    }

    /**
     * 用户连接上netty以后走这里
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            String Token = NettyUtil.getAttr(ctx.channel(), NettyUtil.TOKEN);
            if(StrUtil.isNotBlank(Token)){
                websocketService.authorize(ctx.channel(),Token);
            }
      System.out.println("握手完成");
        }else if (evt instanceof IdleState){//这里是如果对象属于长时间读空闲就会触发这个事件
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state() == IdleState.READER_IDLE){
                System.out.println("读空闲");
                //Todo:用户下线
                useroffline(ctx.channel());
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught",cause);
        super.exceptionCaught(ctx,cause);
    }

    /**
     * 用户下线统一处理
     * @param channel
     */
    private void useroffline(Channel channel){
        websocketService.remove(channel);
        channel.close();
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        WSBaseReq wsBaseReq = JSONUtil.toBean(text, WSBaseReq.class);

        switch (WSReqTypeEnum.of(wsBaseReq.getType())) {
            case AUTHORIZE:
                websocketService.authorize(ctx.channel(),wsBaseReq.getData());
                break;
            case HEARTBEAT:
                break;
            case LOGIN:
                ctx.channel().writeAndFlush(new TextWebSocketFrame("发送成功"));
                websocketService.handleLoginReq(ctx.channel());
                break;//这里是拿二维码

        }


    }

}
