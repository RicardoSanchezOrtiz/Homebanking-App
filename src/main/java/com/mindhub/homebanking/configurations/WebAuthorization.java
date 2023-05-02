package com.mindhub.homebanking.configurations;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().anyRequest().permitAll();
//                .antMatchers("/rest/**", "/h2-console/").hasAuthority("ADMIN")
//                .antMatchers("/manager.html").hasAnyAuthority("ADMIN")
//                .antMatchers("/web/create-loan.html").hasAnyAuthority("ADMIN")
//                .antMatchers("/api/login").permitAll()
//                .antMatchers("/api/logout").hasAnyAuthority("ADMIN","CLIENT")
//                .antMatchers("/api/loans/newloan").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
//                .antMatchers("/api/clients/current/**").hasAuthority("CLIENT")
//                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAuthority("CLIENT")
//                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAuthority("CLIENT")
//                .antMatchers(HttpMethod.PATCH, "/api/clients/current/cards").hasAuthority("CLIENT")
//                .antMatchers(HttpMethod.PATCH, "/api/clients/current/accounts").hasAuthority("CLIENT")
//                .antMatchers("/api/loans").hasAuthority("CLIENT")
//                .antMatchers("/api/accounts/{id}").hasAuthority("CLIENT")
//                .antMatchers("/web/accounts.html").hasAnyAuthority("CLIENT","ADMIN")
//                .antMatchers("/web/account.html").hasAnyAuthority("CLIENT","ADMIN")
//                .antMatchers("/web/cards.html").hasAnyAuthority("CLIENT")
//                .antMatchers("/web/create-cards.html").hasAnyAuthority("CLIENT")
//                .antMatchers("/web/account.html").hasAnyAuthority("CLIENT")
//                .antMatchers("/web/transaction.html").hasAnyAuthority("CLIENT")
//                .antMatchers("/web/loan-app.html").hasAnyAuthority("CLIENT")
//                .antMatchers("/index.html").permitAll()
//                .antMatchers("/indexStyle.css").permitAll()
//                .antMatchers("/index.css").permitAll()
//                .antMatchers("/assets/js/**").permitAll()
//                .antMatchers("/assets/js/css/**").permitAll()
//                .antMatchers("/images/**").permitAll()
//                .anyRequest().hasRole("ADMIN");
        http.cors().disable();
        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");
        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> {
            if (req.getRequestURI().contains("/web/") || req.getRequestURI().equals("/manager.html")){
                res.sendRedirect("/index.html");
            }
        });
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
