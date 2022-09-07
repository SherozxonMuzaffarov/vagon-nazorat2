package com.sms.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.sms.dto.PlanDto;
import com.sms.dto.VagonDto;
import com.sms.model.VagonTayyor;
import com.sms.model.VagonTayyorUty;
import com.sms.model.VagonTayyor;


public interface VagonTayyorBiznesService {

	VagonTayyor saveVagon(VagonTayyor vagon);
	VagonTayyor saveVagonSam(VagonTayyor vagon);
	VagonTayyor saveVagonHav(VagonTayyor vagon);
	VagonTayyor saveVagonAndj(VagonTayyor vagon);
	
	String getSamDate();
	String getHavDate();
	String getAndjDate();

	VagonTayyor getVagonById(long id);
	
	VagonTayyor updateVagon(VagonTayyor vagon, long id);
	VagonTayyor updateVagonSam(VagonTayyor vagon, long id);
	VagonTayyor updateVagonHav(VagonTayyor vagon, long id);
	VagonTayyor updateVagonAndj(VagonTayyor vagon, long id);
	
	VagonTayyor updateVagonMonths(VagonTayyor vagon, long id);
	VagonTayyor updateVagonSamMonths(VagonTayyor vagon, long id);
	VagonTayyor updateVagonHavMonths(VagonTayyor vagon, long id);
	VagonTayyor updateVagonAndjMonths(VagonTayyor vagon, long id);
	
	void deleteVagonById(long id) throws NotFoundException;
	void deleteVagonSam(long id) throws NotFoundException;
	void deleteVagonHav(long id) throws NotFoundException;
	void deleteVagonAndj(long id) throws NotFoundException;
	
	List<VagonTayyor> findAllBoth();

	List<VagonTayyor> findAllActive();
	
	void savePlan(PlanDto planDto);
	
	PlanDto getPlanDto();
	
	int countByDepoNomiVagonTuriAndTamirTuri(String depoNomi, String vagonTuri, String tamirTuri);
	
	int countAllActiveByDepoNomiVagonTuriAndTamirTuri(String depoNomi, String vagnTuri, String tamirTuri, boolean b);
	
	VagonTayyor findByNomer(Integer nomer);
	
	VagonTayyor searchByNomer(Integer nomer);
	
	List<VagonTayyor> findAllByDepoNomiVagonTuriAndCountry(String depoNomi, String vagonTuri, String country);

	List<VagonTayyor> findAllByDepoNomiAndVagonTuri(String depoNomi, String vagonTuri);

	List<VagonTayyor> findAllByDepoNomiAndCountry(String depoNomi, String country);

	List<VagonTayyor> findAllByDepoNomi(String depoNomi);
	
	List<VagonTayyor> findAllByVagonTuriAndCountry(String vagonTuri, String country);

	List<VagonTayyor> findAllBycountry(String country);

	List<VagonTayyor> findAllByVagonTuri(String vagonTuri);
	/***/
	
	List<VagonTayyor> findByDepoNomiVagonTuriAndCountry(String depoNomi, String vagonTuri, String country);

	List<VagonTayyor> findByDepoNomiAndVagonTuri(String depoNomi, String vagonTuri);

	List<VagonTayyor> findByDepoNomiAndCountry(String depoNomi, String country);

	List<VagonTayyor> findByDepoNomi(String depoNomi);
	
	List<VagonTayyor> findByVagonTuriAndCountry(String vagonTuri, String country);

	List<VagonTayyor> findBycountry(String country);

	List<VagonTayyor> findByVagonTuri(String vagonTuri);
	
	void makePlanNul();
	
	PlanDto getPlanDtoAllMonth();
	
	VagonTayyor findById(Long id);
	
	void createPdf(List<VagonTayyor> vagonsToDownload, HttpServletResponse response) throws IOException;
	


}
