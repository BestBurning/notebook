package com.diyishuai.notebook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Autowired
    private Environment env;

    @Autowired
    private JedisPool pool;

    @Bean
    public JedisPool getJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(1000);//最大空闲
        config.setMaxTotal(10240);//最大连接数
        String host = env.getProperty("spring.redis.host");
        Integer port = Integer.parseInt(env.getProperty("spring.redis.port"));
        Integer timeout = Integer.parseInt(env.getProperty("spring.redis.timeout"));
        String password = env.getProperty("spring.redis.password");
        JedisPool pool = new JedisPool(config,host,port,timeout,password);
        return pool;
    }

    @Scope("prototype")
    @Bean
    public Jedis getJedis(){
        return pool.getResource();
    }

}
