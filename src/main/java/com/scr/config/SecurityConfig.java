package com.scr.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.scr.security.jwt.JwtAuthEntryPoint;
import com.scr.security.jwt.JwtAuthTokenFilter;
import com.scr.security.services.UserDetailsServiceImpl;

import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;


@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Filters will not get executed for the resources
        web.ignoring().antMatchers("/", "/resources/**", "/static/**", "/public/**", "/webui/**", "/h2-console/**"
            , "/configuration/**", "/swagger-ui/**", "/swagger-resources/**", "/api-docs", "/api-docs/**", "/v2/api-docs/**"
            , "/*.html", "/**/*.html" ,"/**/*.css","/**/*.js","/**/*.png","/**/*.jpg", "/**/*.gif", "/**/*.svg", "/**/*.ico", "/**/*.ttf","/**/*.woff","/**/*.otf");
    }

    //If Security is not working check application.properties if it is set to ignore
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .exceptionHandling().and()
        .anonymous().and()
        // Disable Cross site references
        .csrf().disable()
        // Add CORS Filter
        .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
        // Custom Token based authentication based on the header previously given to the client
       // .addFilterBefore(new VerifyTokenFilter(tokenUtil), UsernamePasswordAuthenticationFilter.class)
        // custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
     //   .addFilterBefore(new GenerateTokenForUserFilter ("/session", authenticationManager(), tokenUtil), UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/scr/api/auth/*").permitAll()
        //.antMatchers("/scr/api/*").permitAll()
		.antMatchers("/swagger-ui.html").permitAll()
        .antMatchers("/warehouse/fpApp/*").permitAll()
        .antMatchers("/scr/api/auth/download/*").permitAll() 
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
       // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    /*
    * If You want to store encoded password in your databases and authenticate user
    * based on encoded password then uncomment the below method and provde an encoder

    //@Autowired
    //private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    */
}
