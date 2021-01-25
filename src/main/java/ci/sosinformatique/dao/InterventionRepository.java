package ci.sosinformatique.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import ci.sosinformatique.entites.ClientSociete;
import ci.sosinformatique.entites.Intervention;
@RepositoryRestResource
@CrossOrigin("*")
public interface InterventionRepository extends Serializable, JpaRepository<Intervention, Long> {

}
