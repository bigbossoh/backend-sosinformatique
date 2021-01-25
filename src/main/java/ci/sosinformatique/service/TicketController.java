package ci.sosinformatique.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.jdbc.Driver;

import ci.sosinformatique.dao.ClientSocieteRepository;
import ci.sosinformatique.dao.TicketRepository;
import ci.sosinformatique.entites.ClientSociete;
import ci.sosinformatique.entites.Ticket;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private Environment env;
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
		Ticket ticket = new Ticket();
		Optional<ClientSociete> cs = clientSocieteRepository.findById(ticketForm.getClientSoc());
		if (cs.isPresent()) {
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
	@GetMapping("/etatListeticketets")
	@Transactional()
public void etatListeTicket() throws Exception{
	//ResourceBundle bundle=ResourceBundle.getBundle("application.properties");
	Driver monDriver = new com.mysql.cj.jdbc.Driver();
    DriverManager.registerDriver(monDriver);
    String url=env.getProperty("spring.datasource.url");//=bundle.getString("spring.datasource.url");
	String login=env.getProperty("spring.datasource.username");//bundle.getString("spring.datasource.username");
	String password=env.getProperty("spring.datasource.password");//bundle.getString("spring.datasource.password");
	try {
		Connection connection = DriverManager.getConnection(url, login, password);
		// - Chargement et compilation du rapport
		String templateFile = ResourceUtils.getFile("classpath:templates/etats/listeDesTicketsParEntreprise.jrxml").toString();
        JasperDesign jasperDesign = JRXmlLoader.load(templateFile);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        // - Paramètres à envoyer au rapport
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Titre", "Titre");

        // - Execution du rapport
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

        // - Création du rapport au format PDF
        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\etatstestjava\\etatTest.pdf");
	} catch (JRException e) {

        e.printStackTrace();
    } catch (SQLException e) {

        e.printStackTrace();
    }
	}
}

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
class TicketForm {
	String dateDemandeTicket;
	String heureDemande;
	String dateProgrammer;
	String descSuscinte;
	String heureProgrammer;
	Long clientSoc;
}