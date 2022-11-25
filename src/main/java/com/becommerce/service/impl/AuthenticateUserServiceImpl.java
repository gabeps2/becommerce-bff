package com.becommerce.service.impl;

import com.becommerce.exception.AuthenticateUserException;
import com.becommerce.model.PartnerModel;
import com.becommerce.model.SessionSchema;
import com.becommerce.model.UserModel;
import com.becommerce.model.UserSchema;
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
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static com.becommerce.utils.PasswordStorage.verifyPassword;

@Named
public class AuthenticateUserServiceImpl implements AuthenticateUserService {

    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;
    @Value("${JWT_EXPIRATION_TIME:15}")
    private String JWT_EXPIRATION_TIME;
    @Inject
    private PartnerRepository partnerRepository;

    @Inject
    private UserRepository userRepository;

    @Override
    public SessionSchema authenticate(String email, String password) {
        SecretKey KEY = Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        Optional<UserModel> userModel = userRepository.findByEmail(email);

        if (!userModel.isPresent()) throwsException(HttpStatus.PRECONDITION_FAILED);

        UserModel user = userModel.get();

        try {
            if (!verifyPassword(password, user.getPassword())) throwsException(HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            throwsException(HttpStatus.UNPROCESSABLE_ENTITY);
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

        return SessionSchema.builder()
                .accessToken(jwtToken)
                .expiresIn(JWT_EXPIRATION_TIME)
                .user(userSchema)
                .build();
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
