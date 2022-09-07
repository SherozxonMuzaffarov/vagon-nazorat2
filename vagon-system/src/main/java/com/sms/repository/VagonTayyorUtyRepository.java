package com.sms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sms.model.VagonTayyorUty;

@Repository
public interface VagonTayyorUtyRepository extends JpaRepository<VagonTayyorUty, Long> {
	
	Optional<VagonTayyorUty> findByNomer(Integer nomer);
	
	@Query("SELECT u FROM VagonTayyorUty u WHERE u.nomer = ?1")
	Optional<VagonTayyorUty> searchByNomer(Integer nomer);

//oyliklar uchun 
	@Query(value = "SELECT count(*) FROM vagon_tayyor_uty u WHERE u.depo_nomi = ?1 and u.vagon_turi = ?2 and u.remont_turi = ?3", nativeQuery = true)
	int countByDepoNomiVagonTuriAndTamirTuri(String depoNomi, String vagonTuri, String remontTuri);

	@Query(value = "SELECT count(*) FROM vagon_tayyor_uty u WHERE u.depo_nomi = ?1 and u.vagon_turi = ?2 and u.remont_turi = ?3 and u.is_active =?4", nativeQuery = true)
	int countAllActiveByDepoNomiVagonTuriAndTamirTuri(String depoNomi, String vagnTuri, String tamirTuri, boolean active);
//filterniki

	@Query("SELECT u FROM VagonTayyorUty u WHERE u.depoNomi = ?1 and u.vagonTuri = ?2 and u.isActive =?3")
	List<VagonTayyorUty> findAllByDepoNomiAndVagonTuri(String depoNomi, String vagonTuri, boolean isActive);

	@Query("SELECT u FROM VagonTayyorUty u WHERE u.depoNomi = ?1 and u.isActive =?2")
	List<VagonTayyorUty> findAllByDepoNomi(String depoNomi, boolean isActive);

	@Query("SELECT u FROM VagonTayyorUty u WHERE u.vagonTuri = ?1 and u.isActive =?2")
	List<VagonTayyorUty> findAllByVagonTuri(String vagonTuri, boolean isActive);

	//filterniki

	@Query("SELECT u FROM VagonTayyorUty u WHERE u.depoNomi = ?1 and u.vagonTuri = ?2")
	List<VagonTayyorUty> findByDepoNomiAndVagonTuri(String depoNomi, String vagonTuri);

	@Query("SELECT u FROM VagonTayyorUty u WHERE u.depoNomi = ?1")
	List<VagonTayyorUty> findByDepoNomi(String depoNomi);

	@Query("SELECT u FROM VagonTayyorUty u WHERE u.vagonTuri = ?1")
	List<VagonTayyorUty> findByVagonTuri(String vagonTuri);

}
