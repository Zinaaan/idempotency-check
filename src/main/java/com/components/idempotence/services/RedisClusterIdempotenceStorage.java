package com.components.idempotence.services;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lzn
 * @date 2023/01/10 15:49
 * @description IdempotenceStorage的实现类, 使用NoSql数据库redis存储幂等号
 */
@Service
public class RedisClusterIdempotenceStorage implements IdempotenceStorage {

    private final JedisCluster jedisCluster;

    /**
     * Constructor
     *
     * @param redisClusterAddress: the format is 128.91.12.1:3455;128.91.12.2:3452;289.13.2.12:8979
     * @param config               should not be null
     */
    public RedisClusterIdempotenceStorage(String redisClusterAddress, GenericObjectPoolConfig config) {
        Set<HostAndPort> redisNodes = parseHostAndPorts(redisClusterAddress);
        this.jedisCluster = new JedisCluster(redisNodes, config);
    }

    public RedisClusterIdempotenceStorage(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    /**
     * Save @idempotenceId into storage if not absent
     *
     * @param idempotenceId: the idempotence id
     * @return true if @idempotenceId is saved, otherwise return false
     */
    @Override
    public boolean saveIfAbsent(String idempotenceId) {
        long success = jedisCluster.setnx(idempotenceId, "1");
        return success == 1;
    }

    @Override
    public void delete(String idempotenceId) {
        jedisCluster.del(idempotenceId);
    }

    @Override
    public boolean check(String idempotenceId) {
        return jedisCluster.exists(idempotenceId);
    }

    /**
     * parse redisClusterAddress and save to HashSet
     *
     * @param redisClusterAddress: cluster address, the format is 128.91.12.1:3455;128.91.12.2:3452;289.13.2.12:8978
     * @return redisNodes: cluster relationship about host and ports
     */
    protected Set<HostAndPort> parseHostAndPorts(String redisClusterAddress) {
        String[] addressArray = redisClusterAddress.split(";");
        Set<HostAndPort> redisNodes = new HashSet<>();
        for (String address : addressArray) {
            String[] hostAndPort = address.split(":");
            redisNodes.add(new HostAndPort(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
        }

        return redisNodes;
    }
}
