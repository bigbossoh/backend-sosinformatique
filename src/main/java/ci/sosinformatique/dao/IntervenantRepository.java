package ci.sosinformatique.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import ci.sosinformatique.entites.Intervenant;
@RepositoryRestResource
@CrossOrigin("*")
public interface IntervenantRepository extends Serializable, JpaRepository<Intervenant, Long> {
	/*
	 * @Query("Select count (*) from Intervenant") Long nbreIntervanat;
	 */
	
}
