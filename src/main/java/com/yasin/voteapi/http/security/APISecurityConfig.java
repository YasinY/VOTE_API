package com.yasin.voteapi.http.security;

import com.yasin.voteapi.http.security.authorize.APIKeyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@Order(1)
public class APISecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("voteapi_token")
    private String headerName;

    @Value("-af83hoqbb39.3+as-qeg.q/@_@pbbq")
    private String apiKey;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        APIKeyFilter filter = new APIKeyFilter(headerName);
        filter.setAuthenticationManager(authentication -> {
            String apiKey = (String) authentication.getPrincipal();
            if (!this.apiKey.equals(apiKey)) {
                throw new BadCredentialsException("The API key was not found or not the expected value.");
            }
            authentication.setAuthenticated(true);
            return authentication;
        });
        httpSecurity.antMatcher("/vote/**").csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
    }

}