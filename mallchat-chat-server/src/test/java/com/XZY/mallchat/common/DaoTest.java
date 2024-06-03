package com.XZY.mallchat.common;


import com.XZY.mallchat.common.common.utils.JwtUtils;
import com.XZY.mallchat.common.user.dao.UserDao;
import com.XZY.mallchat.common.user.domain.enums.IdempotentEum;
import com.XZY.mallchat.common.user.domain.enums.ItemEnum;
import com.XZY.mallchat.common.user.mapper.UserMapper;
import com.XZY.mallchat.common.user.service.IUserBackpackService;
import com.XZY.mallchat.common.user.service.IpService;
import com.XZY.mallchat.common.user.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DaoTest {
    public static final Long UID = 11031L;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserDao userDao;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    LoginService loginService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 幂等发放
     */
    @Autowired
    private IUserBackpackService iUserBackpackService;


    @Test
    public void sendMQ() {
        Message<String> build = MessageBuilder.withPayload("5515").build();
        rocketMQTemplate.send("test-topic", build);
    }





    @Test
    public void acquireItem() {
        iUserBackpackService.acquireItem(UID, ItemEnum.CONTRIBUTOR.getId(), IdempotentEum.UID,UID+"plll");
    }



    @Test
    public void JWt() {
        String login = loginService.login(11031L);
        System.out.println(login);
    }


    @Test
    public void testjtt() {

        System.out.println(jwtUtils.createToken(1L));
        System.out.println(jwtUtils.createToken(1L));
    }

    @Autowired
    private RedissonClient redissionClien;
    @Autowired
    private ThreadPoolTaskExecutor executor;//这是spring自带的线程池操作

    //ThreadPoolExecutor 这才是concurrent带的线程池操作
    @Test
    public void Thread() throws InterruptedException {
        executor.execute(() -> {
            if (1 == 1) {
                log.error("123123123");
                throw new RuntimeException("!2233");
            }
        });
        Thread.sleep(200);
    }

    @Test
    public void UserLogin() {
        String token = loginService.login(11032L);
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjExMDA1LCJjcmVhdGVUaW1lIjoxNzEwMDc2NjkzfQ.lsYJ-63HkThrvu-Yaj_tSBypnH5DgalUtxTUGRDACr4
        System.out.println(token);
    }

    @Test
    public void redis() {
        RLock lock = redissionClien.getLock("123");
        lock.lock();
        System.out.println("dd");
        lock.unlock();
//        redisTemplate.opsForValue().set("name","卷心菜");
//        RedisUtils.set("ssname","好好");
//        String name = (String) redisTemplate.opsForValue().get("name");
//        String ssname = RedisUtils.get("ssname");
//        System.out.println(ssname);

    }
//    @Test
//    public void test(){
//        User inser = new User();
//        inser.setName("张三");
//        inser.setOpenId("123");
//        int save = userMapper.insert(inser);
//        System.out.println(save);
//    }
    @Autowired
    private IpService ipService;

        @Test
    public void test() throws InterruptedException {
            ipService.aaa();
            Thread.sleep(500000);
    }

    @Test
    public void test11() throws WxErrorException {
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(1, 1000);//创建一个临时二维码
        String url = wxMpQrCodeTicket.getUrl();
        System.out.println(url);
    }


}
