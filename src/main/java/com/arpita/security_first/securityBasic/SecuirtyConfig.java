package com.arpita.security_first.securityBasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;

import static com.arpita.security_first.securityBasic.ApplicationUserPermission.COURSE_WRITE;
import static com.arpita.security_first.securityBasic.ApplicationUserPermission.STUDENT_WRITE;
import static com.arpita.security_first.securityBasic.ApplicationUserRole.*;

@Controller
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecuirtyConfig extends WebSecurityConfigurerAdapter {



    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecuirtyConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/" ,"index" ,"/css/*" ,"/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
              /*  .antMatchers(HttpMethod.DELETE,"/manager/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/manager/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/manager/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/manager/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name()) */
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails arpitaUser= User.builder()
                .username("arpitamitra")
                .password(passwordEncoder.encode("password"))
                //.roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthority())
                .build();

        UserDetails sohailUser=User.builder()
                .username("sohail")
                .password(passwordEncoder.encode("password1"))
                //.roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthority())
                .build();

        UserDetails lindaUser=User.builder()
                .username("linda")
                .password(passwordEncoder.encode("password1"))
               // .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthority())
                .build();

        return new InMemoryUserDetailsManager(arpitaUser,sohailUser,lindaUser);
    }
}
