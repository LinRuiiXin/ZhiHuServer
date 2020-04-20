package com.sz.ZhiHu;

import com.sz.ZhiHu.dao.UserDao;
import com.sz.ZhiHu.service.AnswerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
class ZhiHuApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserDao userDao;
    @Autowired
    AnswerService answerService;
    @Test
    void contextLoads() {
        System.out.println("12edE".toUpperCase());
    }
/*    @Test
    public void m1(){
        Integer rangeNumber = RandomUtils.getRangeNumber(2);
        System.out.println(rangeNumber);
    }
    @Test
    public void m2(){
        int [] arr = new int[]{3,4,5,6,8,9,11,10,13,14};
        int [] arr2 = new int[]{1,2,3};
        Random random = new Random();
        for(int i = 0;i<100;i++){
            answerService.insertAnswer(new Answer(Long.valueOf(arr[random.nextInt(arr.length-1)]),31L,"圣诞节卡萨帝好办法的空间和肯定撒计划",arr2[random.nextInt(arr2.length)]));
        }
    }*/
    @Test
    public void m3(){
        ConcurrentHashMap<Long, Object> map = new ConcurrentHashMap<>();
        map.put(1L,new Object());
        map.put(2L,new Object());
        boolean b = map.containsKey(new Long(1));
        System.out.println(b);
    }
}
