package com.XZY.mallchat.common.common.intercepter;

import cn.hutool.http.ContentType;
import com.XZY.mallchat.common.common.Excption.HttpErrorEnum;
import com.XZY.mallchat.common.user.service.LoginService;
import com.google.common.base.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_SCHEMA = "Bearer ";
    public static final String UID = "uid";


    @Autowired
    private LoginService loginService;
    /**
     * 前置拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getToken(request);

//        boolean isPbulicUrI = isPbulicUrI(request);
        Long validUid = loginService.getValidUid(token);
        if(Objects.nonNull(validUid)){
            request.setAttribute(UID,validUid);
        }else
        {        //用户未登录
            boolean isPbulicUrI = isPbulicUrI(request);
                //401
                HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
                return false;

        }
        return true;
    }

    private boolean isPbulicUrI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String[] split = requestURI.split("/");
        boolean isPbulicUrI = split.length > 2 && "public".equals(split[3]);
        return isPbulicUrI;
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
      return   Optional.ofNullable(header)
                .filter(h->h.startsWith(BEARER_SCHEMA))
                .map(h->h.replaceFirst(BEARER_SCHEMA,""))
                .orElse(null);


    }
}
