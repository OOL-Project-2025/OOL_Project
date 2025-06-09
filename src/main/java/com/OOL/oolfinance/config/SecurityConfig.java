package com.OOL.oolfinance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity


public class SecurityConfig {
	//private final UserDetailsServiceImpl userDetailsService;
	//private final ObjectMapper objectMapper;

	  //스프링 시큐리티 기능 비활성화 (H2 DB 접근을 위해)
	@Bean
	public WebSecurityCustomizer configure() {
		return (web -> web.ignoring()
				.requestMatchers("/h2-console/**")
		);
	}
	
	//특정 HTTP 요청에 대한 웹 기반 보안 구성
	//시큐리티 대부분의 설정을 담당하는 메소드
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((authorize) -> authorize
			.requestMatchers("/**","/signup", "/","/index.html", "/login").permitAll()
			.anyRequest().authenticated()
			)
			// Form 로그인을 활용하는경우 (JWT에는 필요없음)
//			.formLogin(formLogin -> formLogin
//	    		.loginPage("/login") 
//	  			.defaultSuccessUrl("/home")
//			)
			.logout((logout) -> logout
				.logoutUrl("/logout")
	 			.logoutSuccessUrl("/login")
				.invalidateHttpSession(true)
			)
			.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 정책 설정
                .maximumSessions(1) // 동시 로그인 세션 개수 제한
                .maxSessionsPreventsLogin(true) // 초과 시 기존 세션 유지 여부 설정
	            );
		
		return http.build();
	}
	
	//패스워드 암호화 관련 메소드
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
