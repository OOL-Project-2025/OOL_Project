package com.OOL.oolfinance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : CorsConfig
 * @date : 2025. 8. 13. / 오후 4:08
 * @modifyed :
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns("http://localhost", "http://oolfinance.kro.kr");
    }
}
