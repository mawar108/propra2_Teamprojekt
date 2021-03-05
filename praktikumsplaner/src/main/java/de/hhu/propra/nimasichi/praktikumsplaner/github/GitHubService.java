package de.hhu.propra.nimasichi.praktikumsplaner.github;

import com.google.common.io.Files;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

public class GithubConnector {
    @Value("${key_location}")
    private static String keyLoc;

    @Value("${app_id}")
    private static String appId;

    @Value("${installation}")
    private static long installation;

    @Value("${organization_name}")
    private static String organizationName;

    public static void connect() throws Exception {
        final String jwtToken = createJWT(keyLoc, appId, 600000);

        final var preAuth = new GitHubBuilder().withJwtToken(jwtToken).build();
        final var appInstallation = preAuth.getApp().getInstallationById(installation);
        final var token = appInstallation.createToken().create();
        final var gitHub = new GitHubBuilder().withAppInstallationToken(token.getToken()).build();
        final var organization = gitHub.getOrganization(organizationName);


    }

    private static PrivateKey get(final String filename) throws Exception {
        final var keyBytes = Files.toByteArray(new File(filename));

        final var spec = new PKCS8EncodedKeySpec(keyBytes);
        final var kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public static String createJWT(final String keyLocation, String githubAppId, long ttlMillis) throws Exception {
        //The JWT signature algorithm we will be using to sign the token
        final var signatureAlgorithm = SignatureAlgorithm.RS256;

        final long nowMillis = System.currentTimeMillis();
        final Date now = new Date(nowMillis);

        //We will sign our JWT with our private key
        final Key signingKey = get(keyLocation);

        //Let's set the JWT Claims
        final JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setIssuer(githubAppId)
                .signWith(signingKey, signatureAlgorithm);

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
