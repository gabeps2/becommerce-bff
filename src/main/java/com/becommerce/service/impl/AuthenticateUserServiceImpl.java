package com.becommerce.service.impl;

import com.becommerce.exception.AuthenticateUserException;
import com.becommerce.mapper.Mapper;
import com.becommerce.model.*;
import com.becommerce.model.enums.CustomerType;
import com.becommerce.model.enums.ErrorEnum;
import com.becommerce.repository.PartnerRepository;
import com.becommerce.repository.UserRepository;
import com.becommerce.service.AuthenticateUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpStatus;

import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.becommerce.model.enums.ErrorEnum.AUTHENTICATE_USER_ERROR;
import static com.becommerce.model.enums.ErrorEnum.INVALID_TOKEN_ERROR;
import static com.becommerce.utils.PasswordStorage.verifyPassword;

@Singleton
@Named("AuthenticateUserServiceImpl")
public class AuthenticateUserServiceImpl implements AuthenticateUserService {

    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;
    @Value("${JWT_EXPIRATION_TIME:15}")
    private String JWT_EXPIRATION_TIME;
    @Inject
    private PartnerRepository partnerRepository;

    @Inject
    private Mapper mapper;

    @Inject
    private UserRepository userRepository;

    @Override
    public SessionSchema authenticate(String email, String password) {
        SecretKey KEY = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        Optional<UserModel> userModel = userRepository.findByEmail(email);
        if (userModel.isEmpty()) throw throwsException(AUTHENTICATE_USER_ERROR, HttpStatus.PRECONDITION_FAILED);
        UserModel user = userModel.get();

        try {
            if (!verifyPassword(password, user.getPassword()))
                throw throwsException(AUTHENTICATE_USER_ERROR, HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            throw throwsException(AUTHENTICATE_USER_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Date expiration = Date.from(
                LocalDateTime.now()
                        .plusMinutes(Long.parseLong(JWT_EXPIRATION_TIME))
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

        String jwtToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuer("BeCommerce")
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(KEY)
                .compact();

        UserSchema userSchema = UserSchema.builder()
                .id(user.getId().toString())
                .name(user.getName())
                .userType(user.getType())
                .build();

        if (Objects.equals(user.getType(), CustomerType.PARTNER.getName()) && user.getPartner() != null) {
            userSchema.setPartnerSchema(mapper.toPartnerSchema(user.getPartner()));
        }

        return SessionSchema.builder()
                .accessToken(jwtToken)
                .expiresIn(JWT_EXPIRATION_TIME)
                .user(userSchema)
                .build();
    }

    public UUID getSubject(String token) {
        try {
            return UUID.fromString(Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject());
        } catch (Exception e) {
            throw throwsException(INVALID_TOKEN_ERROR, HttpStatus.PRECONDITION_FAILED);
        }
    }

    @Override
    public void validateToken(String token) {
        if (getSubject(token).toString().isEmpty())
            throw throwsException(INVALID_TOKEN_ERROR, HttpStatus.PRECONDITION_FAILED);
    }

    AuthenticateUserException throwsException(ErrorEnum errorEnum, HttpStatus httpStatus) {
        return new AuthenticateUserException(
                errorEnum.getMessage(),
                errorEnum.getDetailMessage(),
                errorEnum.getCode(),
                httpStatus
        );
    }
}
