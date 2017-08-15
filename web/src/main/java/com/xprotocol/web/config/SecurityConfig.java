package com.xprotocol.web.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private DataSource dataSource;

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
                .antMatchers("/css/**","/images/**","/img/**","/js/**","/dist/**","/src/**").permitAll()
                .antMatchers("/home","/index.html","/","/index","/error").permitAll()
                .antMatchers("/login", "/signup").permitAll()
//                .antMatchers("/users").permitAll()
//                .antMatchers(HttpMethod.GET, "/user").permitAll()
//                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers("/api/admin/**").hasAuthority("admin")
                .anyRequest().authenticated()
                .and().requestCache().requestCache(new NullRequestCache())
                .and().httpBasic()
                .and().formLogin().usernameParameter("email").passwordParameter("password").loginPage("/login").defaultSuccessUrl("/home")
                .and().logout().logoutSuccessUrl("/home")
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//                .and().csrf().disable();
    }
}
