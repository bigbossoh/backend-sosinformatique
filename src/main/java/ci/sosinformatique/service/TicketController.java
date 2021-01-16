package ci.sosinformatique.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ci.sosinformatique.dao.ClientSocieteRepository;
import ci.sosinformatique.dao.TicketRepository;
import ci.sosinformatique.entites.ClientSociete;
import ci.sosinformatique.entites.Ticket;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("api/v1/")
@CrossOrigin("*")
public class TicketController {
	DateFormat dateFormatHeure = new SimpleDateFormat("HH:mm");
	DateFormat dateFormatJour = new SimpleDateFormat("dd-MM-yyyy");
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	ClientSocieteRepository clientSocieteRepository;

	@GetMapping("/listDesTickets")
	public List<Ticket> getTick() {
		return ticketRepository.findAll();
	}

	@GetMapping("/listClientSocie")
	public List<ClientSociete> getCliSo() {
		return clientSocieteRepository.findAll(Sort.by("itcketRecent").descending());
	}
	
	@PostMapping("/creerTicket")
	@Transactional
	public boolean creerTicket(@RequestBody TicketForm ticketForm) {
		Ticket ticket= new Ticket();
		Optional<ClientSociete>cs=clientSocieteRepository.findById(ticketForm.getClientSoc());
		if(cs.isPresent()) {
			ticket.setArchiver(false);
			ticket.setClientSoc(cs.get());
			ticket.setCloture(false);
			try {
				ticket.setDateDemandeTicket(dateFormatJour.parse(ticketForm.getDateDemandeTicket()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ticket.setDateProgrammer(dateFormatJour.parse(ticketForm.getDateProgrammer()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ticket.setDescSuscinte(ticketForm.getDescSuscinte());
			ticket.setEnAttente(true);
			
			try {
				ticket.setHeureDemande(dateFormatHeure.parse(ticketForm.getHeureDemande()));
				ticket.setHeureProgrammer(dateFormatHeure.parse(ticketForm.getHeureProgrammer()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			ticketRepository.save(ticket);
			return true;
		}		
		System.out.println("Le Client n'existe pas");
		return false;		
	}
	
}
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
class TicketForm{
	String dateDemandeTicket;
	String heureDemande;
	String dateProgrammer;
	String descSuscinte;
	String heureProgrammer;
	Long clientSoc;
}