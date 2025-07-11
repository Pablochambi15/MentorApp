package Mentoria.dc.service;

import Mentoria.dc.dto.UserRegistrationDto;
import Mentoria.dc.model.Mentor;
import Mentoria.dc.model.User;
import Mentoria.dc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MentorService mentorService;       // ⬅️ inyectamos MentorService

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario y, si su rol es MENTOR, crea también la
     * entidad Mentor asociada.
     */
    @Transactional
    public void registerUser(UserRegistrationDto registrationDto) {

        // 1. Guardar el User
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setName(registrationDto.getName());
        user.setRole(registrationDto.getRole());   // Enum con valores MENTOR / ESTUDIANTE
        user.setProvider("local");
        userRepository.save(user);

        // 2. Si el rol es MENTOR, guardar Mentor asociado
        if (user.getRole().name().equals("MENTOR")) {    // ajusta según tu enum
            Mentor mentor = new Mentor();
            mentor.setUser(user);
            mentor.setName(user.getName());              // o registrationDto.getName()
            mentorService.save(mentor);
        }

        System.out.println("Usuario (y mentor si aplica) guardado: " + user.getEmail());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
