package de.hhu.propra.nimasichi.praktikumsplaner.controller;

import de.hhu.propra.nimasichi.praktikumsplaner.OauthFaker;
import de.hhu.propra.nimasichi.praktikumsplaner.config.HandleAuth;
import de.hhu.propra.nimasichi.praktikumsplaner.config.MethodSecurityConfiguration;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.UbungswocheConfigRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.repositories.ZeitslotRepo;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.IndexRedirectService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.GruppenService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.orga.GruppenAndernService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.LetzteTutorTermineService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.RestplatzeService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.ubungsconfig.UbungswocheConfigService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.anmeldung.ZeitslotService;
import de.hhu.propra.nimasichi.praktikumsplaner.services.github.GitHubService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({MethodSecurityConfiguration.class})
@WebMvcTest
@ExtendWith(SpringExtension.class)
public class ConfigContollerTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private GitHubService gitHubService;

  @MockBean
  private UbungswocheConfigRepo repo;

  @MockBean
  private LetzteTutorTermineService ttService;

  @MockBean
  private GruppenService gruppenService;

  @MockBean
  private ZeitslotRepo zsRepo;

  @SpyBean
  private ZeitslotService zsService;

  @MockBean
  private UbungswocheConfigService uwConfig;

  @MockBean
  private RestplatzeService rpService;

  @MockBean
  private HandleAuth handleAuth;

  @MockBean
  private IndexRedirectService anmoService;

  @MockBean
  private GruppenAndernService grAnService;

  private ConfigContollerTests() { }

  @Test
  @WithMockUser(roles = {"ORGA"})
  void configIndexTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
        .get("/konfiguration")
        .session(OauthFaker.makeUserSession()))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = {"ORGA"})
  void configIndexPostTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
        .post("/konfiguration/tutor_termine")
            .with(csrf())
            .session(OauthFaker.makeOrgaSession())
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
                    containsString(
                        "<input type=\"hidden\" name=\"anStartdatum\" value=\"2000-02-01\">"
                    )));
  }

  @Test
  @WithMockUser(roles = {"ORGA"})
  void tutorHinzufugenPostTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
        .post("/tutor_termin_hinzufugen")
            .with(csrf())
            .session(OauthFaker.makeOrgaSession())
            .param("name", "hallo welt!")
            .param("modus", "1")
            .param("anStartdatum", "2000-02-01")
            .param("anStartzeit", "20:00")
            .param("anSchlussdatum", "2000-02-08")
            .param("anSchlusszeit", "10:00")
            .param("minPersonen", "3")
            .param("maxPersonen", "5")
            .param("slotDatum", "2000-02-02")
            .param("tutorName", "Hans")
            .param("slotZeit", "14:00")
            .param("tutorTermine", "2021-03-25;03:03;Max", "2021-05-25;03:03;Peter"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().string(
                containsString(
                    "<input type=\"hidden\" name=\"tutorTermine\" value=\"2000-02-02;14:00;Hans\">"
                )))
            .andExpect(content().string(
                containsString(
                    "<input type=\"hidden\" name=\"tutorTermine\" value=\"2021-03-25;03:03;Max\">"
                )))
            .andExpect(content().string(
                containsString(
                    "<input type=\"hidden\" name=\"tutorTermine\" value=\"2021-05-25;03:03;Peter\">"
                )));
  }

  @Test
  @WithMockUser(roles = {"ORGA"})
  void tutorLoschenPostTest() throws Exception {
    final MvcResult mvcResult
        = mvc.perform(MockMvcRequestBuilders
        .post("/tutor_termin_loschen/1")
        .with(csrf())
        .session(OauthFaker.makeOrgaSession())
        .param("name", "hallo welt!")
        .param("modus", "1")
        .param("anStartdatum", "2000-02-01")
        .param("anStartzeit", "20:00")
        .param("anSchlussdatum", "2000-02-08")
        .param("anSchlusszeit", "10:00")
        .param("minPersonen", "3")
        .param("maxPersonen", "5")
        .param("tutorTermine",
            "2021-03-25;03:03;Max",
            "2021-05-25;03:03;Peter"))
        .andExpect(status().isOk())
        .andReturn();

    final var contentAsString
        = mvcResult
        .getResponse()
        .getContentAsString();

    assertThat(contentAsString)
        .contains(
            "<input type=\"hidden\" name=\"tutorTermine\" value=\"2021-03-25;03:03;Max\">"
        );
    assertThat(contentAsString)
        .doesNotContain(
            "<input type=\"hidden\" name=\"tutorTermine\" value=\"2021-05-25;03:03;Peter\">"
        );
  }

  @Test
  @WithMockUser(roles = {"ORGA"})
  void konfigurationAbschliessenPostTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders
        .post("/konfiguration_abschliessen")
        .with(csrf())
        .session(OauthFaker.makeOrgaSession())
        .param("name", "hallo welt!")
        .param("modus", "1")
        .param("anStartdatum", "2000-02-01")
        .param("anStartzeit", "20:00")
        .param("anSchlussdatum", "2000-02-08")
        .param("anSchlusszeit", "10:00")
        .param("minPersonen", "3")
        .param("maxPersonen", "5")
        .param("tutorTermine",
            "2021-03-25;03:03;Max",
            "2021-05-25;03:03;Peter"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().string(
            containsString("Anmeldestartzeit:")))
        .andExpect(content().string(
            containsString("<span>20:00</span>")))
        .andExpect(content().string(
            containsString("<td>Do 25.03. 03:03</td>")))
        .andExpect(content().string(
            containsString("<td>Max</td>")));
  }

  @Test
  @WithMockUser(roles = {"USER"})
  void konfigurationForbiddenForUser() throws Exception {
    mvc.perform(MockMvcRequestBuilders
        .get("/konfiguration")
        .session(OauthFaker.makeUserSession()))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
}
