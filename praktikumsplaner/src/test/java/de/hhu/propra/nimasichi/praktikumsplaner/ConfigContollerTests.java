package de.hhu.propra.nimasichi.praktikumsplaner;

import de.hhu.propra.nimasichi.praktikumsplaner.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.TutorTerminService;
import de.hhu.propra.nimasichi.praktikumsplaner.web.controller.KonfigurationController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class ConfigContollerTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  TutorTerminService ttService;

  @MockBean
  GitHubService gitHubService;

  @MockBean
  UbungswocheConfigRepo repo;

  @Test
  void defaultParamsTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/konfiguration")
        .session(OAuthFaker.makeSession()))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(
                    containsString("Praktische Übung konfigurieren")));
  }
}
