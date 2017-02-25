package org.seckill.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by yunfei on 2017/2/23.
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    public RedisDao(String ip,int port){
        jedisPool = new JedisPool(ip,port);
    }

    private RuntimeSchema<Seckill> schemas = RuntimeSchema.createFrom(Seckill.class);

    public Seckill getSeckill(long seckillId){
        try {
            Jedis jedis = jedisPool.getResource();
//            Jedis jedis = new Jedis("127.0.0.1",6379);
            try {
                String key = "seckill:" + seckillId;
                //自定义序列化
                byte[] bytes = jedis.get(key.getBytes());
                if(bytes != null){
                    Seckill seckill = schemas.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schemas);
                    return seckill;
                }
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.info(e.getMessage(),e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill){
        try {
            Jedis jedis = jedisPool.getResource();
//            Jedis jedis = new Jedis("127.0.0.1",6379);
            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill,schemas, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout = 60 * 60;
                String result = jedis.setex(key.getBytes(),timeout,bytes);
                return result;
            }finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
        }
        return null;
    }

}
