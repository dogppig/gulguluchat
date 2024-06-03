package com.XZY.mallchat.common.websocket.domain.vo.req;


import lombok.Data;

@Data
public class WSBaseReq {
    /**
     * @see com.XZY.mallchat.common.websocket.domain.enums.WSReqTypeEnum
     */
    private Integer type;
    private String data;
}
