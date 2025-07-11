package Mentoria.dc.controller;

import Mentoria.dc.dto.UserRegistrationDto;
import Mentoria.dc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userRegistrationDto") UserRegistrationDto registrationDto) {
        System.out.println("▶️ POST /auth/register recibido: " + registrationDto.getEmail());
        userService.registerUser(registrationDto);
        return "redirect:/auth/login";
    }
}
