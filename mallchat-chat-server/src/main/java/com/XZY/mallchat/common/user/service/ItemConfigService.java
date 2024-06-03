package com.XZY.mallchat.common.user.service;

import com.XZY.mallchat.common.user.domain.entity.ItemConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 功能物品配置表 服务类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">XZY</a>
 * @since 2024-04-01
 */
public interface ItemConfigService extends IService<ItemConfig> {

    List<ItemConfig> getValidByType(Integer itemType);
}
