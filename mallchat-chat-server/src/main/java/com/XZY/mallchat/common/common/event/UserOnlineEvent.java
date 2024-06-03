package com.XZY.mallchat.common.common.event;

import com.XZY.mallchat.common.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/4 14:23
 */
@Getter
public class UserOnlineEvent extends ApplicationEvent {
    private User user;


    public UserOnlineEvent(Object source, User user){
        super(source);
        this.user = user;
    }
}
