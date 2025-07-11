package Mentoria.dc.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import Mentoria.dc.model.Mentor;
import Mentoria.dc.repository.MentorRepository;

@Service
public class MentorService {

    private final MentorRepository mentorRepository;

    public MentorService(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    public Mentor findByEmail(String email) {
        return mentorRepository.findByUser_Email(email).orElse(null);
    }



    public Mentor save(Mentor mentor) {
        return mentorRepository.save(mentor);
    }
}
