package org.seckill.exception;

/**
 * Created by yunfei on 2017/2/21.
 * 秒杀关闭后的异常
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
