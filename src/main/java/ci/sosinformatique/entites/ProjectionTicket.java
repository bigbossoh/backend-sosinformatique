package ci.sosinformatique.entites;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;
@Projection(name = "p1",types = {Ticket.class})
public interface ProjectionTicket {
	Long getId();
	Date getDateDemandeTicket();
	Date getHeureDemande();
	Date getDateProgrammer();
	String getDescSuscinte();
	boolean getCloture();
	boolean getArchiver();
	boolean getEnAttente();
	Date getHeureProgrammer();
	ClientSociete  getClientSoc();
}
