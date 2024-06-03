package com.XZY.mallchat.common.common.event;

import com.XZY.mallchat.common.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/3 19:50
 */
@Getter
public class UserRegisterEvent extends ApplicationEvent {
    private User user;


    public UserRegisterEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
