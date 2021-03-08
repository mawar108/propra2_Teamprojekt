package de.hhu.propra.nimasichi.praktikumsplaner.github;

import com.google.common.io.Files;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Date;

@Component
@SuppressWarnings("PMD.AtLeastOneConstructor")
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

  public void connect() throws Exception {
    final String jwtToken = createJwt(keyLoc, appId, 600_000);
    final var preAuth = new GitHubBuilder().withJwtToken(jwtToken).build();
    final var appInstallation = preAuth.getApp().getInstallationById(installation);
    final var token = appInstallation.createToken().create();
    final var gitHub = new GitHubBuilder().withAppInstallationToken(token.getToken()).build();

    organization = gitHub.getOrganization(organizationName);
  }

  public void createRepository(final String repoName,
                               final String... collaborators) throws IOException {
    final var ghRepository = organization.createRepository(repoName).create();
    final var connect = GitHub.connect();
    final var users = new ArrayList<>();

    for (final var collaborator : collaborators) {
      users.add(connect.getUser(collaborator));
    }

    ghRepository.addCollaborators(users.toArray(new GHUser[0]));
  }

  private static PrivateKey get(final String filename) throws Exception {
    final var keyBytes = Files.toByteArray(new File(filename));

    final var spec = new PKCS8EncodedKeySpec(keyBytes);
    final var keyFactory = KeyFactory.getInstance("RSA");
    return keyFactory.generatePrivate(spec);
  }

  public static String createJwt(final String keyLocation,
                                 final String githubAppId,
                                 final long ttlMillis) throws Exception {
    //The JWT signature algorithm we will be using to sign the token
    final var sigAlg = SignatureAlgorithm.RS256;

    final long nowMillis = System.currentTimeMillis();
    final Date now = new Date(nowMillis);

    //We will sign our JWT with our private key
    final Key signingKey = get(keyLocation);

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
    return builder.compact();
  }
}
