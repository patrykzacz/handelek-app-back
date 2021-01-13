package com.engthesis.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST = {
            //Api

            "/api/user/login*",
            "/api/user/register*",
            "/api/user/hello*",
            "/api/user/test*",
            "/api/adress/markers*",
            "/api/anno/userannos*",
            "/api/anno/allanno*"
            // other public endpoints of your API may be appended to this array
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //here we add methods with needed permissions
        http.cors().and().authorizeRequests()
                .antMatchers("/api/adress/adress").hasRole("USER")
                .antMatchers("/api/user/password").hasRole("USER")
                .antMatchers("/api/adress/userPatch").hasRole("USER")
                .antMatchers("/api/user/user").hasRole("USER")
                .antMatchers("/api/adress/adress").hasRole("USER")
                .antMatchers("/api/adress/adressPatch").hasRole("USER")
                .antMatchers("/api/adress/adressLang").hasRole("USER")
                .antMatchers("/api/anno/add").hasRole("USER")
                .antMatchers("/api/anno/useranno").hasRole("USER")
                .antMatchers("/api/anno/deleteanno").hasRole("USER")
                .and()
                .addFilter(new JwtFilter(authenticationManager()))
                .csrf().disable();;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }
}
