package com.study.feign.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * fileName:RedisConfig
 * description:
 * author:hcq
 * createTime:2019-03-23 11:39
 */
@Configuration
public class RedisConfig {
    @Value("${redis.pool.MaxTotal}")
    private int MaxTotal;
    @Value("${redis.pool.MaxIdle}")
    private int MaxIdle;
    @Value("${redis.pool.MaxWaitMillis}")
    private int MaxWaitMillis;
    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;
    @Bean
    public JedisPoolConfig poolConfig(){
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(MaxTotal);
        config.setMaxIdle(MaxIdle);
        config.setMaxWaitMillis(MaxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        return config;
     }
     @Bean
    public JedisPool jedisPoll(@Autowired JedisPoolConfig config){
        return new JedisPool(config,host,port);
     }
}
