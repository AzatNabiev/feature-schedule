package ru.waveaccess.features.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.waveaccess.features.security.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private JwtFilter jwtFilter;

    @Autowired
    public void setJwtFilter(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/register", "/api/auth").permitAll()
                .antMatchers("/api/feature/**").hasAuthority("MANAGER")
                .antMatchers("/api/task/solved").hasAnyAuthority("MANAGER", "DEVELOPER")
                .antMatchers("/api/task/opened").hasAnyAuthority("MANAGER", "DEVELOPER", "TESTER")
                .antMatchers("/api/task/{id}").hasAnyAuthority("MANAGER", "DEVELOPER", "TESTER")
                .antMatchers("/api/task/").hasAuthority("MANAGER")
                .antMatchers("/api/task/solved/tester").hasAnyAuthority("MANAGER", "DEVELOPER")
                .antMatchers("/api/grade/status").hasAuthority("MANAGER")
                .antMatchers("/api/task/param").hasAnyAuthority("MANAGER")
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
