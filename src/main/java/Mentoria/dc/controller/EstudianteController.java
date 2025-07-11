package Mentoria.dc.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import Mentoria.dc.model.Estudiante;
import Mentoria.dc.repository.EstudianteRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    private final EstudianteRepository estudianteRepository;

    public EstudianteController(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "estudiantes/dashboard-estudiante";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        Estudiante estudiante = (Estudiante) session.getAttribute("student");
        if (estudiante == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("student", estudiante);
        model.addAttribute("mentorCount", 0);
        model.addAttribute("sessionCount", 0);
        model.addAttribute("rating", 0);
        model.addAttribute("followedMentors", List.of());
        model.addAttribute("recentSessions", List.of());

        return "estudiantes/perfil-estudiante";
    }

    @GetMapping("/mentores")
    public String mentores() {
        return "estudiantes/mentores-estudiante";
    }

    @GetMapping("/sesiones")
    public String sesiones() {
        return "estudiantes/sesiones-estudiante";
    }

    @GetMapping("/chat")
    public String chat() {
        return "estudiantes/chat-estudiante";
    }

    @PostMapping("/perfil/update")
public String actualizarPerfil(@RequestParam("name") String name,
                               @RequestParam("university") String university,
                               @RequestParam("career") String career,
                               @RequestParam("phone") String phone,
                               @RequestParam(value = "interestsInput", required = false) String interestsInput,
                               @RequestParam("profileImage") MultipartFile imagen,
                               HttpSession session) {

    Estudiante estudiante = (Estudiante) session.getAttribute("student");

    if (estudiante != null) {
        estudiante.setName(name);
        estudiante.setUniversity(university);
        estudiante.setCareer(career);
        estudiante.setPhone(phone);

        if (interestsInput != null) {
            List<String> intereses = Arrays.stream(interestsInput.split(","))
                                           .map(String::trim)
                                           .toList();
            estudiante.setInterests(intereses);
        }

        if (!imagen.isEmpty()) {
            try {
                // Crear nombre único
                String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();

                // Carpeta de destino (fuera de resources)
                Path directorio = Paths.get("uploads/img/perfil");

                // Crear carpeta si no existe
                Files.createDirectories(directorio);

                // Ruta final completa
                Path rutaCompleta = directorio.resolve(nombreArchivo);

                // Guardar archivo en disco
                imagen.transferTo(rutaCompleta.toFile());

                // Ruta accesible desde HTML
                estudiante.setProfileImage("/uploads/img/perfil/" + nombreArchivo);

                // Verificar en consola
                System.out.println("Imagen guardada en: " + rutaCompleta.toAbsolutePath());
                System.out.println("Ruta guardada en BD: /uploads/img/perfil/" + nombreArchivo);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        estudianteRepository.save(estudiante);
        session.setAttribute("student", estudiante);
    }

    return "redirect:/estudiante/perfil";
}


}
