package com.XZY.mallchat.common.user.mapper;

import com.XZY.mallchat.common.user.domain.entity.Contact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会话列表 Mapper 接口
 * </p>
 *
 * @author OYBW
 * @since 2024-05-19
 */
public interface ContactMapper extends BaseMapper<Contact> {

    void refreshOrCreateActiveTime(Long roomId, List<Long> memberUidList, Long msgId, Date activeTime);
}
