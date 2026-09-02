package ipss.TechSolutions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Retorna el archivo login.html
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro"; // Retorna el archivo registro.html
    }
}
