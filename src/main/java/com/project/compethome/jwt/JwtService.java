package com.project.compethome.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.compethome.dto.TokenDTO;
import com.project.compethome.exception.token.UnexpectedRefreshTokenException;
import com.project.compethome.jwt.refreshToken.JpaRefreshTokenManager;
import com.project.compethome.jwt.refreshToken.RefreshTokenManager;
import com.project.compethome.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public String createAccessTokenWithPrefix(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::toString)
                .collect(Collectors.toList());

        String accessToken = createAccessToken(username, roles);

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

    public Authentication resolveAccessTokenWithPrefix(String accessTokenWithPrefix) {
        String token = removePrefix(accessTokenWithPrefix);
        DecodedJWT decodeToken = verifier.verify(token);

        String username = decodeToken.getClaim(USERNAME_CLAIM).asString();
        List<SimpleGrantedAuthority> roles = decodeToken.getClaim(ROLES_CLAIM)
                .asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return UsernamePasswordAuthenticationToken.authenticated(username, null, roles);
    }

    private String removePrefix(String token) {
        if (!token.startsWith(tokenPrefix + " ")) {
            throw new JWTVerificationException("Invalid Token Format");
        }
        return token.substring(tokenPrefix.length() + 1);
    }
    @Transactional
    public String createRefreshTokenWithPrefix(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        String refreshToken = createRefreshToken(username);

        refreshTokenManager.update(username, refreshToken);

        return withTokenPrefix(refreshToken);
    }

    private String createRefreshToken(String username) {
        return JWT.create().withSubject(REFRESH_TOKEN_SUBJECT)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpirationSeconds * 1000L))
                .withClaim(USERNAME_CLAIM, username)
                .sign(algorithm);

    }

    @Transactional
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
        String newAccessToken = withTokenPrefix(createAccessToken(userId, roles));

        return new TokenDTO(newAccessToken, newRefreshToken);
    }
}