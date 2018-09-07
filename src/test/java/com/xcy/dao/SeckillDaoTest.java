package com.xcy.dao;

import com.xcy.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * Spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void reduceNumber() throws Exception {
        long seckillId = 5;
        int i = seckillDao.reduceNumber(seckillId, new Date());
        System.out.println("i=" + i);
    }

    @Test
    public void queryById() throws Exception {
        long id = 5;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println("name:"+seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckillList = seckillDao.queryAll(2, 2);
        System.out.println("size = " + seckillList.size());
        System.out.println(seckillList);
    }

}