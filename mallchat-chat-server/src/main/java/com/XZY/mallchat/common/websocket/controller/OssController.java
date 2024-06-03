package com.XZY.mallchat.common.websocket.controller;


import com.XZY.mallchat.common.common.utils.RequestHolderUtils;
import com.XZY.mallchat.common.user.domain.vo.resp.ApiResult;
import com.XZY.mallchat.common.websocket.domain.vo.oss.UploadUrlReq;
import com.XZY.mallchat.common.websocket.service.OssService;
import com.abin.frequencycontrol.util.RequestHolder;
import com.abin.mallchat.oss.domain.OssResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Description: oss控制层
 */
@RestController
@RequestMapping("/capi/oss")
@Api(tags = "oss相关接口")
public class OssController {
    @Autowired
    private OssService ossService;

    @GetMapping("/upload/url")
    @ApiOperation("获取临时上传链接")
    public ApiResult<OssResp> getUploadUrl(@Valid UploadUrlReq req) {
        return ApiResult.success(ossService.getUploadUrl(RequestHolderUtils.get().getUid(), req));
    }
}
