package com.OOL.oolfinance.config;

import com.OOL.oolfinance.handler.AuthChannelInterceptor;
import com.OOL.oolfinance.handler.JwtHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

/**
 * @author : yongjukim
 * @version : 1.0.0
 * @package : oolfinance
 * @name : StompWebSocketConfig
 * @date : 2025. 5. 8. / 오후 3:27
 * @modifyed :
 **/

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;
    private final AuthChannelInterceptor authChannelInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOriginPatterns("http://localhost:*", "http://oolfinance.kro.kr");

        registry.addEndpoint("/ws")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOriginPatterns("http://localhost:*", "http://oolfinance.kro.kr")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/api/pub");
        registry.enableSimpleBroker("/api/sub");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor);
    }
}
