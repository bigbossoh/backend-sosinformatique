package ci.sosinformatique.entites;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AccessLevel;

import lombok.experimental.FieldDefaults;
@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientSociete implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String nomSoc;
	String telSoc;
	String emailSoc;
	int itcketRecent;
	boolean sousContratSoc;
	String lieuSoc;
	@OneToMany(mappedBy = "clientSoc")
	Collection<Ticket>tickets;
	@OneToMany(mappedBy = "clientSoc")
	Collection<Employe>employes;

}
