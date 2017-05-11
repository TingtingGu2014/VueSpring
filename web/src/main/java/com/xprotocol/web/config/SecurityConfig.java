package com.xprotocol.web.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private DataSource dataSource;

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }
    
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.debug(true);
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
//                .inMemoryAuthentication()
//                .withUser("sedooe").password("password").roles("USER");
                    .jdbcAuthentication().dataSource(dataSource)
                    .usersByUsernameQuery(" SELECT email AS username, password, userId FROM USERS WHERE email=? ")
                    .authoritiesByUsernameQuery(" SELECT USERS.email AS username, ROLES.roleName AS role FROM USERS INNER JOIN USERROLES ON (USERS.userId=USERROLES.userId)"
                            + "                                                                 INNER JOIN ROLES ON (USERROLES.roleId=ROLES.roleId) "
                            + "                                                                 WHERE USERS.email=? ");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**","/images/**","/scripts/**").permitAll()
                .antMatchers("/home","/index.html","/","/index").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/users").permitAll()
                .antMatchers("/admin/**").hasAuthority("admin")
                .antMatchers("/api/**").hasAuthority("admin")
                .anyRequest().authenticated()
                .and().requestCache().requestCache(new NullRequestCache())
                .and().httpBasic()
                .and().formLogin().usernameParameter("email").passwordParameter("password").defaultSuccessUrl("/index.html")
                .and().csrf().disable();
    }
}
