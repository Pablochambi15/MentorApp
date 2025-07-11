package Mentoria.dc.security;

import Mentoria.dc.model.Estudiante;
import Mentoria.dc.model.User;
import Mentoria.dc.repository.EstudianteRepository;
import Mentoria.dc.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final EstudianteRepository estudianteRepository;
    private final UserRepository userRepository;

    public CustomAuthenticationSuccessHandler(EstudianteRepository estudianteRepository, UserRepository userRepository) {
        this.estudianteRepository = estudianteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (auth.getAuthority().equals("ROLE_ESTUDIANTE")) {

                // Si el estudiante no existe aún, lo creamos
                Estudiante estudiante = estudianteRepository.findByUser_Email(email).orElseGet(() -> {
                    User user = userRepository.findByEmail(email).orElseThrow();
                    Estudiante nuevoEstudiante = new Estudiante();
                    nuevoEstudiante.setUser(user);
                    nuevoEstudiante.setName(user.getName());
                    nuevoEstudiante.setEmail(user.getEmail());
                    return estudianteRepository.save(nuevoEstudiante);
                });

                session.setAttribute("student", estudiante);
                response.sendRedirect("/estudiante/dashboard");
                return;
            }

            if (auth.getAuthority().equals("ROLE_MENTOR")) {
                response.sendRedirect("/mentor/dashboard");
                return;
            }
        }

        response.sendRedirect("/dashboard");
    }
}
