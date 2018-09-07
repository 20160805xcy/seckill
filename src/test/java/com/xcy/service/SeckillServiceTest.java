package com.xcy.service;

import com.xcy.dto.Exposer;
import com.xcy.dto.SeckillExecution;
import com.xcy.entity.Seckill;
import com.xcy.exception.RepeatKillException;
import com.xcy.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;


    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("list={}",seckillList);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(5);
        logger.info("seckill={}"+seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId = 5;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer={}",exposer);
        //获取秒杀地址成功后:exposer=Exposer{exposed=true, md5='d1bb9ba3c40b55332d863a0b4a25c77f', seckillId=5, now=0, start=0, end=0}
        //成功需要得到md5就够了,不需要时间now start end
        //获取秒杀地址失败后:exposer=Exposer{exposed=false, md5='null', seckillId=5, now=1533136058034, start=1533052800000, end=1533096000000}
        //失败需要得到时间now start end,方便前端展示给用户看
    }

    @Test
    public void executeSeckill() throws Exception {
        long seckillId = 5L;
        long userPhone = 18617151926L;
        String md5 = "d1bb9ba3c40b55332d863a0b4a25c77f";
        try{
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
            logger.info("seckillExecution={}",seckillExecution);
        }catch (SeckillCloseException e){
            logger.error("e={}",e.getMessage());
        }catch (RepeatKillException e){
            logger.error("e={}",e.getMessage());
        }
        //秒杀成功后:seckillExecution=SeckillExecution{seckillId=5, state=1, stateInfo='秒杀成功',
        // successKilled=SuccessKilled{seckillId=5, userPhone=18617151926, state=0, createTime=Wed Aug 01 23:20:02 CST 2018,
        // seckill=Seckill{seckillId=5, name='1000元秒杀iPhone6', number=96, startTime=Wed Aug 01 00:00:00 CST 2018, endTime=Fri Aug 03 12:00:00 CST 2018,
        // createTime=Wed Aug 01 23:20:02 CST 2018}}}
    }

    //集成测试代码完整逻辑,注意克重复执行.
    @Test
    public void exportSeckillUrlAndExecuteSeckill() throws Exception {
        long seckillId = 5;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer={}",exposer);
        if(exposer.isExposed()){
            long userPhone = 18617151926L;
            String md5 = exposer.getMd5();
            try{
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                logger.info("seckillExecution={}",seckillExecution);
            }catch (SeckillCloseException e){
                logger.error("e={}",e.getMessage());
            }catch (RepeatKillException e){
                logger.error("e={}",e.getMessage());
            }
        }else {
            //秒杀未开启
            logger.warn("exposer={}",exposer);
        }
    }

}