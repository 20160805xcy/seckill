package com.xcy.exception;

/**
 * 秒杀相关业务异常(运行期异常)
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
