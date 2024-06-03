package com.XZY.mallchat.common.user.service.cache;

import com.XZY.mallchat.common.user.dao.ItemConfigDao;
import com.XZY.mallchat.common.user.domain.entity.ItemConfig;
import com.XZY.mallchat.common.user.domain.enums.ItemEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/2 19:45
 */
@Component
public class ItemCache {
    // 如果缓存中存在指定键值的数据，则直接返回缓存数据
    // 否则执行方法体，并将结果存入缓存
    @Autowired
    private ItemConfigDao itemConfigDao;

    @Cacheable(cacheNames = "item",key ="'itemsByType:' + #itemType")
    public List<ItemConfig> getByType(Integer itemType){
        return itemConfigDao.getValidByType(itemType);
    }

    @CacheEvict(cacheNames = "item",key ="'itemsByType:' + #itemType")//清空缓存
    public void evictByType(Integer itemType){
    }
    @Cacheable(cacheNames = "item", key = "'item:'+#itemId")
    public ItemConfig getById(Long itemId) {
        return itemConfigDao.getById(itemId);
    }

}
