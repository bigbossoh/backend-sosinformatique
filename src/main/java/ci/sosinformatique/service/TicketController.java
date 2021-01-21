package ci.sosinformatique.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

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
	@PutMapping("/updateTicket")
	@Transactional
	public boolean updateTicket(@RequestBody TicketForm ticketForm) {
		Ticket ticket = null;
		ClientSociete cs=null;
		try {
			ticket = ticketRepository.findById(ticketForm.getId()).orElseThrow(()->new Exception("Id ticket not found"));

			ticket.setArchiver(ticketForm.isArchiver());
			ticket.setCloture(ticketForm.isCloture());
			ticket.setHeureDemande(dateFormatHeure.parse(ticketForm.getHeureDemande()));
			ticket.setHeureProgrammer(dateFormatHeure.parse(ticketForm.getHeureProgrammer()));
			ticket.setDescSuscinte(ticketForm.getDescSuscinte());
			ticket.setDateDemandeTicket(dateFormatJour.parse(ticketForm.getDateDemandeTicket()));
			ticket.setDateProgrammer(dateFormatJour.parse(ticketForm.getDateProgrammer()));
			ticket.setEnAttente(ticketForm.isEnAttente());
			cs=clientSocieteRepository.findById(ticketForm.getClientSoc()).orElseThrow(()->new Exception("Error "));;
			ticket.setClientSoc(cs);
			ticketRepository.save(ticket);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return false;
	}
	@DeleteMapping("/deleteTicket/{id}")
	@Transactional
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long IdTicket)
			throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		Ticket ticket = ticketRepository.findById(IdTicket)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not found for this id :: " + IdTicket));
		if (ticket.isEnAttente()) {
			ticketRepository.delete(ticket);

			response.put("deleted", Boolean.TRUE);

		}
		else {response.put("is not in waiting state", Boolean.FALSE);

		}
		return response;
	}
}
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
class TicketForm{
	Long id;
	String dateDemandeTicket;
	String heureDemande;
	String dateProgrammer;
	String descSuscinte;
	String heureProgrammer;
	boolean cloture;
	boolean archiver;
	boolean enAttente;
	Long clientSoc;
}
