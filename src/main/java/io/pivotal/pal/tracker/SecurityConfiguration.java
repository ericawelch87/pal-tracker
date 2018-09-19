package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private String disabled;

    public SecurityConfiguration(@Value("${HTTPS_DISABLED}") String disabled){
        this.disabled = disabled;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests().anyRequest().hasRole("USER")
            .and()
            .httpBasic()
            .and()
            .csrf().disable();

        if(!isDisabled()){
            http.requiresChannel().anyRequest().requiresSecure();
        }

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

    private boolean isDisabled(){
        return "true".equals(disabled);
    }
}
