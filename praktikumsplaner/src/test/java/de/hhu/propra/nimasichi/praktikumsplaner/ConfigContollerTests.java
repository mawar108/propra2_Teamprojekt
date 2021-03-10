package de.hhu.propra.nimasichi.praktikumsplaner;

import de.hhu.propra.nimasichi.praktikumsplaner.github.GitHubService;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.DateService;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
  void configIndexTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/konfiguration")
        .session(OAuthFaker.makeSession()))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(
                    containsString("Praktische Übung konfigurieren")));
  }

  @Test
  void configIndexPostTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/konfiguration_zeitslots")
            .with(csrf())
            .session(OAuthFaker.makeSession())
            .param("name", "hallo welt!")
            .param("modus", "1")
            .param("anStartdatum", "2000-02-01")
            .param("anStartzeit", "20:00")
            .param("anSchlussdatum", "2000-02-08")
            .param("anSchlusszeit", "10:00")
            .param("minPersonen", "3")
            .param("maxPersonen", "5"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(
                    containsString("hallo welt")))
            .andExpect(content().string(
                    containsString("Zeitslot")))
            .andExpect(content().string(
                    containsString("<input type=\"hidden\" name=\"anStartdatum\" value=\"2000-02-01\">")));
  }

  @Test
  void tutorHinzufugenPostTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/tutorenZeitHinzufugen")
            .with(csrf())
            .session(OAuthFaker.makeSession())
            .param("name", "hallo welt!")
            .param("modus", "1")
            .param("anStartdatum", "2000-02-01")
            .param("anStartzeit", "20:00")
            .param("anSchlussdatum", "2000-02-08")
            .param("anSchlusszeit", "10:00")
            .param("minPersonen", "3")
            .param("maxPersonen", "5")
            .param("slotDatum", "2000-02-02")
            .param("tutorName","Hans")
            .param("slotZeit","14:00")
            .param("tutorenTermine","2021-03-17;05:05;Peter", "2021-03-25;03:03;Max"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(
                    containsString("hallo welt")))
            .andExpect(content().string(
                    containsString("Zeitslot")))
            .andExpect(content().string(
                    containsString("<input type=\"hidden\" name=\"anStartdatum\" value=\"2000-02-01\">")));
  }


}
