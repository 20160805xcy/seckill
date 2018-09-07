package com.xcy.service;

import com.xcy.dto.Exposer;
import com.xcy.dto.SeckillExecution;
import com.xcy.entity.Seckill;
import com.xcy.exception.RepeatKillException;
import com.xcy.exception.SeckillCloseException;
import com.xcy.exception.SeckillException;

import java.util.List;

/**
 * 业务接口:站在使用者角度设计接口
 * 三个方面:方法定义粒度;参数;返回类型(return 类型/异常)
 */
public interface SeckillService {

    /**
     * 查询所有秒杀商品
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址,否则输出系统时间和秒杀时间
     * 要达到 只要秒杀没有开始,谁都不能知道我们的秒杀地址 的目的
     * 防止有人恶意使用程序使用浏览器来进行秒杀
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 某个商品对应某个人执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * 执行秒杀动作时候，需要将暴露接口时暴露的md5值带入此方法，将带入的md5值跟内部的
     * 生成规则做比较，如果变了，则说明接口url被篡改了。拒绝这次秒杀。
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException;
}
