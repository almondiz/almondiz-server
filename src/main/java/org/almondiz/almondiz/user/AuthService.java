package org.almondiz.almondiz.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.almondiz.almondiz.common.ValidStringUtils;
import org.almondiz.almondiz.exception.exception.*;
import org.almondiz.almondiz.nut.NutService;
import org.almondiz.almondiz.nut.entity.Nut;
import org.almondiz.almondiz.profileFile.ProfileFileService;
import org.almondiz.almondiz.profileFile.entity.ProfileFile;
import org.almondiz.almondiz.tag.TagService;
import org.almondiz.almondiz.tag.entity.Tag;
import org.almondiz.almondiz.user.dto.UserRegisterDto;
import org.almondiz.almondiz.user.entity.ProviderType;
import org.almondiz.almondiz.user.entity.Role;
import org.almondiz.almondiz.user.entity.Token;
import org.almondiz.almondiz.user.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserService userService;

    private final TagService tagService;

    private final NutService nutService;

    private final ProfileFileService profileFileService;

    private static final String secretKey = "almondiz-secret-2022-#$%";

    private static final String ACCESS_TOKEN_SUBJECT = "almondiz-access-token";

    private static final String REFRESH_TOKEN_SUBJECT = "almondiz-refresh-token";

    private static final String TOKEN_CLAIM = "email";

    private static final String TOKEN_ISSUER = "almondiz";

    private final Algorithm algorithm = Algorithm.HMAC512(secretKey);

    //    private static final long ACCESS_TOKEN_VALID_MILISECOND = 1000L * 60 * 60 * 3; 3시간
    private static final long ACCESS_TOKEN_VALID_MILISECOND = 1000L * 60 * 60 * 24 * 10; // 10일

    private static final long REFRESH_TOKEN_VALID_MILISECOND = 1000L * 60 * 60 * 24 * 14; // 14일

    private final JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(TOKEN_ISSUER).build();

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        return userService.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
    }

    public Token signIn(String userEmail) {
        userService.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        return createToken(userEmail);
    }

    @Transactional
    public Token signup(UserRegisterDto userRegisterDto) {
        if (nonNull(ValidStringUtils.getValidEmail(userRegisterDto.getEmail())) && userService.findByEmail(userRegisterDto.getEmail()).isPresent()) {
            throw new AccountExistedException();
        }

        ProfileFile profileFile = profileFileService.getProfileFileById(userRegisterDto.getProfileId());

        Tag tag = tagService.getTagById(userRegisterDto.getTagId());

        Nut nut = nutService.getNutById(userRegisterDto.getNutId());

        User user = new User(userRegisterDto.getEmail(),profileFile, tag, nut, ProviderType.GOOGLE, Role.USER);
        userService.saveUser(user);
        return createToken(user.getEmail());
    }

    private Token createToken(String userEmail) {
        Date now = new Date();

        String newAccessToken = createAccessToken(userEmail, now);

        String newRefreshToken = createRefreshToken(userEmail, now);

        return Token.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
    }

    private String createAccessToken(String userEmail, Date now) {
        return JWT.create()
                  .withIssuer(TOKEN_ISSUER)
                  .withIssuedAt(now)
                  .withExpiresAt(new Date(now.getTime() + ACCESS_TOKEN_VALID_MILISECOND))
                  .withSubject(ACCESS_TOKEN_SUBJECT)
                  .withClaim(TOKEN_CLAIM, userEmail)
                  .sign(algorithm);
    }

    private String createRefreshToken(String userEmail, Date now) {
        return JWT.create()
                  .withIssuer(TOKEN_ISSUER)
                  .withIssuedAt(now)
                  .withExpiresAt(new Date(now.getTime() + REFRESH_TOKEN_VALID_MILISECOND))
                  .withSubject(REFRESH_TOKEN_SUBJECT)
                  .withClaim(TOKEN_CLAIM, userEmail)
                  .sign(algorithm);
    }

    @Transactional
    public Token refreshTokenAccessToken(String userEmail, String refreshToken) {
        if (getDecodedToken(refreshToken).isPresent()) {
            Date now = new Date();
            DecodedJWT decodedJWT = getDecodedToken(refreshToken).get();
            if (!checkRefreshTokenSubject(decodedJWT)) {
                throw new RefreshTokenException();
            }
            if (decodedJWT.getExpiresAt().after(now)) {
                String newAccessToken = createAccessToken(userEmail, now);
                String newRefreshToken = refreshToken;
                if (decodedJWT.getExpiresAt()
                              .before(new Date(now.getTime() + (1000L * 60 * 60 * 24 * 30)))) {
                    newRefreshToken = createRefreshToken(userEmail, now);
                }
                return Token.builder()
                            .accessToken(newAccessToken)
                            .refreshToken(newRefreshToken)
                            .build();
            } else {
                throw new ExpiredTokenException();
            }
        } else {
            throw new RefreshTokenException();
        }
    }

    public Authentication getAuthentication(String token) {
        User user = loadUserByToken(token);
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    public User loadUserByToken(String token) {
        return getDecodedToken(token)
            .map(this::getUserEmailFromToken)
            .flatMap(userService::findByEmail)
            .orElseThrow(TokenUserNotFoundException::new);
    }

    private Optional<DecodedJWT> getDecodedToken(String token) {
        try {
            return Optional.of(jwtVerifier.verify(token));
        } catch (JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    private String getUserEmailFromToken(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim(TOKEN_CLAIM).asString();
    }

    @Transactional
    public boolean validateToken(String token) {
        Date now = new Date();
        Optional<DecodedJWT> decodedJWT = getDecodedToken(token);
        if (decodedJWT.isEmpty()) {
            return false;
        }

        if (decodedJWT.get().getExpiresAt().before(now)) {
            throw new ExpiredTokenException();
        }

        return nonNull(loadUserByToken(token));
    }

    private boolean checkRefreshTokenSubject(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject().equals(REFRESH_TOKEN_SUBJECT);
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("AUTH-TOKEN");
    }

}
