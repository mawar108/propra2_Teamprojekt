package de.hhu.propra.nimasichi.praktikumsplaner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@SuppressWarnings({"PMD.AtLeastOneConstructor", "PMD.LawOfDemeter"})
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.authorizeRequests(a -> a
        .antMatchers("/**").permitAll())
        .exceptionHandling(e -> e.authenticationEntryPoint(
            new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .csrf(c -> c.csrfTokenRepository(
            CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .logout(l -> l.logoutSuccessUrl("/").permitAll())
        .oauth2Login();
  }
}
