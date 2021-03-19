package de.hhu.propra.nimasichi.praktikumsplaner.services.github;

import com.google.common.io.Files;
import de.hhu.propra.nimasichi.praktikumsplaner.PraktikumsplanerApplication;
import de.hhu.propra.nimasichi.praktikumsplaner.domain.dto.GruppeDto;
import de.hhu.propra.nimasichi.praktikumsplaner.utility.RepoNameHelper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@Service
@SuppressWarnings({
    "PMD.AtLeastOneConstructor",
    "PMD.LawOfDemeter",
    "PMD.DataflowAnomalyAnalysis"
})
public class GitHubService {
  @Value("${key_location}")
  private transient String keyLoc;

  @Value("${app_id}")
  private transient String appId;

  @Value("${installation}")
  private transient long installation;

  @Value("${organization_name}")
  private transient String organizationName;

  private transient GHOrganization organization;

  private transient GitHub gitHub;

  public void connect() {
    String jwtToken;

    try {
      jwtToken = createJwt(keyLoc, appId, 300_000);

      final var preAuth = new GitHubBuilder()
              .withJwtToken(jwtToken).build();
      final var appInstallation = preAuth.getApp()
              .getInstallationById(installation);
      final var token = appInstallation.createToken().create();

      gitHub = new GitHubBuilder()
              .withAppInstallationToken(token.getToken()).build();

      organization = gitHub.getOrganization(organizationName);
    } catch (IOException e) {
      LoggerFactory.getLogger(PraktikumsplanerApplication.class)
              .error(e.getMessage());
    }
  }

  public boolean doesUserExist(final String ghHandle)  {
    boolean exists;

    try {
      exists = gitHub.getUser(ghHandle) != null;
    } catch (IOException e) {
      exists = false;
    }

    return exists;
  }

  public boolean doUsersExist(final String... ghHandles) {
    boolean exist = true;

    for (final var ghHandle : ghHandles) {
      if (!doesUserExist(ghHandle)) {
        exist = false;
        break;
      }
    }

    return exist;
  }

  public void createRepositories(final Set<GruppeDto> gruppenDto,
                                 final LocalDateTime ubungsAnfang) {
    gruppenDto.forEach(g -> createRepository(
        RepoNameHelper.getRepoName(g.getGruppenName(), ubungsAnfang),
        g.getMitglieder()));
  }

  private void createRepository(final String repoName,
                                 final Set<String> collaborators) {
    try {
      if (organization == null) {
        return;
      }
      final var ghRepository = organization
          .createRepository(repoName).create();

      final var users = new ArrayList<>();
      final var connect = GitHub.connect();

      for (final var collaborator : collaborators) {
        users.add(connect.getUser(collaborator));
      }

      ghRepository.addCollaborators(users.toArray(new GHUser[0]));
    } catch (IOException e) {
      LoggerFactory.getLogger(PraktikumsplanerApplication.class)
          .error(e.getMessage());
    }

  }

  private static PrivateKey get(final String filename) {
    byte[] keyBytes;
    PrivateKey privateKey = null;

    try {
      keyBytes = Files.toByteArray(new File(filename));
      final var spec = new PKCS8EncodedKeySpec(keyBytes);
      final var keyFactory = KeyFactory.getInstance("RSA");
      privateKey = keyFactory.generatePrivate(spec);
    } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      LoggerFactory.getLogger(PraktikumsplanerApplication.class)
              .error(e.getMessage());
    }

    return privateKey;
  }

  public static String createJwt(final String keyLocation,
                                 final String githubAppId,
                                 final long ttlMillis) {
    //The JWT signature algorithm we will be using to sign the token
    final var sigAlg = SignatureAlgorithm.RS256;

    final long nowMillis = System.currentTimeMillis();
    final Date now = new Date(nowMillis);
    String jwt;

    //We will sign our JWT with our private key
    final Key signingKey = get(keyLocation);
    if (signingKey == null) {
      jwt = "";
    } else {
      //Let's set the JWT Claims
      final JwtBuilder builder = Jwts.builder()
              .setIssuedAt(now)
              .setIssuer(githubAppId)
              .signWith(signingKey, sigAlg);

      //if it has been specified, let's add the expiration
      if (ttlMillis > 0) {
        final long expMillis = nowMillis + ttlMillis;
        final Date exp = new Date(expMillis);
        builder.setExpiration(exp);
      }
      //Builds the JWT and serializes it to a compact, URL-safe string
      jwt = builder.compact();

    }

    return jwt;
  }


  public boolean isReady() {
    boolean ready = true;
    if (organization == null) {
      ready = false;
    }
    try {
      GitHub.connect();
    } catch (IOException e) {
      ready = false;
    }
    return ready;
  }
}
