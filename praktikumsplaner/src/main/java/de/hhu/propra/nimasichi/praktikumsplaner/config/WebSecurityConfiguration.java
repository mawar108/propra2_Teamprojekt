package de.hhu.propra.nimasichi.praktikumsplaner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_ORGA;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_USER;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_TUTOR;


@Configuration
@SuppressWarnings({
    "PMD.AtLeastOneConstructor",
    "PMD.LawOfDemeter"
})
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final transient HandleAuth tutorenAuth;

  public WebSecurityConfiguration(final HandleAuth tutorenAuth) {
    super();
    this.tutorenAuth = tutorenAuth;
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(initOauth2UserService());
  }

  private OAuth2UserService<OAuth2UserRequest, OAuth2User> initOauth2UserService() {
    return userRequest -> {
      final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService
          = new DefaultOAuth2UserService();

      final var oAuth2User = oAuth2UserService.loadUser(userRequest);

      final Map<String, Object> attributes = oAuth2User.getAttributes(); //keep existing attributes

      final var attributeNameKey = userRequest.getClientRegistration()
          .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

      final Set<GrantedAuthority> authorities = new HashSet<>();

      // Standard USER Role hinzufügen
      authorities.add(new SimpleGrantedAuthority(ROLE_USER));

      //Read lists from some config. hardcoded only for example purposes.
      final List<String> orgaList = tutorenAuth.getOrgaHandles();

      final List<String> tutorList = tutorenAuth.getTutorenHandles();

      // Prüfen auf Rollen
      if (orgaList.contains(attributes.get("login").toString())) {
        authorities.add(new SimpleGrantedAuthority(ROLE_ORGA));
      }

      if (tutorList.contains(attributes.get("login").toString())) {
        authorities.add(new SimpleGrantedAuthority(ROLE_TUTOR));
      }

      return new DefaultOAuth2User(authorities, attributes, attributeNameKey);
    };
  }

}
