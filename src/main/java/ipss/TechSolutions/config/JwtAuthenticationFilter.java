package ipss.TechSolutions.config;

//Este es el middleware, que interceptará cada petición HTTP, buscará el JWT, y si es válido, dejará pasar al usuario.
import ipss.TechSolutions.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Obtener el header Authorization
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String correoUsuario;

        // 2. Si no hay token o no empieza con "Bearer ", continuamos con el filtro normal (bloqueo)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el token (quitando la palabra "Bearer ")
        jwt = authHeader.substring(7);

        try {
            correoUsuario = jwtUtil.extraerCorreo(jwt);

            // 4. Si el token es válido y no hay una autenticación previa en este hilo
            if (correoUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.validarToken(jwt)) {
                    // Creamos el objeto de autenticación y lo guardamos en el contexto de Spring
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            correoUsuario, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Token inválido, dejamos que Spring Security maneje el rechazo
        }

        filterChain.doFilter(request, response);
    }
}
