package com.OOL.oolfinance.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : TimeZoneConfig
 * @date : 2025. 9. 11. / 오전 12:14
 * @modifyed :
 **/
@Component
@Slf4j
public class TimeZoneConfig {

    @PostConstruct
    public void setupDefaultTimeZone() {
        ZoneId before = ZoneId.systemDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        ZoneId after = ZoneId.systemDefault();
        log.info("JVM Default TimeZone {}→{}", before, after);
    }
}