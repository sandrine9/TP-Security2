package com.bnpp.epita.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
public class SecurityConfiguration {
    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //Authentification : on utilise ce que Spring nous fournit
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
        //        .passwordEncoder(new BCryptPasswordEncoder())
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, activated from utilisateur where username=?")
                .authoritiesByUsernameQuery("select username, role from utilisateur_role where username=?");
    }

    //authorization : on fournit à Spring
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v1/hello/user").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/v1/hello/admin").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
        return http.build();
    }
}
