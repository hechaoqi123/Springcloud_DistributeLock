package com.study.moudel.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.UUID;

/**
 * fileName:RedisLock
 * description:
 * author:hcq
 * createTime:2019-03-27 19:33
 */

@Component
public class RedisLock {
      @Autowired
      private JedisPool jedisPool;

    /**
     * 加锁
     * @param locaName  锁的key
     * @param acquireTimeout  获取超时时间
     * @param timeout   锁的超时时间
     * @return 锁标识  or  Null
     */
    public String lockWithTimeout(String locaName,long acquireTimeout, long timeout) {
        Jedis jedis = null;
        String retIdentifier = null;
        try {
            // 获取连接
            jedis = jedisPool.getResource();
            // 随机生成一个identifier,此处需要优化，生成唯一id
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = "lock:" + locaName;

            // 超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int)(timeout / 1000);

            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                //设置
                String result=jedis.set(lockKey, identifier,"NX","PX",lockExpire);
                if ("OK".equals(result)) {//成功加锁
                    // 返回唯一标识，作为释放锁时的凭证
                    return identifier;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JedisException e) {
             e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        //加锁请求超时
        return null;
    }

        /**
         * 释放锁
         * @param lockName 锁的key
         * @param identifier    释放锁的标识
         * @return
         */
        public boolean releaseLock(String lockName, String identifier) {
            Jedis jedis = null;
            String lockKey = "lock:" + lockName;
            boolean retFlag = false;
            try {
                jedis = jedisPool.getResource();
                while (true) {
                    // 监视lock，准备开始事务
                    jedis.watch(lockKey);
                    // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                    if (identifier.equals(jedis.get(lockKey))) {
                        Transaction transaction = jedis.multi();
                        transaction.del(lockKey);
                        List<Object> results = transaction.exec();
                        if (results == null) {
                            continue;
                        }
                        retFlag = true;
                    }
                    jedis.unwatch();
                    break;
                }
            } catch (JedisException e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return retFlag;
        }
    }
