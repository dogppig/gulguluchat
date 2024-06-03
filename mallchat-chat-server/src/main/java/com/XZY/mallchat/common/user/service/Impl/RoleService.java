package com.XZY.mallchat.common.user.service.Impl;

import com.XZY.mallchat.common.user.domain.enums.RoleEnum;
import com.XZY.mallchat.common.user.service.IRoleService;
import com.XZY.mallchat.common.user.service.cache.Usercache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Description:
 * Author:戏中言
 *
 * @date: 2024/4/5 9:10
 */
@Service
public class RoleService implements IRoleService {

    @Autowired
    private Usercache usercache;

    

    @Override
    public boolean hasPower(Long uid, RoleEnum roleEnum) {
        Set<Long> roleSet = usercache.getRoleSet(uid);

        return isAdmin(roleSet)||roleSet.contains(roleEnum.getId());

    }


    private boolean isAdmin(Set<Long> roleSet){
        return roleSet.contains(RoleEnum.ADMIN.getId());
    }
}
