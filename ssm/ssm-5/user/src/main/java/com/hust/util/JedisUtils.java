package com.hust.util;

import com.alibaba.fastjson.JSON;
import com.hust.accountcommon.util.PublicUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.util.*;

/**
 * redis工具类.
 *
 * @author yurj
 */
@Component
@Slf4j
public class JedisUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JedisCluster jedisCluster;

    private static String connectFail = "Redis连接异常，再试一次：";

    /**
     * 重命名
     *
     * @param oldKey
     * @param newKey
     * @return
     */
    public boolean rename(String oldKey, String newKey) {
        log.debug(String.format("方法：rename，参数[oldKey:%s,newKey:%s]", oldKey, newKey), "redis");
        try {
            jedisCluster.rename(oldKey, newKey);
            return true;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                jedisCluster.rename(oldKey, newKey);
                return true;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return false;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return false;
        }
    }

    /**
     * 写入对象
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        log.debug(String.format("方法：set，参数[key:%s,value:%s]", key, value), "redis");
        try {
            if(PublicUtil.isBaseType(value)) {
                jedisCluster.set(key, value.toString());
            } else {
                jedisCluster.set(key.getBytes(), PublicUtil.serialize(value));
            }
            return true;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                if(PublicUtil.isBaseType(value)) {
                    jedisCluster.set(key, value.toString());
                } else {
                    jedisCluster.set(key.getBytes(), PublicUtil.serialize(value));
                }
                return true;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return false;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return false;
        }
    }

    /**
     * 写入字符串
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setString(final String key, String value) {
        log.debug(String.format("方法：setString，参数[key:%s,value:%s]", key, value), "redis");
        try {
            jedisCluster.set(key, value);
            return true;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                jedisCluster.set(key, value);
                return true;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return false;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return false;
        }
    }

    /**
     * 写入对象，设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, long expireTime) {
        log.debug(String.format("方法：set，参数[key:%s,value:%s,expireTime:%s]", key, value,expireTime), "redis");
        try {
            if(PublicUtil.isBaseType(value)) {
                jedisCluster.set(key, value.toString());
            } else {
                jedisCluster.set(key.getBytes(), PublicUtil.serialize(value));
            }
            jedisCluster.expire(key, (int)expireTime);
            return true;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                if(PublicUtil.isBaseType(value)) {
                    jedisCluster.set(key, value.toString());
                } else {
                    jedisCluster.set(key.getBytes(), PublicUtil.serialize(value));
                }
                jedisCluster.expire(key, (int)expireTime);
                return true;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return false;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return false;
        }
    }

    /**
     * 写入字符串设置有效时间
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean setString(final String key, String value, long expireTime) {
        log.debug(String.format("方法：setString，参数[key:%s,value:%s,expireTime:%s]", key, value,expireTime), "redis");
        try {
            jedisCluster.set(key, value);
            jedisCluster.expire(key, (int)expireTime);
            return true;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                jedisCluster.set(key, value);
                jedisCluster.expire(key, (int)expireTime);
                return true;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return false;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return false;
        }
    }

    /**
     * redis切换到指定db.
     * 集群没有切换DB的概念了
     *
     * @param dbIndex int
     */
    public void redisSwitchDB(int dbIndex) {
//        JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) redisTemplate.getConnectionFactory();
//        jedisConnectionFactory.getStandaloneConfiguration().setDatabase(dbIndex);
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
    }

    /**
     * 如果不存在则写入 字符串，并设置时效时间
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean setStringIfAbsent(final String key, String value, long expireTime) {
        log.debug(String.format("方法：setStringIfAbsent，参数[key:%s,value:%s,expireTime:%s]", key, value, expireTime), "redis");
        try {
            return setStringIfAbsentInner(key, value, (int)expireTime);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                return setStringIfAbsentInner(key, value, (int)expireTime);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return false;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return false;
        }
    }

    private boolean setStringIfAbsentInner(final String key, String value, int expireTime){
        if (jedisCluster.setnx(key, value) == 1) {
            jedisCluster.expire(key, expireTime);
            return true;
        }
        return false;
    }

    /**
     * 如果不存在则写入 对象，并设置时效时间
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean setIfAbsent(final String key, Object value, long expireTime) {
        log.debug(String.format("方法：setIfAbsent，参数[key:%s,value:%s,expireTime:%s]", key, JSON.toJSONString(value), expireTime), "redis");
        try {
            return setIfAbsentInner(key, value, (int)expireTime);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                return setIfAbsentInner(key, value, (int)expireTime);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return false;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return false;
        }
    }

    private boolean setIfAbsentInner(final String key, Object value, int expireTime) throws IOException {
        if(PublicUtil.isBaseType(value)) {
            if (jedisCluster.setnx(key, value.toString()) == 1) {
                jedisCluster.expire(key, expireTime);
                return true;
            }
        }
        else{
            if (jedisCluster.setnx(key.getBytes(), PublicUtil.serialize(value)) == 1) {
                jedisCluster.expire(key, expireTime);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取缓存过期时间
     *
     * @param key
     * @return
     */
    public long getExpire(final String key) {
        try {
            long expireTime = jedisCluster.ttl(key);
            log.debug(String.format("获取key:%s的过期时间：%s", key, expireTime), "redis");
            return expireTime;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                long expireTime = jedisCluster.ttl(key);
                log.debug(String.format("获取key:%s的过期时间：%s", key, expireTime), "redis");
                return expireTime;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return -1;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return -1;
        }
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        try {
            jedisCluster.del(keys);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                jedisCluster.del(keys);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        log.debug(String.format("方法：remove，参数[key:%s]", key), "redis");
        try {
            jedisCluster.del(key);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                jedisCluster.del(key);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        try {
            boolean isExist = jedisCluster.exists(key);
            log.debug(String.format("查询是否存在key:%s,结果：%s", key, isExist), "redis");
            return isExist;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                boolean isExist = jedisCluster.exists(key);
                log.debug(String.format("查询是否存在key:%s,结果：%s", key, isExist), "redis");
                return isExist;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return false;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return false;
        }
    }

    /**
     * 读取对象
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        try {
            Object object =  PublicUtil.unserizlize(jedisCluster.get(key.getBytes()));
            log.debug(String.format("查询key:%s,值JSON：%s", key, JSON.toJSONString(object)), "redis");
            return object;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                Object object =  PublicUtil.unserizlize(jedisCluster.get(key.getBytes()));
                log.debug(String.format("查询key:%s,值JSON：%s", key, JSON.toJSONString(object)), "redis");
                return object;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    /**
     * 读取字符串
     *
     * @param key
     * @return
     */
    public String getString(final String key) {
        try {
            String result = jedisCluster.get(key);
            log.debug(String.format("查询key:%s,值：%s", key, result), "redis");
            return result;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                String result = jedisCluster.get(key);
                log.debug(String.format("查询key:%s,值：%s", key, result), "redis");
                return result;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    /**
     * 增加
     *
     * @param key
     * @param value
     * @return
     */
    public long incr(final String key, long value) {
        log.debug(String.format("方法：incr，参数[keys:%s,value:%s]", key,value), "redis");
        try {
            return jedisCluster.incrBy(key, value);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                return jedisCluster.incrBy(key, value);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return -1;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return -1;
        }
    }


    /**
     *哈希 添加对象
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hset(String key, String hashKey, Object value) {
        log.debug(String.format("方法：hset，参数[key:%s,hashKey:%s,value:%s]", key, hashKey, JSON.toJSONString(value)), "redis");
        try {
            if(PublicUtil.isBaseType(value)) {
                jedisCluster.hset(key, hashKey, value.toString());
            } else {
                jedisCluster.hset(key.getBytes(), hashKey.getBytes(), PublicUtil.serialize(value));
            }
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                if(PublicUtil.isBaseType(value)) {
                    jedisCluster.hset(key, hashKey, value.toString());
                } else {
                    jedisCluster.hset(key.getBytes(), hashKey.getBytes(), PublicUtil.serialize(value));
                }
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }

    /**
     * 哈希 添加字符串
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hsetString(String key, String hashKey, String value) {
        log.debug(String.format("方法：hsetString，参数[key:%s,hashKey:%s,value:%s]", key,hashKey,value), "redis");
        try {
            jedisCluster.hset(key, hashKey, value);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                jedisCluster.hset(key, hashKey, value);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }

    /**
     * 设置过期时间
     * @param key
     * @param expireTime
     */
    public void setExpire(String key, int expireTime) {
        log.debug(String.format("方法：hsetString，参数[key:%s,expireTime:%s]", key,expireTime), "redis");
        try {
            jedisCluster.expire(key, expireTime);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                jedisCluster.expire(key, expireTime);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }

    /**
     * 哈希 获取对象
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hget(String key, String hashKey) {
        log.debug(String.format("方法：hget，参数[key:%s,hashKey:%s]", key,hashKey), "redis");
        try {
            Object object =  PublicUtil.unserizlize(jedisCluster.hget(key.getBytes(), hashKey.getBytes()));
            log.debug(String.format("返回结果:%s", JSON.toJSONString(object)), "redis");
            return  object;

        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                Object object =  PublicUtil.unserizlize(jedisCluster.hget(key.getBytes(), hashKey.getBytes()));
                log.debug(String.format("返回结果:%s", JSON.toJSONString(object)), "redis");
                return  object;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    /**
     * 哈希 获取字符串
     *
     * @param key
     * @param hashKey
     * @return
     */
    public String hgetString(String key, String hashKey) {
        log.debug(String.format("方法：hgetString，参数[key:%s,hashKey:%s]", key,hashKey), "redis");
        try {
            String result =  jedisCluster.hget(key, hashKey);
            log.debug(String.format("返回结果:%s", result), "redis");
            return result;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                String result =  jedisCluster.hget(key, hashKey);
                log.debug(String.format("返回结果:%s", result), "redis");
                return result;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    /**
     * 哈希 获取整个map
     * @param key
     * @return
     */
    public Map<String,?> hgetAll(String key) {
        log.debug(String.format("方法：hgetAll，参数[key:%s]", key), "redis");
        try {
            Map<String,Object> object =  unserizlizeMap(jedisCluster.hgetAll(key.getBytes()));
            log.debug(String.format("返回结果:%s", JSON.toJSONString(object)), "redis");
            return  object;

        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                Map<String,Object> object =  unserizlizeMap(jedisCluster.hgetAll(key.getBytes()));
                log.debug(String.format("返回结果:%s", JSON.toJSONString(object)), "redis");
                return  object;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    /**
     * 哈希 获取整个map字符串
     * @param key
     * @return
     */
    public Map<String,String> hgetAllString(String key) {
        log.debug(String.format("方法：hgetAllString，参数[key:%s]", key), "redis");
        try {
            Map<String,String> object =  jedisCluster.hgetAll(key);
            log.debug(String.format("返回结果:%s", JSON.toJSONString(object)), "redis");
            return  object;

        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                Map<String,String> object =  jedisCluster.hgetAll(key);
                log.debug(String.format("返回结果:%s", JSON.toJSONString(object)), "redis");
                return  object;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }


    /**
     * 获取所有的哈希key
     *
     * @param key
     * @return
     */
    public Set<String> hmKeys(String key) {
        log.debug(String.format("方法：hmKeys，参数[key:%s]", key), "redis");
        try {
            Set<String> result =  jedisCluster.hkeys(key);
            log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
            return result;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                Set<String> result =  jedisCluster.hkeys(key);
                log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
                return result;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    /**
     * 判断哈希是否存在指定key
     *
     * @param key
     * @param hashKey
     * @return
     */
    public boolean hmExists(String key, String hashKey){
        log.debug(String.format("方法：hmExists，参数[key:%s,hashKey:%s]", key,hashKey), "redis");
        try {
            return jedisCluster.hexists(key, hashKey);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                return jedisCluster.hexists(key, hashKey);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return false;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return false;
        }
    }

    /**
     * 删除哈希指定的key
     *
     * @param key
     * @param hashKey
     */
    public void hmDelete(String key, String hashKey){
        log.debug(String.format("方法：hmDelete，参数[key:%s,hashKey:%s]", key,hashKey), "redis");
        try {
            jedisCluster.hdel(key, hashKey);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                jedisCluster.hdel(key, hashKey);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }

    /**
     * 列表添加 对象
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        log.debug(String.format("方法：lPush，参数[k:%s,v:%s]", k, JSON.toJSONString(v)), "redis");
        try {
            if(PublicUtil.isBaseType(v)) {
                jedisCluster.lpush(k, v.toString());
            } else {
                jedisCluster.lpush(k.getBytes(), PublicUtil.serialize(v));
            }
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                if(PublicUtil.isBaseType(v)) {
                    jedisCluster.lpush(k, v.toString());
                } else {
                    jedisCluster.lpush(k.getBytes(), PublicUtil.serialize(v));
                }
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }

    /**
     * 列表添加 对象
     *
     * @param k
     * @param v
     */
    public void rPush(String k, Object v) {
        log.debug(String.format("方法：rPush，参数[k:%s,v:%s]", k, JSON.toJSONString(v)), "redis");
        try {
            if(PublicUtil.isBaseType(v)) {
                jedisCluster.rpush(k, v.toString());
            } else {
                jedisCluster.rpush(k.getBytes(), PublicUtil.serialize(v));
            }
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                if(PublicUtil.isBaseType(v)) {
                    jedisCluster.rpush(k, v.toString());
                } else {
                    jedisCluster.rpush(k.getBytes(), PublicUtil.serialize(v));
                }
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }


    /**
     * 列表添加, 字符串
     *
     * @param k
     * @param v
     */
    public void lPushString(String k, String v) {
        log.debug(String.format("方法：lPushString，参数[k:%s,v:%s]", k, v), "redis");
        try {
            jedisCluster.lpush(k, v);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                jedisCluster.lpush(k, v);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }


    /**
     * 列表添加, 字符串
     *
     * @param k
     * @param v
     */
    public void rPushString(String k, String v) {
        log.debug(String.format("方法：rPushString，参数[k:%s,v:%s]", k, v), "redis");
        try {
            jedisCluster.rpush(k, v);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                jedisCluster.rpush(k, v);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }

    /**
     * 列表获取 对象
     *
     * @param k
     * @param start
     * @param end
     * @return
     */
    public List<Object> lRange(String k, long start, long end) {
        log.debug(String.format("方法：lRange，参数[k:%s,start:%s,end:%s]", k, start, end), "redis");
        try {
            List<Object> result =  lRangeInner(k, start, end);
            log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
            return result;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                List<Object> result =  lRangeInner(k, start, end);
                log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
                return result;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    private List<Object> lRangeInner(String k, long start, long end) throws IOException, ClassNotFoundException {
        List<byte[]> byteList = jedisCluster.lrange(k.getBytes(), start, end);
        return unserizlizeList(byteList);
    }

    private List<Object> unserizlizeList(List<byte[]> byteList) throws IOException, ClassNotFoundException {
        List<Object> result = new ArrayList<>();
        for(byte[] bytes : byteList){
            result.add(PublicUtil.unserizlize(bytes));
        }
        return result;
    }

    private Map<String,Object> unserizlizeMap(Map<byte[],byte[]> byteMap) throws IOException, ClassNotFoundException {
        Map<String,Object> result = new HashMap<>();
        for (Map.Entry<byte[], byte[]> entry : byteMap.entrySet()) {
            byte[] key = entry.getKey();
            byte[] value = entry.getValue();
            result.put(new String(key), PublicUtil.unserizlize(value));
        }
        return result;
    }

    /**
     * 列表获取, 字符串
     *
     * @param k
     * @param start
     * @param end
     * @return
     */
    public List<String> lRangeString(String k, long start, long end) {
        log.debug(String.format("方法：lRangeString，参数[k:%s,start:%s,end:%s]", k, start, end), "redis");
        try {
            List<String> result = jedisCluster.lrange(k, start, end);
            log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
            return result;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                List<String> result = jedisCluster.lrange(k, start, end);
                log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
                return result;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    /**
     * 弹出表头 对象
     *
     * @param k
     * @return
     */
    public Object lPop(String k) {
        log.debug(String.format("方法：lPop，参数[k:%s]", k), "redis");
        try {
            Object result =  PublicUtil.unserizlize(jedisCluster.lpop(k.getBytes()));
            log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
            return result;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                Object result =  PublicUtil.unserizlize(jedisCluster.lpop(k.getBytes()));
                log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
                return result;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    /**
     * 弹出表头字符串
     *
     * @param k
     * @return
     */
    public String lPopString(String k){
        log.debug(String.format("方法：lPopString，参数[k:%s]", k), "redis");
        try {
            String result = jedisCluster.lpop(k);
            log.debug(String.format("返回结果:%s", result), "redis");
            return result;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                String result = jedisCluster.lpop(k);
                log.debug(String.format("返回结果:%s", result), "redis");
                return result;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    /**
     * 集合添加 对象
     *
     * @param key
     * @param value
     */
    public void sadd(String key, Object value) {
        log.debug(String.format("方法：sadd，参数[key:%s,value:%s]", key, JSON.toJSONString(value)), "redis");
        try {
            if(PublicUtil.isBaseType(value)) {
                jedisCluster.sadd(key, value.toString());
            } else {
                jedisCluster.sadd(key.getBytes(), PublicUtil.serialize(value));
            }
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                if(PublicUtil.isBaseType(value)) {
                    jedisCluster.sadd(key, value.toString());
                } else {
                    jedisCluster.sadd(key.getBytes(), PublicUtil.serialize(value));
                }
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }

    /**
     * 集合获取 对象
     *
     * @param key
     * @return
     */
    public Set<Object> smembers(String key) {
        log.debug(String.format("方法：smembers，参数[key:%s]", key), "redis");
        try {
            Set<Object> result =  smembersInner(key);
            log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
            return result;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                Set<Object> result =  smembersInner(key);
                log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
                return result;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    private Set<Object> smembersInner(String key) throws IOException, ClassNotFoundException {
        Set<byte[]> bytes = jedisCluster.smembers(key.getBytes());
        return unserizlizeSet(bytes);
    }

    private Set<Object> unserizlizeSet(Set<byte[]> byteSet) throws IOException, ClassNotFoundException {
        Set<Object> result = new HashSet<>();
        for(byte[] bytes : byteSet){
            result.add(PublicUtil.unserizlize(bytes));
        }
        return result;
    }

    /**
     * 集合添加 字符串
     *
     * @param key
     * @param value
     */
    public void saddString(String key, String value) {
        log.debug(String.format("方法：saddString，参数[key:%s,value:%s]", key, value), "redis");
        try {
            jedisCluster.sadd(key, value);
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                jedisCluster.sadd(key, value);
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
        }
    }

    /**
     * 集合获取 字符串
     *
     * @param key
     * @return
     */
    public Set<String> smembersString(String key) {
        log.debug(String.format("方法：smembersString，参数[key:%s]", key), "redis");
        try {
            Set<String> result =  jedisCluster.smembers(key);
            log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
            return result;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                Set<String> result =  jedisCluster.smembers(key);
                log.debug(String.format("返回结果:%s", JSON.toJSONString(result)), "redis");
                return result;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return null;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return null;
        }
    }

    /**
     * 集合是否存在字符串
     *
     * @param key
     * @param value
     */
    public boolean sismember(String key, String value) {
        log.debug(String.format("方法：sismember，参数[key:%s,value:%s]", key, value), "redis");
        try {
            boolean exist = jedisCluster.sismember(key, value);
            return exist;
        }
        catch (JedisConnectionException ex) {
            try {
                log.error(connectFail + ex.getMessage(), "redis");
                boolean exist = jedisCluster.sismember(key, value);
                return exist;
            } catch (Exception ex1) {
                log.error("", "redis", ex1);
                return false;
            }
        }
        catch (Exception ex){
            log.error("", "redis", ex);
            return false;
        }
    }

    /**
     * 发布消息
     *
     * @param channel 通道
     * @param message 消息
     */
    public void publish(String channel, String message){
        log.debug(String.format("方法：publish，参数[channel:%s,message:%s]", channel, message), "redis");
        stringRedisTemplate.convertAndSend(channel, message);
    }

    private List<String> getScan(Jedis redisService, String key) {
        List<String> list = new ArrayList<>();
        ScanParams params = new ScanParams();
        params.match(key);
        params.count(10000);
        String cursor = "0";
        while (true) {
            ScanResult scanResult = redisService.scan(cursor, params);
            List<String> elements = scanResult.getResult();
            if (elements != null && elements.size() > 0) {
                list.addAll(elements);
            }
            cursor = scanResult.getStringCursor();
            if ("0".equals(cursor)) {
                break;
            }
        }
        return list;
    }

    public List<String> getRedisKeys(String matchKey) {
        log.debug(String.format("方法：getRedisKeys，参数[matchKey:%s]", matchKey), "redis");
        List<String> list = new ArrayList<>();
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (Map.Entry<String, JedisPool> entry : clusterNodes.entrySet()) {
            Jedis jedis = null;
            try {
                jedis = entry.getValue().getResource();
                // 判断非从节点(因为若主从复制，从节点会跟随主节点的变化而变化)
                if (!jedis.info("replication").contains("role:slave")) {
                    List<String> keys = getScan(jedis, matchKey);
                    list.addAll(keys);
                }
            } catch (Exception e) {
//                e.printStackTrace();
                log.error("scan 异常", "redis", e);
            } finally {
                try {
                    if (jedis != null) {
                        entry.getValue().returnResource(jedis);
                    }
                } catch (Exception ex) {
//                    ex.printStackTrace();
                    log.error("scan 异常", "redis", ex);
                }
            }
        }
        log.debug(String.format("返回结果:%s", JSON.toJSONString(list)), "redis");
        return list;
    }
}