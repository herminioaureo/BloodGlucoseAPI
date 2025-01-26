package com.bloodglucose.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bloodglucose.api.core.dto.RecoveryJwtTokenRecord;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;


@Service
public class JwtTokenService {

    private static final String SECRET_KEY = "4Z^XrroxR@dWxqf$mTTKwW$!@#qGr4P"; // Chave secreta utilizada para gerar e verificar o token
    private static final String ISSUER = "bloodglucose-api"; // Emissor do token

    public RecoveryJwtTokenRecord generateToken(UserDetailsImpl user) {
        try {
            // Define o algoritmo HMAC SHA256 para criar a assinatura do token passando a chave secreta definida
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            Instant expiresTime = expirationDate();

            String generatedToken = JWT.create()
                                .withIssuer(ISSUER) // Define o emissor do token
                                .withIssuedAt(creationDate()) // Define a data de emissão do token
                                .withExpiresAt(expiresTime) // Define a data de expiração do token
                                .withSubject(user.getUsername()) // Define o assunto do token (neste caso, o nome de usuário)
                                .sign(algorithm); // Assina o token usando o algoritmo especificado

            String expiresAt = expiresTime.toString();

            return new RecoveryJwtTokenRecord(generatedToken, expiresAt);
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {

            token = token.replace("\"", "");
            // Define o algoritmo HMAC SHA256 para verificar a assinatura do token passando a chave secreta definida
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER) // Define o emissor do token
                    .build()
                    .verify(token) // Verifica a validade do token
                    .getSubject(); // Obtém o assunto (neste caso, o nome de usuário) do token
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token inválido ou expirado.", exception);
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).plusHours(4).toInstant();
    }

}
