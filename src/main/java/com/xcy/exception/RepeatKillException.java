package com.xcy.exception;

/**
 * 重复秒杀异常(运行期异常)  ==注意:spring的声明式事务只支持运行期异常的回滚策略
 * 用户绕过前端验证，执行了两次提交。
 * 用户可能是无心刷新浏览器，造成两次提交。
 * 用户通过第三方工具拿到秒杀接口，执行多次提交，来提高成功率。
 * 我们程序要识别这种重复操作
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message){
        super(message);
    }

    public RepeatKillException(String message, Throwable cause){
        super(message, cause);
    }
}
