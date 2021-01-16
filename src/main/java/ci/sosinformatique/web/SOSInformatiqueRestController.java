package ci.sosinformatique.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ci.sosinformatique.entites.ClientSociete;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@CrossOrigin("*")
public class SOSInformatiqueRestController {
	@PostMapping("/ajoutSociete")
	@javax.transaction.Transactional
	public ClientSociete ajoutClientSociete(@RequestBody FormClientSociete formClientSociete) {

		return null;
		
	}
}

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
class FormClientSociete{
	String nomSoc;
	String telSoc;
	String emailSoc;
	boolean sousContratSoc;
	String lieuSoc;
}
