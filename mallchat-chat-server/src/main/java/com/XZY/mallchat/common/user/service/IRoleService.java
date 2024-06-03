package com.XZY.mallchat.common.user.service;

import com.XZY.mallchat.common.user.domain.entity.Role;
import com.XZY.mallchat.common.user.domain.enums.RoleEnum;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">XZY</a>
 * @since 2024-04-05
 */
public interface IRoleService{


    /**
     * 是否拥有某个权限
     * @param uid
     * @return
     */
    boolean hasPower(Long uid, RoleEnum roleEnum);

}
