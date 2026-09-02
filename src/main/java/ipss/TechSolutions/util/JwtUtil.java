package ipss.TechSolutions.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

    @Component
    public class JwtUtil { //Esta clase se encarga exclusivamente de fabricar el token.

        // Lee los valores de tu application.properties
        @Value("${jwt.secret}")
        private String secret;

        @Value("${jwt.expiration}")
        private Long expiration;

        public String generarToken(String correo) {
            return Jwts.builder()
                    .setSubject(correo)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                    .compact();
        }

        //Estos metodos permitirán extraer el correo del token y verificar que no haya sido alterado.
        public String extraerCorreo(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }

        public boolean validarToken(String token) {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                        .build()
                        .parseClaimsJws(token);
                return true;
            } catch (Exception e) {
                return false; // El token expiró o es inválido
            }
        }
}
