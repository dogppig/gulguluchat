package com.XZY.mallchat.common.user.dao;

import com.XZY.mallchat.common.user.domain.entity.ItemConfig;
import com.XZY.mallchat.common.user.mapper.ItemConfigMapper;

import com.XZY.mallchat.common.user.service.ItemConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 功能物品配置表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">XZY</a>
 * @since 2024-04-01
 */
@Service
public class ItemConfigDao extends ServiceImpl<ItemConfigMapper, ItemConfig>  {

    public List<ItemConfig> getValidByType(Integer itemType) {
        return  lambdaQuery()
                .eq(ItemConfig::getType,itemType)
                .list();
    }
}
