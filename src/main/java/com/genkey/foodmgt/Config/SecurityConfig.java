package com.genkey.foodmgt.Config;


import com.genkey.foodmgt.customUserDatailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;




//@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class SecurityConfig {


    @Autowired
    AuthenticationSuccessHandler successHandler;

    private final customUserDatailService customUserDetailService;



    @Bean
    public AuthenticationManager authManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailService)
                .passwordEncoder(getPasswordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers()
                .frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers("/resources/**", "/webjars/**","/Password","/forgot-password","/forgot-password-confirm"
                ,"/reset-password", "/reset-password-success","/editSingleMeal/**","/editedMenu", "/ManualUpload/**", "/ManualUpload", "/admin/**",
                        "admin/adminCreateMenu").permitAll()
                .antMatchers().permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/","/profile","/UploadForm","/search","/changePassword").hasAnyAuthority("USER","ADMIN","INTERN","SECURITY","NSS")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(successHandler)
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("my-remember-me-cookie")
                .permitAll()
                /*.and()
                .rememberMe()
                .key("rem-me-key")
                .rememberMeParameter("remember-me") // it is name of checkbox at login page
                .rememberMeCookieName("rememberlogin") // it is name of the cookie
                .tokenValiditySeconds(100)*/ // remember for number of seconds
                .and()
                .exceptionHandling()
                .and()
                .httpBasic();
        return http.build();

        //http.sessionManagement().maximumSessions(1).expiredUrl("/login?expired=true");
    }


}
