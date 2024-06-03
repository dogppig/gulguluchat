package com.XZY.mallchat.common.user.dao;

import com.XZY.mallchat.common.user.domain.entity.Role;
import com.XZY.mallchat.common.user.mapper.RoleMapper;
import com.XZY.mallchat.common.user.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">XZY</a>
 * @since 2024-04-05
 */
@Service
public class RoleDao extends ServiceImpl<RoleMapper, Role> {

}
