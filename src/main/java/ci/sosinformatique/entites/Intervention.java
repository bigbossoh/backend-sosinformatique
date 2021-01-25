package ci.sosinformatique.entites;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class Intervention implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Temporal(TemporalType.DATE)
	Date dateIntervention;
	@Temporal(TemporalType.TIME)
	Date heureArrivee;
	@Temporal(TemporalType.TIME)
	Date heureDebut;
	@Temporal(TemporalType.TIME)
	Date dureeDiagnostique;
	@Temporal(TemporalType.TIME)
	Date dureeIntervention;
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	Intervenant interv;
	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	Ticket tick;
}
