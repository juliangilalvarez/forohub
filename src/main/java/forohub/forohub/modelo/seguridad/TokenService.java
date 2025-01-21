package forohub.forohub.modelo.seguridad;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import forohub.forohub.domain.usuario.Usuario;
import forohub.forohub.modelo.exceptions.TokenServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TokenService {

    @Value("${forohub.jwtservice.issuer}")
    private String jwtServiceIssuer;

    @Value("${forohub.jwtservice.secret}")
    private String jwtServiceSecret;

    @Value("${forohub.jwtservice.expiration_hours}")
    private int jwtServiceExpirationHours;

    private Instant generarLapsoExpiracion() {
        var systemNow = LocalDateTime.now();
        var zonedNow = systemNow.atZone(ZoneId.systemDefault());
        return zonedNow.plusHours(jwtServiceExpirationHours).toInstant();
    }

    public String crearToken(Usuario usuario) {
        try {
            return JWT.create()
                    .withIssuer(jwtServiceIssuer)
                    .withSubject(usuario.getCorreoelectronico())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarLapsoExpiracion())
                    .sign(Algorithm.HMAC256(jwtServiceSecret));
        } catch (JWTCreationException e) {
            throw new TokenServiceException("Error en el token", e);
        }
    }

    public String obtenerCorreoElectronico(String token) {
        if (token == null || token.isBlank()) {
            throw new TokenServiceException("Omision de token");
        }
        String correoElectronico;
        try {
            correoElectronico = JWT.require(Algorithm.HMAC256(jwtServiceSecret))
                    .withIssuer(jwtServiceIssuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new TokenServiceException("Token inválido", e);
        }
        return correoElectronico;
    }

}