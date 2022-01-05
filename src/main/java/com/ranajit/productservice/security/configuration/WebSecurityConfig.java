package com.ranajit.productservice.security.configuration;

import com.ranajit.productservice.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.httpBasic();
        httpSecurity.authorizeRequests().mvcMatchers(HttpMethod.GET,"/productapi/products/{id:^[0-9]*$}",
                        "/showGetProduct","/productDetails").hasAnyRole("USER","ADMIN")
                .mvcMatchers(HttpMethod.POST,"/productapi/products","/saveProduct").hasAnyRole("ADMIN")
                .mvcMatchers(HttpMethod.GET,"/showCreateProduct","/createProduct","/createResponse").hasAnyRole("ADMIN")
                .mvcMatchers("/","/login").permitAll()
                .mvcMatchers("/getProduct").hasAnyRole("ADMIN","USER")
                .anyRequest().denyAll()
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
