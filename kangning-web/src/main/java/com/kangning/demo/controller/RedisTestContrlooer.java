package com.kangning.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

/**
 * @author 加康宁 Date: 2019-05-07 Time: 15:04
 * @version $Id$
 */
@Controller
@RequestMapping("redis")
public class RedisTestContrlooer {

    @Autowired
    JedisCluster jedisCluster;

    @ResponseBody
    @RequestMapping("set")
    public Object testSetValue(){
        return jedisCluster.set("hello", "world");
    }

    @ResponseBody
    @RequestMapping("get")
    public Object testGetValue(){
        return jedisCluster.get("hello");
    }

}
