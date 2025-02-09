package com.binarray.spring.dev.security.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
            .requestMatchers("/", "/home").permitAll()
            .requestMatchers("/customers/**").hasRole("USER")
            .requestMatchers("/orders").hasRole("ADMIN")
            .anyRequest().authenticated())
            .formLogin(form -> form
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .permitAll())
            .logout(logout -> logout.clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login?logout")
                    .permitAll());
    }

    @Bean
    public UserDetailsService users(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setConvertToUpperCase(true);
        return authorityMapper;
    }
}
