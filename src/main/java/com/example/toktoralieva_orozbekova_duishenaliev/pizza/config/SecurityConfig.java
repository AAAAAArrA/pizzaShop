package com.example.toktoralieva_orozbekova_duishenaliev.pizza.config;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.enums.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class    SecurityConfig extends WebSecurityConfigurerAdapter {

    private  CustomUserDetailsService userDetailsService;
    @Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl") CustomUserDetailsService userDetailsService) {

        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Permit all requests to the H2 console
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/", "/users/new","/users/profile","/products/page/**").permitAll()
                .antMatchers(HttpMethod.GET,"/menu/**").hasAuthority(Permission.PRODUCTS_READ.getPermission())
                .antMatchers(HttpMethod.POST,"/menu/**").hasAuthority(Permission.PRODUCTS_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/users/delete/**").hasAuthority(Permission.USERS_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/users/").hasAuthority(Permission.USERS_READ.getPermission())
                .antMatchers(HttpMethod.POST,"/users/profile").hasAuthority(Permission.USERS_READ.getPermission())
                .antMatchers(HttpMethod.POST,"/users/**").hasAuthority(Permission.USERS_WRITE.getPermission())
                .antMatchers("/cart/**").hasAuthority(Permission.BUCKET_WRITE.getPermission())
                .and()
                // Permit all other requests
                .authorizeRequests().anyRequest().permitAll()
                .and()
                // Configure form login
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .loginProcessingUrl("/auth")
                .permitAll()
                .and()
                // Configure logout
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                // Disable CSRF protection
                .csrf().disable()
                // Disable frame options to allow H2 console to be accessed
                .headers().frameOptions().disable();
    }
    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
