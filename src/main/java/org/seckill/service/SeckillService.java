package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Created by yunfei on 2017/2/21.
 */
public interface SeckillService {
    /**
     * 查询所有记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单条记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 开始时，输出秒杀接口地址,否则返回开始和结束时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
        throws SeckillException,RepeatKillException,SeckillCloseException;

    /**
     * 执行秒杀使用存储过程
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5);
}
