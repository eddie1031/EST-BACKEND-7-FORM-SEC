package com.est.form_security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
public class SecurityConfiguration {

    // 암호화를 위한 암호화 객체 빈등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
//                .formLogin(formConfig -> formConfig
////                                .usernameParameter("loginId") // HTML form 에서 username 대신 사용할 파라미터를 설정 (기본값 username)
////                                .passwordParameter("loginPw") // HTML form 에서 password 대신 사용할 파라미터를 설정 (기본값 password)
////                        .loginPage("/login") // 로그인 페이지가 존재한다면 설정 가능
////                        .loginProcessingUrl("/login")
////                        .successForwardUrl("/log-in") // 브라우저의 URL 변경이 필요하지 않을 때 사용합니다. 보통 내부 API 호출이나 비동기 로직을 처리할 때, 또는 로그인 후 보여줄 리소스를 서버가 직접 처리할 때 유용합니다.
////                        .failureForwardUrl("/") // 서버 내부에서 추가적인 로직(예: 실패 원인 로깅, 세부 분석)을 처리할 필요가 있을 때 사용합니다. 브라우저의 주소창을 변경하지 않아야 할 때 유용합니다.
//                        .failureUrl("/login?error=true") // 로그인 실패 시 단순히 로그인 페이지로 되돌리고, 실패 메시지를 쿼리 파라미터로 전달해 표시하고 싶을 때 사용합니다.
////                        .defaultSuccessUrl("/") // 로그인 후 사용자를 다른 페이지로 명확하게 이동시키고 싶을 때 사용합니다. 특히 사용자가 로그인하기 전에 보던 페이지가 있는 경우 그 페이지로 리다이렉트됩니다.
//                        .permitAll() // 로그인페이지 접근범위, 이전 버전과는 달리 url matcher를 했다면 설정객체를 넘겨줘야 로그인이 가능하다.
//                )
                .logout(Customizer.withDefaults())
//                .logout(
//                        logoutConfig -> logoutConfig
//                                .logoutUrl("/logout") // 로그아웃할 url을 설정할 때 사용
//                                .logoutSuccessUrl("/") // 로그아웃 후 리다이렉트 될 경로
//                                .invalidateHttpSession(true) // 세션 무효화 여부(기본값 true)
//                                .deleteCookies("JSESSIONID") // 로그아웃 후 지울 쿠키 이름들
//                                .clearAuthentication(true) // SecurityContextHolder에 존재하는 인증객체 삭제
//                                .permitAll() // 로그아웃 페이지 접근권한 여부(상관 없이 로그아웃 작동)
//                )
                .authorizeHttpRequests(
                        auth ->
                        auth.requestMatchers("/login", "/sign-up")
                                .anonymous()
                                .requestMatchers("/users/**")
                                    .hasAnyAuthority("MEMBER","MANAGER", "ADMIN")
                                .requestMatchers("/manager/**")
                                    .hasAnyAuthority("MANGER", "ADMIN")
                                .requestMatchers("/admin/**")
                                    .hasAnyAuthority("ADMIN")
                                .anyRequest()
                                    .authenticated()
                )
                .build();
    }

    // 1. 패스워드는 데이터베이스에 그대로 보관하면 '아주 많이' 위험하다
    // 2. 패스워드는 데이터베이스에 보관할 때, 암호화(Encrypt)해서 보관해야 한다.
    // 3. Spring Security는 암호화를 위한 도구를 제공한다. 그 이름이 BCryptPasswordEncoder 이다.
    // 4. BCryptPasswordEncoder를 Bean으로 등록해서 사용하면 된다.

    // 만일 이 Bean을 등록하면 초기 유저가 이것으로 대체됨
//    @Bean
//    public UserDetailsService userDetailsService() {
//        // 인메모리 유저 생성 (임시계정)
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user")
//                            .password(passwordEncoder()
//                            .encode("user"))
//                            .roles("ADMIN")
////                            .authorities("ADMIN")
//                            .build()
//        );
//        return manager;
//    }

}
