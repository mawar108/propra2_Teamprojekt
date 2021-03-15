package de.hhu.propra.nimasichi.praktikumsplaner;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_ORGA;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_TUTOR;
import static de.hhu.propra.nimasichi.praktikumsplaner.utility.StringConstants.ROLE_USER;

public final class OauthFaker {
  private OauthFaker() { }

  private static OAuth2AuthenticationToken buildPrincipal(final String role) {
    final Map<String, Object> attributes = new HashMap<>();
    attributes.put("sub", "my-id");

    final var authorities = Collections.singletonList(
        new OAuth2UserAuthority(role, attributes));
    final var user = new DefaultOAuth2User(authorities, attributes, "sub");

    return new OAuth2AuthenticationToken(user, authorities, "whatever");
  }

  public static MockHttpSession makeUserSession() {
    return makeSessionForRole(ROLE_USER);
  }

  public static MockHttpSession makeTutorSession() {
    return makeSessionForRole(ROLE_TUTOR);
  }

  public static MockHttpSession makeOrgaSession() {
    return makeSessionForRole(ROLE_ORGA);
  }

  private static MockHttpSession makeSessionForRole(final String role) {
    final var principal = buildPrincipal(role);
    final var session = new MockHttpSession();

    session.setAttribute(
        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
        new SecurityContextImpl(principal));

    return session;
  }
}
