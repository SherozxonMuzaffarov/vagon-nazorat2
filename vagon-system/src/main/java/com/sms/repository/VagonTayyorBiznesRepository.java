package com.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sms.model.VagonTayyor;
import com.sms.model.VagonTayyor;

@Repository
public interface VagonTayyorBiznesRepository extends JpaRepository<VagonTayyor, Long> {
	
	Optional<VagonTayyor> findByNomer(Integer nomer);
	
	@Query("SELECT u FROM VagonTayyor u WHERE u.nomer = ?1")
	Optional<VagonTayyor> searchByNomer(Integer nomer);

//oyliklar uchun 
	@Query(value = "SELECT count(*) FROM vagon_tayyor u WHERE u.depo_nomi = ?1 and u.vagon_turi = ?2 and u.remont_turi = ?3", nativeQuery = true)
	int countByDepoNomiVagonTuriAndTamirTuri(String depoNomi, String vagonTuri, String remontTuri);

	@Query(value = "SELECT count(*) FROM vagon_tayyor u WHERE u.depo_nomi = ?1 and u.vagon_turi = ?2 and u.remont_turi = ?3 and u.is_active =?4", nativeQuery = true)
	int countAllActiveByDepoNomiVagonTuriAndTamirTuri(String depoNomi, String vagnTuri, String tamirTuri, boolean active);
//filterniki
	@Query("SELECT u FROM VagonTayyor u WHERE u.depoNomi = ?1 and u.vagonTuri = ?2 and u.country = ?3 and u.isActive =?4")
	List<VagonTayyor> findAllByDepoNomiVagonTuriAndCountry(String depoNomi, String vagonTuri, String country,boolean isActive);

	@Query("SELECT u FROM VagonTayyor u WHERE u.depoNomi = ?1 and u.vagonTuri = ?2 and u.isActive =?3")
	List<VagonTayyor> findAllByDepoNomiAndVagonTuri(String depoNomi, String vagonTuri, boolean isActive);

	@Query("SELECT u FROM VagonTayyor u WHERE u.depoNomi = ?1 and u.country = ?2 and u.isActive =?3")
	List<VagonTayyor> findAllByDepoNomiAndCountry(String depoNomi, String country, boolean isActive);

	@Query("SELECT u FROM VagonTayyor u WHERE u.depoNomi = ?1 and u.isActive =?2")
	List<VagonTayyor> findAllByDepoNomi(String depoNomi, boolean isActive);

	@Query("SELECT u FROM VagonTayyor u WHERE u.vagonTuri = ?1 and u.country = ?2 and u.isActive =?3")
	List<VagonTayyor> findAllByVagonTuriAndCountry(String vagonTuri, String country, boolean isActive);

	@Query("SELECT u FROM VagonTayyor u WHERE u.country = ?1 and u.isActive =?2")
	List<VagonTayyor> findAllBycountry(String country, boolean isActive);

	@Query("SELECT u FROM VagonTayyor u WHERE u.vagonTuri = ?1 and u.isActive =?2")
	List<VagonTayyor> findAllByVagonTuri(String vagonTuri, boolean isActive);

	//filterniki
	@Query("SELECT u FROM VagonTayyor u WHERE u.depoNomi = ?1 and u.vagonTuri = ?2 and u.country = ?3")
	List<VagonTayyor> findByDepoNomiVagonTuriAndCountry(String depoNomi, String vagonTuri, String country);

	@Query("SELECT u FROM VagonTayyor u WHERE u.depoNomi = ?1 and u.vagonTuri = ?2")
	List<VagonTayyor> findByDepoNomiAndVagonTuri(String depoNomi, String vagonTuri);

	@Query("SELECT u FROM VagonTayyor u WHERE u.depoNomi = ?1 and u.country = ?2")
	List<VagonTayyor> findByDepoNomiAndCountry(String depoNomi, String country);

	@Query("SELECT u FROM VagonTayyor u WHERE u.depoNomi = ?1")
	List<VagonTayyor> findByDepoNomi(String depoNomi);

	@Query("SELECT u FROM VagonTayyor u WHERE u.vagonTuri = ?1 and u.country = ?2")
	List<VagonTayyor> findByVagonTuriAndCountry(String vagonTuri, String country);

	@Query("SELECT u FROM VagonTayyor u WHERE u.country = ?1")
	List<VagonTayyor> findBycountry(String country);

	@Query("SELECT u FROM VagonTayyor u WHERE u.vagonTuri = ?1")
	List<VagonTayyor> findByVagonTuri(String vagonTuri);

}
