package com.xcy.service.impl;

import com.xcy.dao.SeckillDao;
import com.xcy.dao.SuccessKilledDao;
import com.xcy.dto.Exposer;
import com.xcy.dto.SeckillExecution;
import com.xcy.entity.Seckill;
import com.xcy.entity.SuccessKilled;
import com.xcy.enums.SeckillStateEnum;
import com.xcy.exception.RepeatKillException;
import com.xcy.exception.SeckillCloseException;
import com.xcy.exception.SeckillException;
import com.xcy.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    private final String slat = "yfzyhsxxapersyyhjk";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        //商品不存在
        if (null == seckill) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        //商品的秒杀开始时间 > 此时时间 || 商品的秒杀结束时间 < 此时时间
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        //加密md5 + 加盐(增加密码复杂度)
        String md5 = getMD5(seckillId);
        //秒杀成功
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }


    @Transactional
    /**
     * 使用注解控制事务方法的优点：
     * 1.开发团队达成一致约定,明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短,不要穿插其他网络操作RPC/HTTP请求,如果需要请求则将耗时的网络请求剥离到事务方法外部
     * 3.不是所有的方法都需要事务,如只有一条修改操作,只读操作 是不需要添加事务控制.
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException {
        if (null == md5 || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        //执行秒杀逻辑: 减库存 + 记录购买行为
        Date nowTime = new Date();
        try {
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                //没有更新到记录, 秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录购买行为
                int insertCount = successKilledDao.insertSuccesskilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillException("seckill repeated");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    //return new SeckillExecution(seckillId, 1, "秒杀成功", successKilled);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译期异常转换成运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }
}
