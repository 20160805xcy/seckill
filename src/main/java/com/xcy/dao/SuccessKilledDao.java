package com.xcy.dao;

import com.xcy.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

public interface SuccessKilledDao {

    /**
     * 插入购买明细，可过滤重复
     *
     * @param seckillId
     * @param userPhone
     * @return 插入的行数 > 0 表示插入成功
     */
    int insertSuccesskilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据id和userPhone查询SuccessKilled并携带秒杀产品对象实体
     *
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone") long userPhone);

}
