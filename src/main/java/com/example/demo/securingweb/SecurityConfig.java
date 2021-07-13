package com.example.demo.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private final JwtFilter jwtTokenFilter;
//    public SecurityConfig(JwtFilter filter){
//        this.jwtTokenFilter = filter;
//    }
//    String issuerUri = "https://login.microsoftonline.com/b3b9cfc4-0719-45ae-9178-946ebe5ac23f";
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String issuerUri;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated().and()
                .oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer
                                .jwt(jwt ->
                                        jwt.decoder(JwtDecoders.fromOidcIssuerLocation(issuerUri))
                                )
                );
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/api/teacher");
    }
}

