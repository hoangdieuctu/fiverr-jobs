package com.greg.authservice.service;

import com.greg.authservice.dto.AuthDto;
import com.greg.authservice.dto.TokenDto;
import com.greg.authservice.dto.UserDto;
import com.greg.authservice.exception.ServiceException;
import com.greg.authservice.service.downstream.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Component
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Value("${jwt.token.expire-in-minutes}")
    private Long tokenExpireTime;

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    @Value("${jwt.token.issuer}")
    private String issuer;

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public TokenDto login(AuthDto auth) {
        if (StringUtils.isBlank(auth.getUsername()) || StringUtils.isBlank(auth.getPassword())) {
            throw new ServiceException("Username and password must be provided.");
        }
        UserDto user = userService.searchUser(auth.getUsername());
        var errorMessage = "Username or password is not match.";
        if (user == null) {
            logger.warn("Username not found for [{}]", auth.getUsername());
            throw new ServiceException(errorMessage);
        }
        if (!user.getPassword().equals(auth.getPassword())) {
            logger.warn("Password does not match");
            throw new ServiceException(errorMessage);
        }
        return generateJwtToken(user);
    }

    private TokenDto generateJwtToken(UserDto user) {
        var signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        var now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        var signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        var builder = Jwts.builder()
                .setId(String.valueOf(user.getId()))
                .setIssuedAt(now)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        long expMillis = nowMillis + (tokenExpireTime * 1000 * 60);
        var exp = new Date(expMillis);
        builder.setExpiration(exp);

        var token = builder.compact();
        return new TokenDto(token, expMillis, user.getScope());
    }
}
