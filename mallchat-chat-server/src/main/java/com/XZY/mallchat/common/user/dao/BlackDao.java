package com.XZY.mallchat.common.user.dao;

import com.XZY.mallchat.common.user.domain.entity.Black;
import com.XZY.mallchat.common.user.mapper.BlackMapper;
import com.XZY.mallchat.common.user.service.IBlackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 黑名单 服务实现类
 * </p>
 *
 * @author <a href="https://github.com/zongzibinbin">XZY</a>
 * @since 2024-04-05
 */
@Service
public class BlackDao extends ServiceImpl<BlackMapper, Black> implements IBlackService {

}
