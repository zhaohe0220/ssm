package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yunfei on 2017/2/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccesskilledDaoTest {
    @Resource
    private SuccesskilledDao successkilledDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        int insertCount = successkilledDao.insertSuccessKilled(1000L,1242354L);
        System.out.println("insertCount = " + insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessKilled successKilled = successkilledDao.queryByIdWithSeckill(1000L,1242354L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}