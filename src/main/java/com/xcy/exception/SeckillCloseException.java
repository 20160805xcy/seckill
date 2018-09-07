package com.xcy.exception;

/**
 * 秒杀关闭异常(运行期异常)
 * (如果这场秒杀已经结束(1.秒杀时间到了; 2.库存已经消耗光了),用户还是要拿到接口地址进行秒杀,是不允许的)
 */
public class SeckillCloseException extends SeckillException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
