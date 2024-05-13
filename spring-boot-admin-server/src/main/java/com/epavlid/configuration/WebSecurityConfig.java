package com.epavlid.configuration;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.UUID;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final AdminServerProperties adminServer;

    public WebSecurityConfig(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(this.adminServer.getContextPath() + "/assets/**").permitAll()
                        .requestMatchers(this.adminServer.getContextPath() + "/login").permitAll()
                        .requestMatchers(this.adminServer.getContextPath() + "/instances").permitAll()
                        .requestMatchers(this.adminServer.getContextPath() + "/instances/*").permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage(this.adminServer.getContextPath() + "/login")
                        .successHandler(successHandler))
                .logout(logout -> logout
                        .logoutUrl(this.adminServer.getContextPath() + "/logout"))
                .httpBasic(withDefaults())
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers(
                                this.adminServer.getContextPath() + "/instances",
                                this.adminServer.getContextPath() + "/instances/*",
                                this.adminServer.getContextPath() + "/actuator/**"))
                .rememberMe(rememberMe -> rememberMe
                        .key(UUID.randomUUID().toString())
                        .tokenValiditySeconds(1209600));

        return http.build();
    }
}


