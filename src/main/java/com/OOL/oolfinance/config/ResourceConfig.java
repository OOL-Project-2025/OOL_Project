package com.OOL.oolfinance.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : ResourceConfig
 * @date : 2025. 8. 28. / 오전 12:13
 * @modifyed :
 **/

@Slf4j
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Value("${custom.path}")
    private String fileDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**") // 브라우저 접근 URL
                .addResourceLocations("file:" + fileDir) // 실제 경로
                .setCachePeriod(20); // 캐시 20초
    }

}
