package Mentoria.dc.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    // Campo auxiliar para recibir los intereses como texto (no se persiste)
    @Transient
    private String interestsInput;

    public String getInterestsInput() {
        return interestsInput;
    }

    public void setInterestsInput(String interestsInput) {
        this.interestsInput = interestsInput;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la tabla User
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;
    private String university;
    private String career;
    private String email;   // opcional si no quieres duplicarlo con el user
    private String phone;

    private String profileImage; // ruta o nombre de archivo

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> interests;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
