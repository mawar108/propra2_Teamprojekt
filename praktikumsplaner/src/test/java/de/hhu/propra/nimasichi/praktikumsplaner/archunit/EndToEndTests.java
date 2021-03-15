package de.hhu.propra.nimasichi.praktikumsplaner.archunit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EndToEndTests {

  @Autowired
  private TestRestTemplate template;

  private static MultiValueMap<String, String>
          formData(final Map<String, String> form) {
    final var request = new LinkedMultiValueMap<String, String>();
    form.forEach(request::add);
    return request;
  }

  @Test
  @Disabled
  void testIndexThroughKonfigurationAbschliessen() {
    ResponseEntity<String> response;

    response = template.getForEntity("/", String.class);

    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody()).contains("Konfiguration").doesNotContain("PÜ 1");

    response = template.getForEntity("/konfiguration", String.class);

    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getBody()).contains("PÜ 1");
  }

}
