package Mentoria.dc.controller;

import Mentoria.dc.model.Mentor;
import Mentoria.dc.service.MentorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/mentor")
public class MentorController {

    private final MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    /** 
     * Dashboard principal del mentor.
     * 1. Obtiene el email del usuario autenticado.
     * 2. Carga/guarda el mentor en sesión.
     * 3. Pone en el modelo TODO lo que la plantilla espera.
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal, HttpSession session) {

        // 1. Email que Spring Security entrega como “username”
        String email = principal.getName();

        // 2. Intenta traer de sesión
        Mentor mentor = (Mentor) session.getAttribute("mentor");

        if (mentor == null) {
            mentor = mentorService.findByEmail(email);
            if (mentor == null) {                   // Si realmente no existe
                return "error/mentor-no-encontrado";
            }
            session.setAttribute("mentor", mentor); // Guardar en sesión
        }

        // 3. Atributos mínimos que tu dashboard usa
        model.addAttribute("mentor", mentor);
        model.addAttribute("sesionesCompletadas", 0);   // TODO: llénalo desde base de datos
        model.addAttribute("calificacionPromedio", 0.0);
        model.addAttribute("estudiantesActivos", 0);
        model.addAttribute("horasMentoria", 0);
        model.addAttribute("proximasSesiones", List.of());   // lista vacía inicial
        model.addAttribute("estudiantesRecientes", List.of());

        return "mentores/dashboard-mentor";
    }

    // Ejemplos de otras rutas vacías
    @GetMapping("/sesiones")
    public String sesiones() {
        return "mentores/sesiones-mentor";
    }

    @GetMapping("/estudiantes")
    public String estudiantes() {
        return "mentores/estudiantes-mentor";
    }

    @GetMapping("/perfil")
    public String perfil() {
        return "mentores/perfil-mentor";
    }

    @GetMapping("/chat")
    public String chat() {
        return "mentores/chat-mentor";
    }
}
