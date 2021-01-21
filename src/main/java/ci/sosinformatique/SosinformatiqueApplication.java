package ci.sosinformatique;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import ci.sosinformatique.dao.ClientSocieteRepository;
import ci.sosinformatique.dao.DiagnosticMaterielRepository;
import ci.sosinformatique.dao.DiagnostiqueRepository;
import ci.sosinformatique.dao.EmployeRepository;
import ci.sosinformatique.dao.IntervenantRepository;
import ci.sosinformatique.dao.InterventionRepository;
import ci.sosinformatique.dao.MaterielRepository;
import ci.sosinformatique.dao.TicketRepository;
import ci.sosinformatique.entites.ClientSociete;
import ci.sosinformatique.entites.DiagnosticMateriel;
import ci.sosinformatique.entites.Diagnostique;
import ci.sosinformatique.entites.Employe;
import ci.sosinformatique.entites.Intervenant;
import ci.sosinformatique.entites.Intervention;
import ci.sosinformatique.entites.Materiel;
import ci.sosinformatique.entites.Ticket;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.bytebuddy.utility.RandomString;

@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootApplication
public class SosinformatiqueApplication implements CommandLineRunner {
	@Autowired
	ClientSocieteRepository clientSocieteRepository;

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	IntervenantRepository intervenantRepository;

	@Autowired
	MaterielRepository materielRepository;

	@Autowired
	EmployeRepository employeRepository;

	@Autowired
	DiagnostiqueRepository diagnostiqueRepository;

	@Autowired
	InterventionRepository interventionRepository;
int vIdItket=1;
	@Autowired
	DiagnosticMaterielRepository diagnosticMaterielRepository;
	@Autowired
	RepositoryRestConfiguration restConfiguration;
	DateFormat dateFormatHeure = new SimpleDateFormat("HH:mm");
	DateFormat dateFormatJour = new SimpleDateFormat("dd-MM-yyyy");

	Random rdm = new Random();

	public static void main(String[] args) {
		SpringApplication.run(SosinformatiqueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		restConfiguration.exposeIdsFor(Ticket.class,Employe.class,Intervenant.class);
		// CREATION DE CLIENTSOCIETE
		for (int i = 0; i < 5; i++) {

			ClientSociete clientSociete = new ClientSociete();
			clientSociete.setNomSoc(RandomString.make(6));
			clientSociete.setTelSoc(Integer.toString(rdm.nextInt(9)) + "100011122");
			clientSociete.setEmailSoc(RandomString.make(4) + "@gmail.com");
			clientSociete.setSousContratSoc(rdm.nextBoolean());
			clientSociete.setLieuSoc(RandomString.make(5));
			clientSociete.setItcketRecent(0);
			clientSocieteRepository.save(clientSociete);
		}
		// CREATION DE INTERVENANT
		for (int i = 0; i < 6; i++) {

			Intervenant intervenant = new Intervenant();
			intervenant.setNomIntervenante(RandomString.make(6));
			intervenant.setTelIntervenante("100011122" + Integer.toString(rdm.nextInt(9)));
			intervenant.setEmailIntervenante(RandomString.make(4) + "@gmail.com");
			intervenant.setPrenomIntervenante(RandomString.make(15));
			intervenantRepository.save(intervenant);
		}
		// CREATION DE EMPLOYE ET TICKET
		clientSocieteRepository.findAll().forEach(cs -> {
			// EMPLOYE

			for (int i = 0; i < 4; i++) {

				Employe employe = new Employe();
				employe.setClientSoc(cs);
				employe.setEmailEmploye(RandomString.make(6) + "@gmail.com");
				employe.setNomEmploye(RandomString.make(6));
				employe.setPrenomEmploye(RandomString.make(15));
				employe.setTelEmploye("100011122" + Integer.toString(rdm.nextInt(9)));
				employeRepository.save(employe);
			}
			// MATERIEL
			for (int i = 0; i < rdm.nextInt(5); i++) {
				Materiel materiel = new Materiel();
				materiel.setNomMateriel(RandomString.make(3 + rdm.nextInt(7)));
				materielRepository.save(materiel);
			}
			// TICKET
			for (int i = 0; i < (1 + (int) (Math.random() * 3)); i++) {

				Ticket ticket = new Ticket();
				ticket.setClientSoc(cs);
				ticket.setCloture(rdm.nextBoolean());
				ticket.setArchiver(rdm.nextBoolean());
				ticket.setEnAttente(rdm.nextBoolean());
				try {
					ticket.setDateDemandeTicket(dateFormatJour.parse((rdm.nextInt(31)+1)+"-"+(rdm.nextInt(11)+1)+"-"+"2020"));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				try {
					ticket.setDateProgrammer(dateFormatJour.parse("05-12-2021"));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				ticket.setDescSuscinte(RandomString.make(100));
				try {
					ticket.setHeureDemande(dateFormatHeure.parse("11:30"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				try {
					ticket.setHeureProgrammer(dateFormatHeure.parse("14:00"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				ticketRepository.save(ticket);
				vIdItket=vIdItket+cs.getItcketRecent();
//				System.out.println("Tickete "+vIdItket+"**** CS "+cs.getItcketRecent());
				cs.setItcketRecent(vIdItket);
				clientSocieteRepository.save(cs);
			}
		});
		// DIAGNOSTIQUE

		ticketRepository.findAll().forEach(t -> {
			for (int i = 0; i < 1 + (int) (Math.random() * 6); i++) {
				Diagnostique diag = new Diagnostique();
				try {
					diag.setDateDiagnostique(dateFormatJour.parse("03-01-2021"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					diag.setHeureDiagnostique(dateFormatHeure.parse(rdm.nextInt(22) + ":" + rdm.nextInt(59)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				diag.setObservation(RandomString.make(200));
				diag.setResumeIntervention(RandomString.make(rdm.nextInt(250)));
				diag.setResumerDiagnostique(RandomString.make(rdm.nextInt(200)));
				diag.setTicket(t);
				diagnostiqueRepository.save(diag);
			}
		});
		// INTERVENTION
		ticketRepository.findAll().forEach(t -> {
			for (int i = 1; i < 1 + (int) (Math.random() * 5); i++) {
				Intervention inter = new Intervention();
				try {
					inter.setDateIntervention(dateFormatJour.parse("02-01-2021"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inter.setDureeDiagnostique(dateFormatHeure.parse(rdm.nextInt(22) + ":" + rdm.nextInt(59)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inter.setDureeIntervention(dateFormatHeure.parse(rdm.nextInt(24) + ":" + rdm.nextInt(59)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inter.setHeureArrivee(dateFormatHeure.parse(rdm.nextInt(24) + ":" + rdm.nextInt(59)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					inter.setHeureDebut(dateFormatHeure.parse(rdm.nextInt(24) + ":" + rdm.nextInt(59)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Optional<Intervenant> itervants = intervenantRepository.findById((long) (rdm.nextInt(5)));
				if (itervants.isPresent()) {
					inter.setInterv(itervants.get());
				}
				inter.setTick(t);
				interventionRepository.save(inter);
			}
		});
		// DIAGNOSTIQUE MATERIEL
		materielRepository.findAll().forEach(m -> {
			for (int i = 0; i < (int) (1 + Math.random() * 5); i++) {
				DiagnosticMateriel diagnostiqueMateriel = new DiagnosticMateriel();
				diagnostiqueMateriel.setMat(m);
				Optional<Diagnostique> diagn = diagnostiqueRepository.findById((long) (2 + rdm.nextInt(2)));
				if (diagn.isPresent()) {
					diagnostiqueMateriel.setDiag(diagn.get());
				}
				diagnosticMaterielRepository.save(diagnostiqueMateriel);
			}
		});
	}

}
