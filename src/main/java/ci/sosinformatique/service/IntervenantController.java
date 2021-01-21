package ci.sosinformatique.service;

import ci.sosinformatique.dao.IntervenantRepository;
import ci.sosinformatique.entites.Intervenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin("*")
public class IntervenantController {
    @Autowired
    IntervenantRepository intervenantRepository;

    @GetMapping("/intervenants")
    public List<Intervenant> getInterv() {
        return intervenantRepository.findAll();
    }
}
