package com.xprotocol.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private DataSource dataSource;
    
    private RequestMatcher csrfRequestMatcher = new RequestMatcher() {

      private AntPathRequestMatcher[] requestMatchers = {
//          new AntPathRequestMatcher("/"),
//          new AntPathRequestMatcher("/home"),
//          new AntPathRequestMatcher("/dist/**"),
//          new AntPathRequestMatcher("/logout"),
          new AntPathRequestMatcher("/api/**")
      };

      @Override
      public boolean matches(HttpServletRequest request) {
        // If the request match one url the CSFR protection will be disabled
        for (AntPathRequestMatcher rm : requestMatchers) {
          if (rm.matches(request)) { return true; }
        }
        return false;
      } // method matches

    };

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
                .and().csrf().requireCsrfProtectionMatcher(csrfRequestMatcher).csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//                .and().csrf().disable();
    }
}
