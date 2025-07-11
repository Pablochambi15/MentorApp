package Mentoria.dc.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Mentor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación 1‑1 con la tabla User (misma que usas en Estudiante)
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Datos básicos
    private String name;
    private String profession;   // Ej: “Ingeniero de Software”
    private String company;      // Opcional
    private String email;        // Puedes omitir si lo lees de User
    private String phone;
    private String profileImage; // ruta o nombre de archivo

    // Áreas de expertise del mentor
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> specialties;   // Ej: ["Java", "Spring", "Arquitectura"]

    // Breve resumen o biografía
    @Column(length = 2048)
    private String bio;

    /* ---------- Getters y Setters ---------- */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProfession() { return profession; }
    public void setProfession(String profession) { this.profession = profession; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public List<String> getSpecialties() { return specialties; }
    public void setSpecialties(List<String> specialties) { this.specialties = specialties; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}