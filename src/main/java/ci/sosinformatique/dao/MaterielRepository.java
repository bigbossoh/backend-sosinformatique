package ci.sosinformatique.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import ci.sosinformatique.entites.ClientSociete;
import ci.sosinformatique.entites.Materiel;
@RepositoryRestResource
@CrossOrigin("*")
public interface MaterielRepository extends Serializable, JpaRepository<Materiel, Long> {

}
