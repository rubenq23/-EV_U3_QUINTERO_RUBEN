package ipss.TechSolutions.controller;

import ipss.TechSolutions.model.Usuario;
import ipss.TechSolutions.repository.UsuarioRepository;
import ipss.TechSolutions.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // Agregamos la utilidad del JWT

    // Inyección de dependencias
    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registro")
    public ResponseEntity registrarUsuario(@RequestBody Usuario nuevoUsuario) {
        // 1. Verificar si el correo ya está registrado
        if (usuarioRepository.findByCorreo(nuevoUsuario.getCorreo()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: El correo ya está en uso.");
        }

        // 2. Cifrar la clave antes de guardarla
        String claveCifrada = passwordEncoder.encode(nuevoUsuario.getClave());
        nuevoUsuario.setClave(claveCifrada);

        // 3. Guardar el usuario en la base de datos MySQL
        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok("Usuario registrado con éxito.");
    }

    @PostMapping("/login")
    public ResponseEntity iniciarSesion(@RequestBody Usuario loginData) {
        // 1. Buscar si el correo existe en la base de datos
        Optional usuarioOpt = usuarioRepository.findByCorreo(loginData.getCorreo());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Error: Usuario no encontrado.");
        }

        Usuario usuario = (Usuario) usuarioOpt.get();

        // 2. Comparar la clave que envía el usuario con la clave cifrada en la BD
        // El método matches() hace la magia de comparar texto plano vs BCrypt
        if (!passwordEncoder.matches(loginData.getClave(), usuario.getClave())) {
            return ResponseEntity.status(401).body("Error: Credenciales incorrectas.");
        }

        // 3. Si todo es correcto, generamos y devolvemos el JWT
        String token = jwtUtil.generarToken(usuario.getCorreo());
        return ResponseEntity.ok(token);
    }
}
