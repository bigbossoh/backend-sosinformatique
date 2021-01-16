package ci.sosinformatique.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import ci.sosinformatique.entites.ClientSociete;
import ci.sosinformatique.entites.Diagnostique;
@RepositoryRestResource
@CrossOrigin("*")
public interface DiagnostiqueRepository extends Serializable, JpaRepository<Diagnostique, Long> {

}
