package com.XZY.mallchat.common.websocket.service.Impl;

import com.XZY.mallchat.common.websocket.domain.enums.OssSceneEnum;
import com.XZY.mallchat.common.websocket.domain.vo.oss.UploadUrlReq;
import com.XZY.mallchat.common.websocket.service.OssService;
import com.abin.frequencycontrol.util.AssertUtil;
import com.abin.mallchat.oss.MinIOTemplate;
import com.abin.mallchat.oss.domain.OssReq;
import com.abin.mallchat.oss.domain.OssResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 */
@Service
public class OssServiceImpl implements OssService {
    @Autowired
    private MinIOTemplate minIOTemplate;

    @Override
    public OssResp getUploadUrl(Long uid, UploadUrlReq req) {
        OssSceneEnum sceneEnum = OssSceneEnum.of(req.getScene());
        AssertUtil.isNotEmpty(sceneEnum, "场景有误");
        OssReq ossReq = OssReq.builder()
                .fileName(req.getFileName())
                .filePath(sceneEnum.getPath())
                .uid(uid)
                .build();
        return minIOTemplate.getPreSignedObjectUrl(ossReq);
    }
}
