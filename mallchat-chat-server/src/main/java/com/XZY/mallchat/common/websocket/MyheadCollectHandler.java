package com.XZY.mallchat.common.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Optional;

/**
 * Description:
 */
public class MyheadCollectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.getUri());
            Optional<String >tokenOptional = Optional.of(urlBuilder)
                    .map(UrlBuilder::getQuery)
                    .map(k->k.get("token"))
                    .map(CharSequence::toString);
            //如果token存在
            tokenOptional.ifPresent(s -> NettyUtil.setAttr(ctx.channel(), NettyUtil.TOKEN, s));
            //移除后面拼接的所有参数
            request.setUri(urlBuilder.getPath().toString());
            //取出用户IP
            String ip = request.headers().get("X-Real-IP");//用户从nigx走就取这个
            if(StringUtils.isBlank(ip)){//直连就是这个
                InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
                ip = socketAddress.getAddress().getHostAddress();
            }
            //保存带channel附件
            NettyUtil.setAttr(ctx.channel(),NettyUtil.IP,ip);
            //处理器只需要处理一次
            ctx.pipeline().remove(this);
        }
            ctx.fireChannelRead(msg);
      }

}
