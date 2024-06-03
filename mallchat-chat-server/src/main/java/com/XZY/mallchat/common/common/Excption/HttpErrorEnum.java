package com.XZY.mallchat.common.common.Excption;

import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.XZY.mallchat.common.user.domain.vo.resp.ApiResult;
import com.google.common.base.Charsets;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/2 15:35
 */
@AllArgsConstructor
public enum HttpErrorEnum {
    ACCESS_DENIED(401, "登录失效，请重新登录");
    private Integer httpCode;
    private String desc;
    public void sendHttpError(HttpServletResponse response) throws IOException {
        response.setStatus(401);
        response.setContentType(ContentType.JSON.toString(Charsets.UTF_8));
        response.getWriter().write(JSONUtil.toJsonStr( ApiResult.fail(httpCode,desc)) );
    }
}
