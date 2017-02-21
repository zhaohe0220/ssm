package org.seckill.exception;

/**
 * Created by yunfei on 2017/2/21.
 * 重复秒杀异常
 */
public class RepeatKillException extends SeckillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
