package com.OOL.oolfinance.oauth2user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : NaverOAuth2Properties
 * @date : 2025. 7. 21. / 오후 11:57
 * @modifyed :
 **/

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.naver")
public class NaverOAuth2Properties {
    private String clientId;
    private String clientSecret;
}
