package com.OOL.oolfinance.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : SwaggerConfig
 * @date : 2025. 6. 12. / 오전 12:15
 * @modifyed :
 **/

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components()).info(apiInfo());
    }

    public Info apiInfo() {
        return new Info()
                .title("Swagger-UI")
                .description("API 확인")
                .version("1.0.0");
    }
}
