package com.comepethome.gateway.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.comepethome.gateway.jwt.dto.TokenDTO;
import com.comepethome.gateway.jwt.exception.token.TokenExpireException;
import com.comepethome.gateway.jwt.exception.token.TokenNotVerifiedException;
import com.comepethome.gateway.jwt.exception.token.UnexpectedRefreshTokenException;
import com.comepethome.gateway.jwt.refreshToken.RefreshTokenManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtService {

    private static final String ACCESS_TOKEN_SUBJECT = "access-token";
    private static final String REFRESH_TOKEN_SUBJECT = "refresh-token";
    private static final String USERNAME_CLAIM = "userId";
    private static final String ROLES_CLAIM = "roles";

    @Value("${jwt.properties.secret}")
    private String secret;

    @Value("${jwt.properties.access-token-expiration-seconds}")
    private Long accessTokenExpirationSeconds;

    @Value("${jwt.properties.refresh-token-expiration-seconds}")
    private Long refreshTokenExpirationSeconds;

    @Value("${jwt.properties.token-prefix}")
    private String tokenPrefix;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @Autowired
    private RefreshTokenManager refreshTokenManager;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
    }

    public String createAccessTokenWithPrefix(String userId) {
        String accessToken = createAccessToken(userId, new ArrayList<>());
        return withTokenPrefix(accessToken);
    }

    private String createAccessToken(String username, List<String> roles) {
        return JWT.create().withSubject(ACCESS_TOKEN_SUBJECT)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpirationSeconds * 1000L))
                .withClaim(USERNAME_CLAIM, username)
                .withClaim(ROLES_CLAIM, roles)
                .sign(algorithm);
    }


    private String withTokenPrefix(String token) {
        return tokenPrefix + " " + token;
    }

    public int resolveAccessTokenWithPrefix(String accessTokenWithPrefix) {
        String token = removePrefix(accessTokenWithPrefix);
        int code = -1;
        try {
            verifier.verify(token);
            code = 200;
        } catch (TokenExpiredException e) {
            throw new TokenExpireException();
        } catch (JWTVerificationException e) {
            throw new TokenNotVerifiedException();
        }

        return code;
    }

    private String removePrefix(String token) {
        if (!token.startsWith(tokenPrefix + " ")) {
            throw new JWTVerificationException("Invalid Token Format");
        }
        return token.substring(tokenPrefix.length() + 1);
    }
    public String createRefreshTokenWithPrefix(String userId) {
        String refreshToken = createRefreshToken(userId);

        refreshTokenManager.update(userId, refreshToken);

        return withTokenPrefix(refreshToken);
    }

    private String createRefreshToken(String username) {
        return JWT.create().withSubject(REFRESH_TOKEN_SUBJECT)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpirationSeconds * 1000L))
                .withClaim(USERNAME_CLAIM, username)
                .sign(algorithm);

    }

    public TokenDTO reissue(String refreshTokenWithPrefix) {
        String refreshToken = removePrefix(refreshTokenWithPrefix);

        DecodedJWT decodedToken = verifier.verify(refreshToken);

        String userId= decodedToken.getClaim(USERNAME_CLAIM).asString();
        List<String> roles = decodedToken.getClaim(ROLES_CLAIM).asList(String.class);

        if(roles == null){
            roles = new ArrayList<>();
        }

        Optional<String> savedRefreshToken = refreshTokenManager.find(userId);

        if (savedRefreshToken.isEmpty() || !refreshToken.equals(savedRefreshToken.get())) {
            refreshTokenManager.delete(userId);
            throw new UnexpectedRefreshTokenException();
        }

        String newRefreshToken = withTokenPrefix(createRefreshToken(userId));
        refreshTokenManager.update(userId, newRefreshToken);

        String newAccessToken = withTokenPrefix(createAccessToken(userId, roles));

        return new TokenDTO(newAccessToken, newRefreshToken);
    }
}