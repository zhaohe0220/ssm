package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by yunfei on 2017/2/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:spring/spring-dao.xml",
    "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    @Autowired
    private SeckillService seckillService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> lists = seckillService.getSeckillList();
        logger.info("list={}",lists);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        Exposer exposer = seckillService.exportSeckillUrl(1000L);
        logger.info("exposer={}",exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        try {
            SeckillExecution execution = seckillService.executeSeckill(1001L,768675L,"2e141f376207658e3fd4de281877af2b");
            logger.info("result={}",execution);
        }catch (RepeatKillException e){
            logger.error(e.getMessage(),e);
        }catch (SeckillCloseException e){
            logger.error(e.getMessage(),e);
        }
    }

    @Test
    public void testSeckillLogic() throws Exception{
        long id = 1001L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long phone = 546533L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id,phone,md5);
                logger.info("result={}",execution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage(),e);
            }catch (SeckillCloseException e){
                logger.error(e.getMessage(),e);
            }
        }else {
            logger.warn("exposer={}",exposer);
        }
    }

    @Test
    public void executeSeckillByProcedure(){
        long id = 1003L;
        long phone = 12123456342L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillByProcedure(id,phone,md5);
            logger.info(execution.getStateInfo());
        }
    }

}