package com.XZY.mallchat.common.user.service.Impl;

import com.XZY.mallchat.common.common.constant.RedisKey;
import com.XZY.mallchat.common.common.utils.JwtUtils;
import com.XZY.mallchat.common.common.utils.RedisUtils;
import com.XZY.mallchat.common.user.dao.UserDao;
import com.XZY.mallchat.common.user.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    public static final int TOKEN_EXPIRED_TIME = 3;
    public static final int TOKEN_RENEWAL_DAYS = 1;//token剩余的时间
    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public boolean verify(String token) {
        return false;
    }

    ThreadPoolExecutor executor;
    /**]
     * 用户再次登录的时候更新token的过期时间
     * 异步执行 还可以加入@Async可以直接实现
     * @param token
     */
    @Override
    @Async
    public void renewalTokenIfNecessary(String token) {
//        executor.execute(()->{
//            //拿到通过token拿到uid
//            Long Uid = getValidUid(token);
//            //通过uid拿到rediskey值
//            String RedisTokenKey = getUserTokenkey(Uid);
//            //通过rediskey获取这个key的过期时间，用天数显示
//            Long TokenexpireTime = RedisUtils.getExpire(RedisTokenKey, TimeUnit.DAYS);
//
//            if(TokenexpireTime == -2) {    //意思是这个rediskey不存在
//                return;
//            }
//            if(TokenexpireTime < TOKEN_RENEWAL_DAYS){
//                RedisUtils.expire(RedisTokenKey,TOKEN_EXPIRED_TIME,TimeUnit.DAYS);
//            }
//        });
        //拿到通过token拿到uid
        Long Uid = getValidUid(token);
        //通过uid拿到rediskey值
        String RedisTokenKey = getUserTokenkey(Uid);
        //通过rediskey获取这个key的过期时间，用天数显示
        Long TokenexpireTime = RedisUtils.getExpire(RedisTokenKey, TimeUnit.DAYS);

        if(TokenexpireTime == -2) {    //意思是这个rediskey不存在
            return;
        }
        if(TokenexpireTime < TOKEN_RENEWAL_DAYS){
            RedisUtils.expire(RedisTokenKey,TOKEN_EXPIRED_TIME,TimeUnit.DAYS);
        }

    }

    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        //存入redis并且设置过期时间过期时间

        String userTokenkey = getUserTokenkey(uid);

        RedisUtils.set(userTokenkey,token, TOKEN_EXPIRED_TIME, TimeUnit.DAYS);
        return token;
    }



    /**
     * 根据token获取用户的uid
     * @param token
     * @return
     */
    @Override
    public Long getValidUid(String token) {
        Long uid = jwtUtils.getUidOrNull(token);
        if(Objects.isNull(uid)){
            return null;
        }
        String oldToken = RedisUtils.getStr(getUserTokenkey(uid));//老token是找的redis里面的，但是新的token是前端给的，所以我们要做判断。
        if(StringUtils.isBlank(oldToken)){
            return  null;
        }
        return Objects.equals(oldToken,token)?uid:null;
    }
    private String  getUserTokenkey(Long uid) {
        return RedisKey.getKey(RedisKey.USER_TOKEN_STRING,uid);
    }

}
