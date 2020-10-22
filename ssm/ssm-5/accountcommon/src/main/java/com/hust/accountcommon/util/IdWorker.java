package com.hust.accountcommon.util;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IdWorker {

    private static SnowflakeIdWorker idWorker;

    private static IdWorker instance = new IdWorker();

    private IdWorker() {
    }

    public static IdWorker getInstance() {
        return instance;
    }

    /**
     * 原先通过@value注入初始化  因为现在从配置类读取，改为用@PostConstruct注解完成初始化操作
     */
    @PostConstruct
    public void init() {
        int workerId = 1;
        int dataCenterId = 1;
        if(workerId == -1){
            LogUtil.info("本服务不支持IdWorker", "IdWorker");
            idWorker = null;
        }
        else{
            idWorker = new SnowflakeIdWorker(workerId, dataCenterId);
            LogUtil.info("初始化机器ID：" + workerId + ",数据中心ID：" + dataCenterId, "IdWorker");
        }
    }


    public long getId() {
        return idWorker.nextId();
    }

}