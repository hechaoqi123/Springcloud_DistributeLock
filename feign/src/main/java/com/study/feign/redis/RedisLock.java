package com.study.feign.redis;

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
     * @param requestTimeout  请求超时时间
     * @param lockTimeout   锁的超时时间
     * @return 锁标识  or  Null
     */
    public String lockWithTimeout(String locaName,long requestTimeout, long lockTimeout) {
        Jedis jedis = null;
        String retIdentifier = null;
        try {
            // 获取连接
            jedis = jedisPool.getResource();
            // 随机生成一个identifier,此处需要优化，生成唯一id
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = "lock:" + locaName;




            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + requestTimeout;
            while (System.currentTimeMillis() < end) {
                //设置
                System.out.println(Thread.currentThread().getName()+"尝试加锁");
                String result=jedis.set(lockKey, identifier,"NX","PX",lockTimeout);
                if ("OK".equals(result)) {//成功加锁
                    // 返回唯一标识，作为释放锁时的凭证
                    System.out.println(Thread.currentThread().getName()+"获取到了锁:"+lockKey+"，令牌为"+identifier);
                    return identifier;
                }
                try {
                    Thread.sleep(500);//500毫秒后再次尝试加锁，6次未成功则请求超时
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
                    System.out.println(Thread.currentThread().getName()+"开始释放锁，令牌为："+identifier+"持有锁线程为:"+jedis.get(lockKey));
                    if (identifier.equals(jedis.get(lockKey))) {
                        Transaction transaction = jedis.multi();
                        transaction.del(lockKey);
                        System.out.println(Thread.currentThread().getName()+"开始释放锁");
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
