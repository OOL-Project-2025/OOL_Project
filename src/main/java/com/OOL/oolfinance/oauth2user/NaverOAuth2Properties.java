package com.OOL.oolfinance.oauth2user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
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
public class NaverOAuth2Properties {
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;
}
