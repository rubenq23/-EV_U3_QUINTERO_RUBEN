package ipss.TechSolutions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    // Inyectamos nuestro middleware
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desactiva la protección CSRF (obligatorio para APIs REST que reciben POST)
                .csrf(csrf -> csrf.disable())

                // 2. Configura los permisos de las rutas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/health").permitAll()  // Health check público
                        .requestMatchers("/api/auth/**").permitAll() // Deja público todo lo que empiece con /api/auth/ (Registro y Login)
                        .requestMatchers("/login", "/registro").permitAll() // Permite a cualquier persona ver las pantallas de login y registro.
                        .anyRequest().authenticated()                // Exige autenticación para cualquier otra ruta futura
                )

                // 3. Apaga las ventanas y formularios de login por defecto de Spring Security
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())
                // Añadimos nuestro middleware ANTES del filtro de seguridad estándar de Spring
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 4. Esta es la función que el AuthController usará para cifrar la clave
        return new BCryptPasswordEncoder();
    }
}
