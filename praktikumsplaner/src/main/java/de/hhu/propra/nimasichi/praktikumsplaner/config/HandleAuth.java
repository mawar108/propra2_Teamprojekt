package de.hhu.propra.nimasichi.praktikumsplaner.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("handles")
@Data
@NoArgsConstructor
public class HandleAuth {

  private List<String> tutorenHandles;
  private List<String> orgaHandles;

}
