package com.XZY.mallchat.common.user.service;

import cn.hutool.core.io.ValidateObjectInputStream;
import com.XZY.mallchat.common.user.domain.entity.UserBackpack;
import com.XZY.mallchat.common.user.domain.enums.IdempotentEum;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户背包表 服务类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">XZY</a>
 * @since 2024-04-01
 */
public interface IUserBackpackService{
    /**
     * 给用户发放一个物品的幂等类型
     * @param uid   用户ID
     * @param itemId  物品ID
     * @param idempotentEum  幂等类型
     * @param busineesId  幂等唯一标识
     */
    void acquireItem(Long uid, Long itemId, IdempotentEum idempotentEum,String  busineesId);

}
