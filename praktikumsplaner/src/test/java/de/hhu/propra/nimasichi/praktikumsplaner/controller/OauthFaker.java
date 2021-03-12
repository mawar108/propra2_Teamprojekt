package de.hhu.propra.nimasichi.praktikumsplaner.controller;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class OauthFaker {
  private OauthFaker() { }

  private static OAuth2AuthenticationToken buildPrincipal() {
    final Map<String, Object> attributes = new HashMap<>();
    attributes.put("sub", "my-id");
    attributes.put("email", "bwatkins@test.org");

    final var authorities = Collections.singletonList(
        new OAuth2UserAuthority("ROLE_USER", attributes));
    final var user = new DefaultOAuth2User(authorities, attributes, "sub");

    return new OAuth2AuthenticationToken(user, authorities, "whatever");
  }

  public static MockHttpSession makeSession() {
    final var principal = buildPrincipal();
    final var session = new MockHttpSession();

    session.setAttribute(
        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
        new SecurityContextImpl(principal));

    return session;
  }
}
