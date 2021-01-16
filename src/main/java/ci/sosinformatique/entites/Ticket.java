package ci.sosinformatique.entites;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Temporal(TemporalType.DATE)
	Date dateDemandeTicket;
	@Temporal(TemporalType.DATE)
	Date dateCreationTicket;
	@Temporal(TemporalType.TIME)
	Date heureDemande;
	@Temporal(TemporalType.DATE)
	Date dateProgrammer;
	String descSuscinte;
	boolean cloture;
	boolean archiver;
	boolean enAttente;
	@Temporal(TemporalType.TIME)
	Date heureProgrammer;
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	ClientSociete clientSoc;
	@OneToMany(mappedBy = "ticket")
	Collection<Diagnostique> diagnostique;
	@OneToMany(mappedBy = "tick")
	Collection<Intervention> interventions;

}
