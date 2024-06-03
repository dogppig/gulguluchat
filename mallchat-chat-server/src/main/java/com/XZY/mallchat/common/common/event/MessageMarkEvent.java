package com.XZY.mallchat.common.common.event;

import com.XZY.mallchat.common.websocket.domain.dto.ChatMessageMarkDTO;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MessageMarkEvent extends ApplicationEvent {

    private final ChatMessageMarkDTO dto;

    public MessageMarkEvent(Object source, ChatMessageMarkDTO dto) {
        super(source);
        this.dto = dto;
    }

}
