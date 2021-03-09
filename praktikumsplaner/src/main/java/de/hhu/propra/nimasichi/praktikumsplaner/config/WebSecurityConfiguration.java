package de.hhu.propra.nimasichi.praktikumsplaner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@SuppressWarnings({"PMD.AtLeastOneConstructor", "PMD.LawOfDemeter"})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .oauth2Login();
  }

}
