package ci.sosinformatique.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import ci.sosinformatique.entites.ProjectionTicket;
import ci.sosinformatique.entites.Ticket;
@RepositoryRestResource
@CrossOrigin("*")
@Transactional
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	@RestResource(path = "/listTickets")
	public List<Ticket>findAll();
//	@RestResource(path = "/listTickets")
//	@Query("SELECT t.id,t.dateDemandeTicket,t.heureDemande,t.dateProgrammer, "
//			+ "t.descSuscinte,t.cloture,t.archiver,t.enAttente,"
//			+ "t.heureProgrammer,s.nomSoc FROM Ticket AS t, ClientSociete s "
//			+ "WHERE t.clientSoc=s.id")
////	@Query("SELECT t.id,t.date_demande_ticket,t.heure_demande,t.date_programmer, " + 
////			"			t.desc_suscinte,t.cloture,t.archiver,t.en_attente," + 
////			"			t.heure_programmer,s.nom_soc FROM client_societe AS s, ticket AS t " + 
////			"			WHERE t.client_soc_id=s.id order by t.date_demande_ticket desc, t.heure_demande desc")
//	public List<ProjectionTicket> getListTickets();

}
