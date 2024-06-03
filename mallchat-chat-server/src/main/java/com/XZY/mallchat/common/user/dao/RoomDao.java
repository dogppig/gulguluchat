package com.XZY.mallchat.common.user.dao;

import com.XZY.mallchat.common.user.domain.entity.Room;
import com.XZY.mallchat.common.user.mapper.RoomMapper;
import com.XZY.mallchat.common.user.service.IRoomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 房间表 服务实现类
 * </p>
 *
 * @author OYBW
 * @since 2024-05-19
 */
@Service
public class RoomDao extends ServiceImpl<RoomMapper, Room>  {
    public void refreshActiveTime(Long roomId, Long msgId, Date msgTime) {
        lambdaUpdate()
                .eq(Room::getId, roomId)
                .set(Room::getLastMsgId, msgId)
                .set(Room::getActiveTime, msgTime)
                .update();
    }
}
