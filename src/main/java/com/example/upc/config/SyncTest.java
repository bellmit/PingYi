package com.example.upc.config;

import com.example.upc.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class SyncTest {
    @Autowired
    private SysUserService sysUserService;

    @Scheduled(cron="0 0 0 * * ?")
    public void cronJob(){
        System.out.println("当前时间:"+new Date());
        sysUserService.deleteAll();
    }
}
