package com.diyishuai.notebook.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Repository
public class RedisDao {

    @Autowired
    private Jedis jedis;

    public List<String> getList(String key){
        if (key==null){
            return null;
        }
        long length = jedis.llen(key);
        List<String> list = jedis.lrange(key, 0, length);
        return list;
    }

    public boolean appendRightList(String key, String value) {
        boolean isSuccess = false;
        Long rpush = jedis.rpush(key, value);
        if (rpush > 0)
            isSuccess = true;
        return isSuccess;
    }

    public boolean deleteValueOfListStartLeft(String key, int count, String value) {
        boolean isSuccess = false;
        Long lrem = jedis.lrem(key, count, value);
        if (lrem==0) {
            return false;
        }
        return true;
    }
}
