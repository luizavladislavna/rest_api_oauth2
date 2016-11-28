package com.digi.web.controller;

import com.digi.model.resource.Log;
import com.digi.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@Slf4j
public class LogController {

    @Autowired
    LogRepository logsRepo;

    // API - read
    //@PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.GET, value = "/logs")
    @ResponseBody
    public List<Log> showAll() {
        return logsRepo.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/{key}")
    @ResponseBody
    public String calculateExpensiveValue(@PathVariable String key) {
        IntStream.range(1, 1000000000).forEach(x->cc(String.valueOf("_key_"+x)));
        return cc(key);
    }

    @Autowired
    private StringRedisTemplate template;

    @Cacheable
    private  String cc(String key){

        ValueOperations<String, String> ops = this.template.opsForValue();
        if (!this.template.hasKey(key)) {
            ops.set(key, "foo");
            log.debug("put: <{},{}>", key, "foo");
        }else{
            log.debug("cached: <{},{}>", key, "foo");
        }
        return ops.get(key);
    }
}
