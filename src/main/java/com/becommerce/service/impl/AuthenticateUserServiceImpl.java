package com.becommerce.service.impl;

import com.becommerce.exception.AuthenticateUserException;
import com.becommerce.model.PartnerModel;
import com.becommerce.model.SessionResponse;
import com.becommerce.model.enums.ErrorEnum;
import com.becommerce.repository.PartnerRepository;
import com.becommerce.repository.SessionRepository;
import com.becommerce.service.AuthenticateUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpStatus;

import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.inject.Named;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.becommerce.utils.PasswordStorage.verifyPassword;

@Named
public class AuthenticateUserServiceImpl implements AuthenticateUserService {

    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;
    @Value("${JWT_EXPIRATION_TIME:15}")
    private String JWT_EXPIRATION_TIME;
    @Inject
    private PartnerRepository partnerRepository;

    @Override
    public SessionResponse authenticate(String email, String password) {
        SecretKey KEY = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        Optional<PartnerModel> partnerModel = partnerRepository.findByEmail(email);

        if (!partnerModel.isPresent()) {
            throwsException(HttpStatus.PRECONDITION_FAILED);
        }

        PartnerModel partner = partnerModel.get();

        boolean isValidPassword = false;

        try {
            isValidPassword = verifyPassword(password, partner.getPassword());
        } catch (Exception e) {
            throwsException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!isValidPassword) {
            throwsException(HttpStatus.PRECONDITION_FAILED);
        }

        Date expiration = Date.from(
                LocalDateTime.now().plusMinutes(Long.parseLong(JWT_EXPIRATION_TIME))
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

        String jwtToken = Jwts.builder()
                .setSubject(partner.getId().toString())
                .setIssuer("BeCommerce")
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(KEY)
                .compact();

        SessionResponse sessionResponse = new SessionResponse();
        sessionResponse.setAccessToken(jwtToken);
        sessionResponse.setExpiresIn(JWT_EXPIRATION_TIME);
        return sessionResponse;
    }

    void throwsException(HttpStatus httpStatus) {
        throw new AuthenticateUserException(
                ErrorEnum.AUTHENTICATE_USER_ERROR.getMessage(),
                ErrorEnum.AUTHENTICATE_USER_ERROR.getDetailMessage(),
                ErrorEnum.AUTHENTICATE_USER_ERROR.getCode(),
                httpStatus
        );
    }
}
