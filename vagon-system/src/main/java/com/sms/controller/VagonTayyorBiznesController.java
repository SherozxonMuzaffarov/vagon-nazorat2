package com.sms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sms.dto.PlanDto;
import com.sms.model.VagonModel;
import com.sms.model.VagonTayyor;
import com.sms.service.VagonTayyorBiznesService;
import com.sms.serviceImp.VagonServiceImp;
import com.sms.serviceImp.VagonTayyorBiznesServiceImp;

@Controller
public class VagonTayyorBiznesController {
	
	@Autowired
	private VagonTayyorBiznesService vagonTayyorService;
	
	 //samarqand depo tamir plan
    int sdKriti ;
	int sdPlatforma;
	int sdPoluvagon;
	int sdSisterna;
	int sdBoshqa;
	int SamDtHammaPlan;
	
	//Hovos depo tamir plan
	int hdKriti;
	int hdPlatforma;
	int hdPoluvagon;
	int hdSisterna;
	int hdBoshqa;
	int HavDtHammaPlan;
	
	//VCHD-5 depo tamir plan
	int adKriti;
	int adPlatforma;
	int adPoluvagon;
	int adSisterna;
	int adBoshqa;
	int AndjDtHammaPlan;

	//Samarand kpital plan
	int skKriti;
	int skPlatforma;
	int skPoluvagon;
	int skSisterna;
	int skBoshqa;
	int SamKtHammaPlan;
	
	//VCHD-3 kpital plan
	int hkKriti;
	int hkPlatforma;
	int hkPoluvagon;
	int hkSisterna;
	int hkBoshqa;
	int HavKtHammaPlan;
	
	//andijon kapital plan
	int akKriti;
	int akPlatforma;
	int akPoluvagon;
	int akSisterna;
	int akBoshqa;
	int AndjKtHammaPlan;
	
	//VCHD-6 Krp plan	
	int skrKriti;
	int skrPlatforma;
	int skrPoluvagon;
	int skrSisterna;
	int skrBoshqa;
	int SamKrpHammaPlan;

	//Hovos Krp plan
	int hkrKriti;
	int hkrPlatforma;
	int hkrPoluvagon;
	int hkrSisterna;
	int hkrBoshqa;
	int HavKrpHammaPlan;
	
	//VCHD-5 Krp plan
	int akrKriti;
	int akrPlatforma;
	int akrPoluvagon;
	int akrSisterna;
	int akrBoshqa;
	int AndjKrpHammaPlan;
	
	@PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("")
	public String main(Model model) {
		return "main";
	}
	
	@PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("/vagons/rejalar")
	public String rejalar(Model model) {
		return "rejalar";
	}
	
	@PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("/vagons/tables")
	public String tables(Model model) {

		LocalDate today = LocalDate.now();
		int month = today.getMonthValue();
		String oy = null;
		switch (month) {
			case 1: 
				oy = "Yanvar";
				break;
			case 2: 
				oy = "Fevral";
				break;
			case 3: 
				oy = "Mart";
				break;
			case 4: 
				oy = "Aprel";
				break;
			case 5: 
				oy = "May";
				break;
			case 6: 
				oy = "Iyun";
				break;
			case 7: 
				oy = "Iyul";
				break;
			case 8: 
				oy = "Avgust";
				break;
			case 9: 
				oy = "Sentabr";
				break;
			case 10: 
				oy = "Oktabr";
				break;
			case 11: 
				oy = "Noyabr";
				break;
			case 12: 
				oy = "Dekabr";
				break;
			
		}
		
		model.addAttribute("month", oy);
		model.addAttribute("year", month + " oylik");
		return "tables";
	}
	
	//Yuklab olish uchun Malumot yigib beradi
	List<VagonTayyor> vagonsToDownload  = new ArrayList<>();
	
	@PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("/vagons/createExcelBiznes")
	public void pdfFile(Model model,HttpServletResponse response) throws IOException {
		vagonTayyorService.createPdf(vagonsToDownload,response);
		model.addAttribute("vagons",vagonsToDownload);

//    	PlanDto planDto = vagonTayyorService.getPlanDto();
//    	//planlar kiritish
//    	//samarqand depo tamir
//    	int SamDtHammaPlan=planDto.getSamDtKritiPlan() + planDto.getSamDtPlatformaPlan() + planDto.getSamDtPoluvagonPlan() + planDto.getSamDtSisternaPlan() + planDto.getSamDtBoshqaPlan();
//    	model.addAttribute("SamDtHammaPlan",SamDtHammaPlan);
//    	model.addAttribute("SamDtKritiPlan", planDto.getSamDtKritiPlan());
//    	model.addAttribute("SamDtPlatformaPlan", planDto.getSamDtPlatformaPlan());
//    	model.addAttribute("SamDtPoluvagonPlan", planDto.getSamDtPoluvagonPlan());
//    	model.addAttribute("SamDtSisternaPlan", planDto.getSamDtSisternaPlan());
//    	model.addAttribute("SamDtBoshqaPlan", planDto.getSamDtBoshqaPlan());
//
//    	//havos hamma plan
//    	int HavDtHammaPlan = planDto.getHavDtKritiPlan() + planDto.getHavDtPlatformaPlan() + planDto.getHavDtPoluvagonPlan() + planDto.getHavDtSisternaPlan() + planDto.getHavDtBoshqaPlan();
//    	model.addAttribute("HavDtHammaPlan", HavDtHammaPlan);
//    	model.addAttribute("HavDtKritiPlan", planDto.getHavDtKritiPlan());
//    	model.addAttribute("HavDtPlatformaPlan", planDto.getHavDtPlatformaPlan());
//    	model.addAttribute("HavDtPoluvagonPlan", planDto.getHavDtPoluvagonPlan());
//    	model.addAttribute("HavDtSisternaPlan", planDto.getHavDtSisternaPlan());
//    	model.addAttribute("HavDtBoshqaPlan", planDto.getHavDtBoshqaPlan());
//
//    	//andijon hamma plan depo tamir
//    	int AndjDtHammaPlan = planDto.getAndjDtKritiPlan() + planDto.getAndjDtPlatformaPlan() + planDto.getAndjDtPoluvagonPlan() + planDto.getAndjDtSisternaPlan() + planDto.getAndjDtBoshqaPlan();
//    	model.addAttribute("AndjDtHammaPlan", AndjDtHammaPlan);
//    	model.addAttribute("AndjDtKritiPlan", planDto.getAndjDtKritiPlan());
//    	model.addAttribute("AndjDtPlatformaPlan", planDto.getAndjDtPlatformaPlan());
//    	model.addAttribute("AndjDtPoluvagonPlan", planDto.getAndjDtPoluvagonPlan());
//    	model.addAttribute("AndjDtSisternaPlan", planDto.getAndjDtSisternaPlan());
//    	model.addAttribute("AndjDtBoshqaPlan", planDto.getAndjDtBoshqaPlan());
//
//    	// Itogo planlar depo tamir
//    	model.addAttribute("DtHammaPlan", AndjDtHammaPlan + HavDtHammaPlan + SamDtHammaPlan);
//    	model.addAttribute("DtKritiPlan", planDto.getAndjDtKritiPlan() + planDto.getHavDtKritiPlan() + planDto.getSamDtKritiPlan());
//    	model.addAttribute("DtPlatformaPlan", planDto.getAndjDtPlatformaPlan() + planDto.getHavDtPlatformaPlan() + planDto.getSamDtPlatformaPlan());
//    	model.addAttribute("DtPoluvagonPlan",planDto.getAndjDtPoluvagonPlan() + planDto.getHavDtPoluvagonPlan() + planDto.getSamDtPoluvagonPlan());
//    	model.addAttribute("DtSisternaPlan", planDto.getAndjDtSisternaPlan() + planDto.getHavDtSisternaPlan() + planDto.getSamDtSisternaPlan());
//    	model.addAttribute("DtBoshqaPlan", planDto.getAndjDtBoshqaPlan() + planDto.getHavDtBoshqaPlan() + planDto.getSamDtBoshqaPlan());
//
//    	//VCHD-6 kapital tamir uchun plan
//    	int SamKtHammaPlan =  planDto.getSamKtKritiPlan() + planDto.getSamKtPlatformaPlan() + planDto.getSamKtPoluvagonPlan() + planDto.getSamKtSisternaPlan() + planDto.getSamKtBoshqaPlan();
//    	model.addAttribute("SamKtHammaPlan",SamKtHammaPlan);
//    	model.addAttribute("SamKtKritiPlan", planDto.getSamKtKritiPlan());
//    	model.addAttribute("SamKtPlatformaPlan", planDto.getSamKtPlatformaPlan());
//    	model.addAttribute("SamKtPoluvagonPlan", planDto.getSamKtPoluvagonPlan());
//    	model.addAttribute("SamKtSisternaPlan", planDto.getSamKtSisternaPlan());
//    	model.addAttribute("SamKtBoshqaPlan", planDto.getSamKtBoshqaPlan());
//
//    	//havos kapital tamir uchun plan
//    	int HavKtHammaPlan = planDto.getHavKtKritiPlan() + planDto.getHavKtPlatformaPlan() + planDto.getHavKtPoluvagonPlan() + planDto.getHavKtSisternaPlan() + planDto.getHavKtBoshqaPlan();
//    	model.addAttribute("HavKtHammaPlan", HavKtHammaPlan);
//    	model.addAttribute("HavKtKritiPlan", planDto.getHavKtKritiPlan());
//    	model.addAttribute("HavKtPlatformaPlan", planDto.getHavKtPlatformaPlan());
//    	model.addAttribute("HavKtPoluvagonPlan", planDto.getHavKtPoluvagonPlan());
//    	model.addAttribute("HavKtSisternaPlan", planDto.getHavKtSisternaPlan());
//    	model.addAttribute("HavKtBoshqaPlan", planDto.getHavKtBoshqaPlan());
//
//    	//VCHD-5 kapital tamir uchun plan
//    	int AndjKtHammaPlan = planDto.getAndjKtKritiPlan() + planDto.getAndjKtPlatformaPlan() + planDto.getAndjKtPoluvagonPlan() + planDto.getAndjKtSisternaPlan() + planDto.getAndjKtBoshqaPlan();
//    	model.addAttribute("AndjKtHammaPlan", AndjKtHammaPlan);
//    	model.addAttribute("AndjKtKritiPlan", planDto.getAndjKtKritiPlan());
//    	model.addAttribute("AndjKtPlatformaPlan", planDto.getAndjKtPlatformaPlan());
//    	model.addAttribute("AndjKtPoluvagonPlan", planDto.getAndjKtPoluvagonPlan());
//    	model.addAttribute("AndjKtSisternaPlan", planDto.getAndjKtSisternaPlan());
//    	model.addAttribute("AndjKtBoshqaPlan", planDto.getAndjKtBoshqaPlan());
//
//    	//kapital itogo
//    	model.addAttribute("KtHammaPlan", AndjKtHammaPlan + HavKtHammaPlan + SamKtHammaPlan);
//    	model.addAttribute("KtKritiPlan", planDto.getAndjKtKritiPlan() + planDto.getHavKtKritiPlan() + planDto.getSamKtKritiPlan());
//    	model.addAttribute("KtPlatformaPlan", planDto.getAndjKtPlatformaPlan() + planDto.getHavKtPlatformaPlan() + planDto.getSamKtPlatformaPlan());
//    	model.addAttribute("KtPoluvagonPlan",planDto.getAndjKtPoluvagonPlan() + planDto.getHavKtPoluvagonPlan() + planDto.getSamKtPoluvagonPlan());
//    	model.addAttribute("KtSisternaPlan", planDto.getAndjKtSisternaPlan() + planDto.getHavKtSisternaPlan() + planDto.getSamKtSisternaPlan());
//    	model.addAttribute("KtBoshqaPlan", planDto.getAndjKtBoshqaPlan() + planDto.getHavKtBoshqaPlan() + planDto.getSamKtBoshqaPlan());
//
//    	//samarqand KRP plan
//    	int SamKrpHammaPlan = planDto.getSamKrpKritiPlan() + planDto.getSamKrpPlatformaPlan() + planDto.getSamKrpPoluvagonPlan() + planDto.getSamKrpSisternaPlan() + planDto.getSamKrpBoshqaPlan();
//    	model.addAttribute("SamKrpHammaPlan", SamKrpHammaPlan);
//    	model.addAttribute("SamKrpKritiPlan", planDto.getSamKrpKritiPlan());
//    	model.addAttribute("SamKrpPlatformaPlan", planDto.getSamKrpPlatformaPlan());
//    	model.addAttribute("SamKrpPoluvagonPlan", planDto.getSamKrpPoluvagonPlan());
//    	model.addAttribute("SamKrpSisternaPlan", planDto.getSamKrpSisternaPlan());
//    	model.addAttribute("SamKrpBoshqaPlan", planDto.getSamKrpBoshqaPlan());
//
//    	//VCHD-3 KRP plan
//    	int HavKrpHammaPlan =  planDto.getHavKrpKritiPlan() + planDto.getHavKrpPlatformaPlan() + planDto.getHavKrpPoluvagonPlan() + planDto.getHavKrpSisternaPlan() + planDto.getHavKrpBoshqaPlan();
//    	model.addAttribute("HavKrpHammaPlan",HavKrpHammaPlan);
//    	model.addAttribute("HavKrpKritiPlan", planDto.getHavKrpKritiPlan());
//    	model.addAttribute("HavKrpPlatformaPlan", planDto.getHavKrpPlatformaPlan());
//    	model.addAttribute("HavKrpPoluvagonPlan", planDto.getHavKrpPoluvagonPlan());
//    	model.addAttribute("HavKrpSisternaPlan", planDto.getHavKrpSisternaPlan());
//    	model.addAttribute("HavKrpBoshqaPlan", planDto.getHavKrpBoshqaPlan());
//
//    	//VCHD-5 Krp plan
//    	int AndjKrpHammaPlan =  planDto.getAndjKrpKritiPlan() + planDto.getAndjKrpPlatformaPlan() + planDto.getAndjKrpPoluvagonPlan() + planDto.getAndjKrpSisternaPlan() + planDto.getAndjKrpBoshqaPlan();
//    	model.addAttribute("AndjKrpHammaPlan",AndjKrpHammaPlan);
//    	model.addAttribute("AndjKrpKritiPlan", planDto.getAndjKrpKritiPlan());
//    	model.addAttribute("AndjKrpPlatformaPlan", planDto.getAndjKrpPlatformaPlan());
//    	model.addAttribute("AndjKrpPoluvagonPlan", planDto.getAndjKrpPoluvagonPlan());
//    	model.addAttribute("AndjKrpSisternaPlan", planDto.getAndjKrpSisternaPlan());
//    	model.addAttribute("AndjKrpBoshqaPlan", planDto.getAndjKrpBoshqaPlan());
//
//    	//Krp itogo plan
//    	model.addAttribute("KrpHammaPlan", AndjKrpHammaPlan + HavKrpHammaPlan + SamKrpHammaPlan);
//    	model.addAttribute("KrpKritiPlan", planDto.getAndjKrpKritiPlan() + planDto.getHavKrpKritiPlan() + planDto.getSamKrpKritiPlan());
//    	model.addAttribute("KrpPlatformaPlan", planDto.getAndjKrpPlatformaPlan() + planDto.getHavKrpPlatformaPlan() + planDto.getSamKrpPlatformaPlan());
//    	model.addAttribute("KrpPoluvagonPlan",planDto.getAndjKrpPoluvagonPlan() + planDto.getHavKrpPoluvagonPlan() + planDto.getSamKrpPoluvagonPlan());
//    	model.addAttribute("KrpSisternaPlan", planDto.getAndjKrpSisternaPlan() + planDto.getHavKrpSisternaPlan() + planDto.getSamKrpSisternaPlan());
//    	model.addAttribute("KrpBoshqaPlan", planDto.getAndjKrpBoshqaPlan() + planDto.getHavKrpBoshqaPlan() + planDto.getSamKrpBoshqaPlan());
//
// // factlar
//    	//samarqand uchun depli tamir
//    	int sdHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
//    	model.addAttribute("sdHamma",sdHamma);
//    	model.addAttribute("sdKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("sdPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("sdPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("sdSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("sdBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
//
//    	//VCHD-3 uchun depli tamir
//    	int hdHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
//    	model.addAttribute("hdHamma",hdHamma);
//    	model.addAttribute("hdKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("hdPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("hdPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("hdSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("hdBoshqaPlan", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
//
//    	//VCHD-5 uchun depli tamir
//    	int adHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) +
//    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)", true);
//    	model.addAttribute("adHamma",adHamma);
//    	model.addAttribute("adKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("adPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("adPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("adSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)", true));
//    	model.addAttribute("adBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
//
//    	// itogo Fact uchun depli tamir
//    	 int factdhamma = sdHamma + hdHamma + adHamma;
//    	 model.addAttribute("factdhamma",factdhamma);
//    	 int boshqaPlan = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) +
//    			 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) +
//    			 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
//    	 model.addAttribute("boshqaPlan",boshqaPlan);
//
//     	//samarqand uchun Kapital tamir
//     	int skHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) +
//		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)", true) +
//		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) +
//		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)", true) +
//		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
//     	model.addAttribute("skHamma",skHamma);
//     	model.addAttribute("skKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("skPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("skPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("skSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("skBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
//
//     	//VCHD-3 uchun kapital tamir
//     	int hkHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) +
//     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)", true) +
//     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) +
//     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)", true) +
//     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
//     	model.addAttribute("hkHamma",hkHamma);
//     	model.addAttribute("hkKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("hkPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("hkPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("hkSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("hkBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
//
//     	//VCHD-5 uchun kapital tamir
//     	int akHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) +
//					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)", true) +
//					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) +
//					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)", true) +
//					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
//     	model.addAttribute("akHamma",akHamma);
//     	model.addAttribute("akKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("akPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("akPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("akSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)", true));
//     	model.addAttribute("akBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
//
//	     // itogo Fact uchun kapital tamir
//	   	 int factkhamma = skHamma + hkHamma + akHamma;
//	   	 model.addAttribute("factkhamma",factkhamma);
//	   	 int boshqaKPlan = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true) +
//		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true) +
//		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
//	   	 			model.addAttribute("boshqaKPlan",boshqaKPlan);
//
//
//      	//samarqand uchun KRP tamir
//      	int skrHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true);
//      	model.addAttribute("skrHamma",skrHamma);
//      	model.addAttribute("skrKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)", true));
//      	model.addAttribute("skrPlatforma",vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)", true));
//      	model.addAttribute("skrPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)", true));
//      	model.addAttribute("skrSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)", true));
//      	model.addAttribute("skrBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true));
//
//      	//VCHD-3 uchun KRP
//      	int hkrHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true);
//      	model.addAttribute("hkrHamma",hkrHamma);
//      	model.addAttribute("hkrKriti",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)", true));
//      	model.addAttribute("hkrPlatforma",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)", true));
//      	model.addAttribute("hkrPoluvagon",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)", true));
//      	model.addAttribute("hkrSisterna",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)", true));
//      	model.addAttribute("hkrBoshqa",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true));
//
//      	//VCHD-5 uchun KRP
//      	int akrHamma =  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)", true) +
//				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true);
//      	model.addAttribute("akrHamma",akrHamma);
//      	model.addAttribute("akrKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)", true));
//      	model.addAttribute("akrPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)", true));
//      	model.addAttribute("akrPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)", true));
//      	model.addAttribute("akrSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)", true));
//      	model.addAttribute("akrBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true));
//
// 	     // itogo Fact uchun KRP
// 	   	 int factkrhamma = skrHamma + hkrHamma + akrHamma;
// 	   	 model.addAttribute("factkrhamma",factkrhamma);
// 	   	 int boshqaKr = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true) +
//		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true) +
//		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true);
//	   	 model.addAttribute("boshqaKr",boshqaKr);
//
//		return "AllPlanTable";
	 }
	
	@PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("/vagons/createExcelBiznesAllMonth")
	public void createPdfFile(Model model,HttpServletResponse response) throws IOException {
		
		vagonTayyorService.createPdf(vagonsToDownload,response);
		model.addAttribute("vagons",vagonsToDownload);
		
//		PlanDto planDto = vagonTayyorService.getPlanDtoAllMonth();
//    	//planlar kiritish
//
//    	//samarqand depo tamir plan
//    	sdKriti += planDto.getSamDtKritiPlan();
//    	sdPlatforma+=planDto.getSamDtPlatformaPlan();
//    	sdPoluvagon+=planDto.getSamDtPoluvagonPlan();
//    	sdSisterna+=planDto.getSamDtSisternaPlan();
//    	sdBoshqa+=planDto.getSamDtBoshqaPlan();
//    	SamDtHammaPlan=sdKriti+sdPlatforma+sdPoluvagon+sdSisterna+sdBoshqa;
//
//    	model.addAttribute("SamDtHammaPlan",SamDtHammaPlan);
//    	model.addAttribute("SamDtKritiPlan", sdKriti);
//    	model.addAttribute("SamDtPlatformaPlan", sdPlatforma);
//    	model.addAttribute("SamDtPoluvagonPlan", sdPoluvagon);
//    	model.addAttribute("SamDtSisternaPlan", sdSisterna);
//    	model.addAttribute("SamDtBoshqaPlan", sdBoshqa);
//
//    	//havos depo tamir hamma plan
//    	hdKriti += planDto.getHavDtKritiPlan();
//    	hdPlatforma+=planDto.getHavDtPlatformaPlan();
//    	hdPoluvagon+=planDto.getHavDtPoluvagonPlan();
//    	hdSisterna+=planDto.getHavDtSisternaPlan();
//    	hdBoshqa+=planDto.getHavDtBoshqaPlan();
//    	HavDtHammaPlan = hdKriti + hdPlatforma + hdPoluvagon + hdSisterna + hdBoshqa;
//
//    	model.addAttribute("HavDtHammaPlan", HavDtHammaPlan);
//    	model.addAttribute("HavDtKritiPlan", hdKriti);
//    	model.addAttribute("HavDtPlatformaPlan", hdPlatforma);
//    	model.addAttribute("HavDtPoluvagonPlan", hdPoluvagon);
//    	model.addAttribute("HavDtSisternaPlan", hdSisterna);
//    	model.addAttribute("HavDtBoshqaPlan", hdBoshqa);
//
//    	//VCHD-5 depo tamir plan
//    	adKriti += planDto.getAndjDtKritiPlan();
//    	adPlatforma+=planDto.getAndjDtPlatformaPlan();
//    	adPoluvagon+=planDto.getAndjDtPoluvagonPlan();
//    	adSisterna+=planDto.getAndjDtSisternaPlan();
//    	adBoshqa+=planDto.getAndjDtBoshqaPlan();
//    	AndjDtHammaPlan = adKriti + adPlatforma + adPoluvagon + adSisterna + adBoshqa;
//
//    	model.addAttribute("AndjDtHammaPlan", AndjDtHammaPlan);
//    	model.addAttribute("AndjDtKritiPlan", adKriti);
//    	model.addAttribute("AndjDtPlatformaPlan",adPlatforma);
//    	model.addAttribute("AndjDtPoluvagonPlan", adPoluvagon);
//    	model.addAttribute("AndjDtSisternaPlan", adSisterna);
//    	model.addAttribute("AndjDtBoshqaPlan", adBoshqa);
//
//    	// Itogo planlar depo tamir
//    	model.addAttribute("DtHammaPlan", AndjDtHammaPlan + HavDtHammaPlan + SamDtHammaPlan);
//    	model.addAttribute("DtKritiPlan", sdKriti + hdKriti + adKriti);
//    	model.addAttribute("DtPlatformaPlan", sdPlatforma + hdPlatforma + adPlatforma);
//    	model.addAttribute("DtPoluvagonPlan",sdPoluvagon + hdPoluvagon + adPoluvagon);
//    	model.addAttribute("DtSisternaPlan", sdSisterna + hdSisterna + adSisterna);
//    	model.addAttribute("DtBoshqaPlan", sdBoshqa + hdBoshqa + adBoshqa);
//
//    	//Samrqand kapital plan
//    	skKriti += planDto.getSamKtKritiPlan();
//    	skPlatforma+=planDto.getSamKtPlatformaPlan();
//    	skPoluvagon+=planDto.getSamKtPoluvagonPlan();
//    	skSisterna+=planDto.getSamKtSisternaPlan();
//    	skBoshqa+=planDto.getSamKtBoshqaPlan();
//    	SamKtHammaPlan=skKriti+skPlatforma+skPoluvagon+skSisterna+skBoshqa;
//
//    	model.addAttribute("SamKtHammaPlan",SamKtHammaPlan);
//    	model.addAttribute("SamKtKritiPlan", skKriti);
//    	model.addAttribute("SamKtPlatformaPlan", skPlatforma);
//    	model.addAttribute("SamKtPoluvagonPlan", skPoluvagon);
//    	model.addAttribute("SamKtSisternaPlan", skSisterna);
//    	model.addAttribute("SamKtBoshqaPlan", skBoshqa);
//
//    	//hovos kapital plan
//    	hkKriti += planDto.getHavKtKritiPlan();
//    	hkPlatforma+=planDto.getHavKtPlatformaPlan();
//    	hkPoluvagon+=planDto.getHavKtPoluvagonPlan();
//    	hkSisterna+=planDto.getHavKtSisternaPlan();
//    	hkBoshqa+=planDto.getHavKtBoshqaPlan();
//    	HavKtHammaPlan = hkKriti + hkPlatforma + hkPoluvagon + hkSisterna + hkBoshqa;
//
//    	model.addAttribute("HavKtHammaPlan", HavKtHammaPlan);
//    	model.addAttribute("HavKtKritiPlan", hkKriti);
//    	model.addAttribute("HavKtPlatformaPlan", hkPlatforma);
//    	model.addAttribute("HavKtPoluvagonPlan", hkPoluvagon);
//    	model.addAttribute("HavKtSisternaPlan", hkSisterna);
//    	model.addAttribute("HavKtBoshqaPlan", hkBoshqa);
//
//    	//ANDIJON kapital plan
//    	akKriti += planDto.getAndjKtKritiPlan();
//    	akPlatforma+=planDto.getAndjKtPlatformaPlan();
//    	akPoluvagon+=planDto.getAndjKtPoluvagonPlan();
//    	akSisterna+=planDto.getAndjKtSisternaPlan();
//    	akBoshqa+=planDto.getAndjKtBoshqaPlan();
//    	AndjKtHammaPlan = akKriti + akPlatforma + akPoluvagon + akSisterna + akBoshqa;
//
//
//    	model.addAttribute("AndjKtHammaPlan", AndjKtHammaPlan);
//    	model.addAttribute("AndjKtKritiPlan", akKriti);
//    	model.addAttribute("AndjKtPlatformaPlan", akPlatforma);
//    	model.addAttribute("AndjKtPoluvagonPlan", akPoluvagon);
//    	model.addAttribute("AndjKtSisternaPlan", akSisterna);
//    	model.addAttribute("AndjKtBoshqaPlan", akBoshqa);
//
//    	//Itogo kapital plan
//    	model.addAttribute("KtHammaPlan", AndjKtHammaPlan + HavKtHammaPlan + SamKtHammaPlan);
//    	model.addAttribute("KtKritiPlan", skKriti + hkKriti + akKriti);
//    	model.addAttribute("KtPlatformaPlan", skPlatforma + hkPlatforma + akPlatforma);
//    	model.addAttribute("KtPoluvagonPlan",skPoluvagon + hkPoluvagon + akPoluvagon);
//    	model.addAttribute("KtSisternaPlan", skSisterna + hkSisterna + akSisterna);
//    	model.addAttribute("KtBoshqaPlan", skBoshqa + hkBoshqa + akBoshqa);
//
//    	//Samarqankr Krp plan
//    	skrKriti += planDto.getSamKrpKritiPlan();
//    	skrPlatforma+=planDto.getSamKrpPlatformaPlan();
//    	skrPoluvagon+=planDto.getSamKrpPoluvagonPlan();
//    	skrSisterna+=planDto.getSamKrpSisternaPlan();
//    	skrBoshqa+=planDto.getSamKrpBoshqaPlan();
//    	SamKrpHammaPlan=skrKriti+skrPlatforma+skrPoluvagon+skrSisterna+skrBoshqa;
//
//    	model.addAttribute("SamKrpHammaPlan", SamKrpHammaPlan);
//    	model.addAttribute("SamKrpKritiPlan", skrKriti);
//    	model.addAttribute("SamKrpPlatformaPlan", skrPlatforma);
//    	model.addAttribute("SamKrpPoluvagonPlan", skrPoluvagon);
//    	model.addAttribute("SamKrpSisternaPlan", skrSisterna);
//    	model.addAttribute("SamKrpBoshqaPlan", skrBoshqa);
//
//    	//Hovos krp plan
//    	hkrKriti += planDto.getHavKrpKritiPlan();
//    	hkrPlatforma+=planDto.getHavKrpPlatformaPlan();
//    	hkrPoluvagon+=planDto.getHavKrpPoluvagonPlan();
//    	hkrSisterna+=planDto.getHavKrpSisternaPlan();
//    	hkrBoshqa+=planDto.getHavKrpBoshqaPlan();
//    	HavKrpHammaPlan = hkrKriti + hkrPlatforma + hkrPoluvagon + hkrSisterna + hkrBoshqa;
//
//    	model.addAttribute("HavKrpHammaPlan",HavKrpHammaPlan);
//    	model.addAttribute("HavKrpKritiPlan", hkrKriti);
//    	model.addAttribute("HavKrpPlatformaPlan", hkrPlatforma);
//    	model.addAttribute("HavKrpPoluvagonPlan", hkrPoluvagon);
//    	model.addAttribute("HavKrpSisternaPlan", hkrSisterna);
//    	model.addAttribute("HavKrpBoshqaPlan", hkrBoshqa);
//
//    	//andijon krp plan
//    	akrKriti += planDto.getAndjKrpKritiPlan();
//    	akrPlatforma+=planDto.getAndjKrpPlatformaPlan();
//    	akrPoluvagon+=planDto.getAndjKrpPoluvagonPlan();
//    	akrSisterna+=planDto.getAndjKrpSisternaPlan();
//    	akrBoshqa+=planDto.getAndjKrpBoshqaPlan();
//    	AndjKrpHammaPlan = akrKriti + akrPlatforma + akrPoluvagon + akrSisterna + akrBoshqa;
//
//    	model.addAttribute("AndjKrpHammaPlan",AndjKrpHammaPlan);
//    	model.addAttribute("AndjKrpKritiPlan", akrKriti);
//    	model.addAttribute("AndjKrpPlatformaPlan", akrPlatforma);
//    	model.addAttribute("AndjKrpPoluvagonPlan", akrPoluvagon);
//    	model.addAttribute("AndjKrpSisternaPlan", akrSisterna);
//    	model.addAttribute("AndjKrpBoshqaPlan", akrBoshqa);
//
//    	//itogo krp
//    	model.addAttribute("KrpHammaPlan", AndjKrpHammaPlan + HavKrpHammaPlan + SamKrpHammaPlan);
//    	model.addAttribute("KrpKritiPlan", skrKriti + hkrKriti + akrKriti);
//    	model.addAttribute("KrpPlatformaPlan", skrPlatforma + hkrPlatforma + akrPlatforma);
//    	model.addAttribute("KrpPoluvagonPlan",akrPoluvagon + hkrPoluvagon + skrPoluvagon);
//    	model.addAttribute("KrpSisternaPlan", skrSisterna + hkrSisterna + akrSisterna);
//    	model.addAttribute("KrpBoshqaPlan", skrBoshqa + hkrBoshqa + akrBoshqa);
//
//    	vagonTayyorService.makePlanNul();
//    	//**//
//    	// samarqand depo tamir hamma false vagonlar soni
//    	int sdKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
//    	int sdPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)");
//    	int sdPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
//    	int sdSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)");
//    	int sdBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
//    	int sdHammaFalse = sdKritiFalse + sdPlatformaFalse+ sdPoluvagonFalse+ sdSisternaFalse + sdBoshqaFalse;
//
//    	model.addAttribute("sdHammaFalse",sdHammaFalse);
//    	model.addAttribute("sdKritiFalse",sdKritiFalse);
//    	model.addAttribute("sdPlatformaFalse",sdPlatformaFalse);
//    	model.addAttribute("sdPoluvagonFalse",sdPoluvagonFalse);
//    	model.addAttribute("sdSisternaFalse",sdSisternaFalse);
//    	model.addAttribute("sdBoshqaFalse",sdBoshqaFalse);
//
//    	// VCHD-3 depo tamir hamma false vagonlar soni
//    	int hdKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
//    	int hdPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)");
//    	int hdPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
//    	int hdSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)");
//    	int hdBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
//    	int hdHammaFalse = hdKritiFalse + hdPlatformaFalse+ hdPoluvagonFalse+ hdSisternaFalse + hdBoshqaFalse;
//
//    	model.addAttribute("hdHammaFalse",hdHammaFalse);
//    	model.addAttribute("hdKritiFalse",hdKritiFalse);
//    	model.addAttribute("hdPlatformaFalse",hdPlatformaFalse);
//    	model.addAttribute("hdPoluvagonFalse",hdPoluvagonFalse);
//    	model.addAttribute("hdSisternaFalse",hdSisternaFalse);
//    	model.addAttribute("hdBoshqaFalse",hdBoshqaFalse);
//
//    	// VCHD-5 depo tamir hamma false vagonlar soni
//    	int adKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
//    	int adPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)");
//    	int adPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
//    	int adSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)");
//    	int adBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
//    	int adHammaFalse = adKritiFalse + adPlatformaFalse+ adPoluvagonFalse+ adSisternaFalse + adBoshqaFalse;
//
//    	model.addAttribute("adHammaFalse",adHammaFalse);
//    	model.addAttribute("adKritiFalse",adKritiFalse);
//    	model.addAttribute("adPlatformaFalse",adPlatformaFalse);
//    	model.addAttribute("adPoluvagonFalse",adPoluvagonFalse);
//    	model.addAttribute("adSisternaFalse",adSisternaFalse);
//    	model.addAttribute("adBoshqaFalse",adBoshqaFalse);
//
//    	// depoli tamir itogo uchun
//    	int dHammaFalse =  adHammaFalse + hdHammaFalse+sdHammaFalse;
//    	int dKritiFalse = sdKritiFalse + hdKritiFalse + adKritiFalse;
//    	int dPlatforma = adPlatformaFalse + sdPlatformaFalse + hdPlatformaFalse;
//    	int dPoluvagon  = adPoluvagonFalse + sdPoluvagonFalse + hdPoluvagonFalse;
//    	int dSisterna = adSisternaFalse + hdSisternaFalse + sdSisternaFalse;
//    	int dBoshqa = adBoshqaFalse + hdBoshqaFalse + sdBoshqaFalse;
//
//    	model.addAttribute("dHammaFalse",dHammaFalse);
//    	model.addAttribute("dKritiFalse",dKritiFalse);
//    	model.addAttribute("dPlatforma",dPlatforma);
//    	model.addAttribute("dPoluvagon",dPoluvagon);
//    	model.addAttribute("dSisterna",dSisterna);
//    	model.addAttribute("dBoshqa",dBoshqa);
//
//
//    	// samarqand KApital tamir hamma false vagonlar soni
//    	int skKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
//    	int skPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)");
//    	int skPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
//    	int skSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)");
//    	int skBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
//    	int skHammaFalse = skKritiFalse + skPlatformaFalse+ skPoluvagonFalse+ skSisternaFalse + skBoshqaFalse;
//
//    	model.addAttribute("skHammaFalse",skHammaFalse);
//    	model.addAttribute("skKritiFalse",skKritiFalse);
//    	model.addAttribute("skPlatformaFalse",skPlatformaFalse);
//    	model.addAttribute("skPoluvagonFalse",skPoluvagonFalse);
//    	model.addAttribute("skSisternaFalse",skSisternaFalse);
//    	model.addAttribute("skBoshqaFalse",skBoshqaFalse);
//
//    	// VCHD-3 kapital tamir hamma false vagonlar soni
//    	int hkKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
//    	int hkPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)");
//    	int hkPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
//    	int hkSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)");
//    	int hkBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
//    	int hkHammaFalse = hkKritiFalse + hkPlatformaFalse+ hkPoluvagonFalse+ hkSisternaFalse + hkBoshqaFalse;
//
//    	model.addAttribute("hkHammaFalse",hkHammaFalse);
//    	model.addAttribute("hkKritiFalse",hkKritiFalse);
//    	model.addAttribute("hkPlatformaFalse",hkPlatformaFalse);
//    	model.addAttribute("hkPoluvagonFalse",hkPoluvagonFalse);
//    	model.addAttribute("hkSisternaFalse",hkSisternaFalse);
//    	model.addAttribute("hkBoshqaFalse",hkBoshqaFalse);
//
//    	// VCHD-5 kapital tamir hamma false vagonlar soni
//    	int akKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
//    	int akPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)");
//    	int akPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
//    	int akSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)");
//    	int akBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
//    	int akHammaFalse = akKritiFalse + akPlatformaFalse+ akPoluvagonFalse+ akSisternaFalse + akBoshqaFalse;
//
//    	model.addAttribute("akHammaFalse",akHammaFalse);
//    	model.addAttribute("akKritiFalse",akKritiFalse);
//    	model.addAttribute("akPlatformaFalse",akPlatformaFalse);
//    	model.addAttribute("akPoluvagonFalse",akPoluvagonFalse);
//    	model.addAttribute("akSisternaFalse",akSisternaFalse);
//    	model.addAttribute("akBoshqaFalse",akBoshqaFalse);
//
//    	// Kapital tamir itogo uchun
//    	int kHammaFalse =  akHammaFalse + hkHammaFalse+skHammaFalse;
//    	int kKritiFalse = skKritiFalse + hkKritiFalse + akKritiFalse;
//    	int kPlatforma = akPlatformaFalse + skPlatformaFalse + hkPlatformaFalse;
//    	int kPoluvagon  = akPoluvagonFalse + skPoluvagonFalse + hkPoluvagonFalse;
//    	int kSisterna = akSisternaFalse + hkSisternaFalse + skSisternaFalse;
//    	int kBoshqa = akBoshqaFalse + hkBoshqaFalse + skBoshqaFalse;
//
//    	model.addAttribute("kHammaFalse",kHammaFalse);
//    	model.addAttribute("kKritiFalse",kKritiFalse);
//    	model.addAttribute("kPlatforma",kPlatforma);
//    	model.addAttribute("kPoluvagon",kPoluvagon);
//    	model.addAttribute("kSisterna",kSisterna);
//    	model.addAttribute("kBoshqa",kBoshqa);
//
//    	//**
//    	// samarqand KRP tamir hamma false vagonlar soni
//    	int skrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)");
//    	int skrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)");
//    	int skrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)");
//    	int skrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)");
//    	int skrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)");
//    	int skrHammaFalse = skrKritiFalse + skrPlatformaFalse+ skrPoluvagonFalse+ skrSisternaFalse + skrBoshqaFalse;
//
//    	model.addAttribute("skrHammaFalse",skrHammaFalse);
//    	model.addAttribute("skrKritiFalse",skrKritiFalse);
//    	model.addAttribute("skrPlatformaFalse",skrPlatformaFalse);
//    	model.addAttribute("skrPoluvagonFalse",skrPoluvagonFalse);
//    	model.addAttribute("skrSisternaFalse",skrSisternaFalse);
//    	model.addAttribute("skrBoshqaFalse",skrBoshqaFalse);
//
//    	// VCHD-3 KRP tamir hamma false vagonlar soni
//    	int hkrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)");
//    	int hkrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)");
//    	int hkrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)");
//    	int hkrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)");
//    	int hkrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)");
//    	int hkrHammaFalse = hkrKritiFalse + hkrPlatformaFalse+ hkrPoluvagonFalse+ hkrSisternaFalse + hkrBoshqaFalse;
//
//    	model.addAttribute("hkrHammaFalse",hkrHammaFalse);
//    	model.addAttribute("hkrKritiFalse",hkrKritiFalse);
//    	model.addAttribute("hkrPlatformaFalse",hkrPlatformaFalse);
//    	model.addAttribute("hkrPoluvagonFalse",hkrPoluvagonFalse);
//    	model.addAttribute("hkrSisternaFalse",hkrSisternaFalse);
//    	model.addAttribute("hkrBoshqaFalse",hkrBoshqaFalse);
//
//    	// VCHD-5 KRP tamir hamma false vagonlar soni
//    	int akrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)");
//    	int akrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)");
//    	int akrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)");
//    	int akrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)");
//    	int akrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)");
//    	int akrHammaFalse = akrKritiFalse + akrPlatformaFalse+ akrPoluvagonFalse+ akrSisternaFalse + akrBoshqaFalse;
//
//    	model.addAttribute("akrHammaFalse",akrHammaFalse);
//    	model.addAttribute("akrKritiFalse",akrKritiFalse);
//    	model.addAttribute("akrPlatformaFalse",akrPlatformaFalse);
//    	model.addAttribute("akrPoluvagonFalse",akrPoluvagonFalse);
//    	model.addAttribute("akrSisternaFalse",akrSisternaFalse);
//    	model.addAttribute("akBoshqaFalse",akBoshqaFalse);
//
//    	// Krp itogo uchun
//    	int krHammaFalse =  akrHammaFalse + hkrHammaFalse+skrHammaFalse;
//    	int krKritiFalse = skrKritiFalse + hkrKritiFalse + akrKritiFalse;
//    	int krPlatforma = akrPlatformaFalse + skrPlatformaFalse + hkrPlatformaFalse;
//    	int krPoluvagon  = akrPoluvagonFalse + skrPoluvagonFalse + hkrPoluvagonFalse;
//    	int krSisterna = akrSisternaFalse + hkrSisternaFalse + skrSisternaFalse;
//    	int krBoshqa = akrBoshqaFalse + hkrBoshqaFalse + skrBoshqaFalse;
//
//    	model.addAttribute("krHammaFalse",krHammaFalse);
//    	model.addAttribute("krKritiFalse",krKritiFalse);
//    	model.addAttribute("krPlatforma",krPlatforma);
//    	model.addAttribute("krPoluvagon",krPoluvagon);
//    	model.addAttribute("krSisterna",krSisterna);
//    	model.addAttribute("krBoshqa",krBoshqa);
//
//		return "planTableForMonths";
	 }
	
    //Tayyor yangi vagon qoshish uchun oyna
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("/vagons/newTayyor")
	public String createVagonForm(Model model) {
		VagonTayyor vagonTayyor = new VagonTayyor();
		model.addAttribute("vagon", vagonTayyor);
		return "create_tayyorvagon";
	}
    
    //TAyyor vagon qoshish
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@PostMapping("/vagons/saveTayyor")
	public String saveVagon(@ModelAttribute("vagon") VagonTayyor vagon, HttpServletRequest request ) { 
    	if (request.isUserInRole("DIRECTOR")) {
    		vagonTayyorService.saveVagon(vagon);
        }else if (request.isUserInRole("SAM")) {
        	vagonTayyorService.saveVagonSam(vagon);
        }else if (request.isUserInRole("HAV")) {
        	vagonTayyorService.saveVagonHav(vagon);
        }else if (request.isUserInRole("ANDJ")) {
        	vagonTayyorService.saveVagonAndj(vagon);
        }
		return "redirect:/vagons/AllPlanTable";
	}
    
    //tahrirlash uchun oyna
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("/vagons/editTayyor/{id}")
	public String editVagonForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("vagon",vagonTayyorService.getVagonById(id));
		return "edit_tayyorvagon";
	}

    //tahrirni saqlash
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@PostMapping("/vagons/updateTayyor/{id}")
	public String updateVagon(@ModelAttribute("vagon") VagonTayyor vagon, @PathVariable Long id,Model model, HttpServletRequest request) throws NotFoundException {
    	if (request.isUserInRole("DIRECTOR")) {
    		vagonTayyorService.updateVagon(vagon, id);
        }else if (request.isUserInRole("SAM")) {
        	vagonTayyorService.updateVagonSam(vagon, id);
        }else if (request.isUserInRole("HAV")) {
        	vagonTayyorService.updateVagonHav(vagon, id);
        }else if (request.isUserInRole("ANDJ")) {
        	vagonTayyorService.updateVagonAndj(vagon, id);
        }
    	return "redirect:/vagons/AllPlanTable";
	}
    
    //tahrirlash jami oylar uchun
  //tahrirlash uchun oyna
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("/vagons/editTayyorMonths/{id}")
	public String editVagonFormMonths(@PathVariable("id") Long id, Model model) {
		model.addAttribute("vagon",vagonTayyorService.getVagonById(id));
		return "edit_tayyorvagonMonths";
	}

    //tahrirni saqlash
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@PostMapping("/vagons/updateTayyorMonths/{id}")
	public String updateVagonMonths(@ModelAttribute("vagon") VagonTayyor vagon, @PathVariable Long id,Model model, HttpServletRequest request) throws NotFoundException {
    	if (request.isUserInRole("DIRECTOR")) {
    		vagonTayyorService.updateVagonMonths(vagon, id);
        }else if (request.isUserInRole("SAM")) {
        	vagonTayyorService.updateVagonSamMonths(vagon, id);
        }else if (request.isUserInRole("HAV")) {
        	vagonTayyorService.updateVagonHavMonths(vagon, id);
        }else if (request.isUserInRole("ANDJ")) {
        	vagonTayyorService.updateVagonAndjMonths(vagon, id);
        }
    	return "redirect:/vagons/planTableForMonths";
	}
	
    //bazadan o'chirish
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("/vagons/deleteTayyor/{id}")
	public String deleteVagonForm(@PathVariable("id") Long id, HttpServletRequest request ) throws NotFoundException {
    	if (request.isUserInRole("DIRECTOR")) {
    		vagonTayyorService.deleteVagonById(id);
        }else if (request.isUserInRole("SAM")) {
        		vagonTayyorService.deleteVagonSam(id);
        }else if (request.isUserInRole("HAV")) {
    		vagonTayyorService.deleteVagonHav(id);
        }else if (request.isUserInRole("ANDJ")) {
    		vagonTayyorService.deleteVagonAndj(id);
        }
		return "redirect:/vagons/AllPlanTable";
	}

    //All planlar uchun 
    @PreAuthorize(value = "hasRole('DIRECTOR')")
   	@GetMapping("/vagons/newPlan")
   	public String addPlan(Model model) {
   		PlanDto planDto = new PlanDto();
   		model.addAttribute("planDto", planDto);
   		return "add_plan";
   	}
    
    //Plan qoshish
    @PreAuthorize(value = "hasAnyRole('DIRECTOR')")
	@PostMapping("/vagons/savePlan")
	public String savePlan(@ModelAttribute("planDto") PlanDto planDto) { 
    	vagonTayyorService.savePlan(planDto);
    	return "redirect:/vagons/AllPlanTable";
	}

    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("/vagons/AllPlanTable")
	public String getAllPlan(Model model) {	
    	
    	//listni toldirish uchun
    	vagonsToDownload = vagonTayyorService.findAllActive();
    	model.addAttribute("vagons", vagonTayyorService.findAllActive());
    	
    	//vaqtni olib turadi
		model.addAttribute("samDate",vagonTayyorService.getSamDate());
		model.addAttribute("havDate", vagonTayyorService.getHavDate());
		model.addAttribute("andjDate",vagonTayyorService.getAndjDate());
    	
    	PlanDto planDto = vagonTayyorService.getPlanDto();
    	//planlar kiritish
    	//samarqand depo tamir
    	int SamDtHammaPlan=planDto.getSamDtKritiPlan() + planDto.getSamDtPlatformaPlan() + planDto.getSamDtPoluvagonPlan() + planDto.getSamDtSisternaPlan() + planDto.getSamDtBoshqaPlan();
    	model.addAttribute("SamDtHammaPlan",SamDtHammaPlan);
    	model.addAttribute("SamDtKritiPlan", planDto.getSamDtKritiPlan());
    	model.addAttribute("SamDtPlatformaPlan", planDto.getSamDtPlatformaPlan());
    	model.addAttribute("SamDtPoluvagonPlan", planDto.getSamDtPoluvagonPlan());
    	model.addAttribute("SamDtSisternaPlan", planDto.getSamDtSisternaPlan());
    	model.addAttribute("SamDtBoshqaPlan", planDto.getSamDtBoshqaPlan());
    	
    	//havos hamma plan
    	int HavDtHammaPlan = planDto.getHavDtKritiPlan() + planDto.getHavDtPlatformaPlan() + planDto.getHavDtPoluvagonPlan() + planDto.getHavDtSisternaPlan() + planDto.getHavDtBoshqaPlan();
    	model.addAttribute("HavDtHammaPlan", HavDtHammaPlan);
    	model.addAttribute("HavDtKritiPlan", planDto.getHavDtKritiPlan());
    	model.addAttribute("HavDtPlatformaPlan", planDto.getHavDtPlatformaPlan());
    	model.addAttribute("HavDtPoluvagonPlan", planDto.getHavDtPoluvagonPlan());
    	model.addAttribute("HavDtSisternaPlan", planDto.getHavDtSisternaPlan());
    	model.addAttribute("HavDtBoshqaPlan", planDto.getHavDtBoshqaPlan());
    	
    	//andijon hamma plan depo tamir
    	int AndjDtHammaPlan = planDto.getAndjDtKritiPlan() + planDto.getAndjDtPlatformaPlan() + planDto.getAndjDtPoluvagonPlan() + planDto.getAndjDtSisternaPlan() + planDto.getAndjDtBoshqaPlan();
    	model.addAttribute("AndjDtHammaPlan", AndjDtHammaPlan);
    	model.addAttribute("AndjDtKritiPlan", planDto.getAndjDtKritiPlan());
    	model.addAttribute("AndjDtPlatformaPlan", planDto.getAndjDtPlatformaPlan());
    	model.addAttribute("AndjDtPoluvagonPlan", planDto.getAndjDtPoluvagonPlan());
    	model.addAttribute("AndjDtSisternaPlan", planDto.getAndjDtSisternaPlan());
    	model.addAttribute("AndjDtBoshqaPlan", planDto.getAndjDtBoshqaPlan());
    	
    	// Itogo planlar depo tamir
    	model.addAttribute("DtHammaPlan", AndjDtHammaPlan + HavDtHammaPlan + SamDtHammaPlan);
    	model.addAttribute("DtKritiPlan", planDto.getAndjDtKritiPlan() + planDto.getHavDtKritiPlan() + planDto.getSamDtKritiPlan());
    	model.addAttribute("DtPlatformaPlan", planDto.getAndjDtPlatformaPlan() + planDto.getHavDtPlatformaPlan() + planDto.getSamDtPlatformaPlan());
    	model.addAttribute("DtPoluvagonPlan",planDto.getAndjDtPoluvagonPlan() + planDto.getHavDtPoluvagonPlan() + planDto.getSamDtPoluvagonPlan());
    	model.addAttribute("DtSisternaPlan", planDto.getAndjDtSisternaPlan() + planDto.getHavDtSisternaPlan() + planDto.getSamDtSisternaPlan());
    	model.addAttribute("DtBoshqaPlan", planDto.getAndjDtBoshqaPlan() + planDto.getHavDtBoshqaPlan() + planDto.getSamDtBoshqaPlan());
    	
    	//VCHD-6 kapital tamir uchun plan
    	int SamKtHammaPlan =  planDto.getSamKtKritiPlan() + planDto.getSamKtPlatformaPlan() + planDto.getSamKtPoluvagonPlan() + planDto.getSamKtSisternaPlan() + planDto.getSamKtBoshqaPlan();
    	model.addAttribute("SamKtHammaPlan",SamKtHammaPlan);
    	model.addAttribute("SamKtKritiPlan", planDto.getSamKtKritiPlan());
    	model.addAttribute("SamKtPlatformaPlan", planDto.getSamKtPlatformaPlan());
    	model.addAttribute("SamKtPoluvagonPlan", planDto.getSamKtPoluvagonPlan());
    	model.addAttribute("SamKtSisternaPlan", planDto.getSamKtSisternaPlan());
    	model.addAttribute("SamKtBoshqaPlan", planDto.getSamKtBoshqaPlan());
    	
    	//havos kapital tamir uchun plan
    	int HavKtHammaPlan = planDto.getHavKtKritiPlan() + planDto.getHavKtPlatformaPlan() + planDto.getHavKtPoluvagonPlan() + planDto.getHavKtSisternaPlan() + planDto.getHavKtBoshqaPlan();
    	model.addAttribute("HavKtHammaPlan", HavKtHammaPlan);
    	model.addAttribute("HavKtKritiPlan", planDto.getHavKtKritiPlan());
    	model.addAttribute("HavKtPlatformaPlan", planDto.getHavKtPlatformaPlan());
    	model.addAttribute("HavKtPoluvagonPlan", planDto.getHavKtPoluvagonPlan());
    	model.addAttribute("HavKtSisternaPlan", planDto.getHavKtSisternaPlan());
    	model.addAttribute("HavKtBoshqaPlan", planDto.getHavKtBoshqaPlan());
    	
    	//VCHD-5 kapital tamir uchun plan
    	int AndjKtHammaPlan = planDto.getAndjKtKritiPlan() + planDto.getAndjKtPlatformaPlan() + planDto.getAndjKtPoluvagonPlan() + planDto.getAndjKtSisternaPlan() + planDto.getAndjKtBoshqaPlan();
    	model.addAttribute("AndjKtHammaPlan", AndjKtHammaPlan);
    	model.addAttribute("AndjKtKritiPlan", planDto.getAndjKtKritiPlan());
    	model.addAttribute("AndjKtPlatformaPlan", planDto.getAndjKtPlatformaPlan());
    	model.addAttribute("AndjKtPoluvagonPlan", planDto.getAndjKtPoluvagonPlan());
    	model.addAttribute("AndjKtSisternaPlan", planDto.getAndjKtSisternaPlan());
    	model.addAttribute("AndjKtBoshqaPlan", planDto.getAndjKtBoshqaPlan());
    	
    	//kapital itogo
    	model.addAttribute("KtHammaPlan", AndjKtHammaPlan + HavKtHammaPlan + SamKtHammaPlan);
    	model.addAttribute("KtKritiPlan", planDto.getAndjKtKritiPlan() + planDto.getHavKtKritiPlan() + planDto.getSamKtKritiPlan());
    	model.addAttribute("KtPlatformaPlan", planDto.getAndjKtPlatformaPlan() + planDto.getHavKtPlatformaPlan() + planDto.getSamKtPlatformaPlan());
    	model.addAttribute("KtPoluvagonPlan",planDto.getAndjKtPoluvagonPlan() + planDto.getHavKtPoluvagonPlan() + planDto.getSamKtPoluvagonPlan());
    	model.addAttribute("KtSisternaPlan", planDto.getAndjKtSisternaPlan() + planDto.getHavKtSisternaPlan() + planDto.getSamKtSisternaPlan());
    	model.addAttribute("KtBoshqaPlan", planDto.getAndjKtBoshqaPlan() + planDto.getHavKtBoshqaPlan() + planDto.getSamKtBoshqaPlan());
  
    	//samarqand KRP plan
    	int SamKrpHammaPlan = planDto.getSamKrpKritiPlan() + planDto.getSamKrpPlatformaPlan() + planDto.getSamKrpPoluvagonPlan() + planDto.getSamKrpSisternaPlan() + planDto.getSamKrpBoshqaPlan();
    	model.addAttribute("SamKrpHammaPlan", SamKrpHammaPlan);
    	model.addAttribute("SamKrpKritiPlan", planDto.getSamKrpKritiPlan());
    	model.addAttribute("SamKrpPlatformaPlan", planDto.getSamKrpPlatformaPlan());
    	model.addAttribute("SamKrpPoluvagonPlan", planDto.getSamKrpPoluvagonPlan());
    	model.addAttribute("SamKrpSisternaPlan", planDto.getSamKrpSisternaPlan());
    	model.addAttribute("SamKrpBoshqaPlan", planDto.getSamKrpBoshqaPlan());
    	
    	//VCHD-3 KRP plan
    	int HavKrpHammaPlan =  planDto.getHavKrpKritiPlan() + planDto.getHavKrpPlatformaPlan() + planDto.getHavKrpPoluvagonPlan() + planDto.getHavKrpSisternaPlan() + planDto.getHavKrpBoshqaPlan();
    	model.addAttribute("HavKrpHammaPlan",HavKrpHammaPlan);
    	model.addAttribute("HavKrpKritiPlan", planDto.getHavKrpKritiPlan());
    	model.addAttribute("HavKrpPlatformaPlan", planDto.getHavKrpPlatformaPlan());
    	model.addAttribute("HavKrpPoluvagonPlan", planDto.getHavKrpPoluvagonPlan());
    	model.addAttribute("HavKrpSisternaPlan", planDto.getHavKrpSisternaPlan());
    	model.addAttribute("HavKrpBoshqaPlan", planDto.getHavKrpBoshqaPlan());
    	
    	//VCHD-5 Krp plan
    	int AndjKrpHammaPlan =  planDto.getAndjKrpKritiPlan() + planDto.getAndjKrpPlatformaPlan() + planDto.getAndjKrpPoluvagonPlan() + planDto.getAndjKrpSisternaPlan() + planDto.getAndjKrpBoshqaPlan();
    	model.addAttribute("AndjKrpHammaPlan",AndjKrpHammaPlan);
    	model.addAttribute("AndjKrpKritiPlan", planDto.getAndjKrpKritiPlan());
    	model.addAttribute("AndjKrpPlatformaPlan", planDto.getAndjKrpPlatformaPlan());
    	model.addAttribute("AndjKrpPoluvagonPlan", planDto.getAndjKrpPoluvagonPlan());
    	model.addAttribute("AndjKrpSisternaPlan", planDto.getAndjKrpSisternaPlan());
    	model.addAttribute("AndjKrpBoshqaPlan", planDto.getAndjKrpBoshqaPlan());
    	
    	//Krp itogo plan
    	model.addAttribute("KrpHammaPlan", AndjKrpHammaPlan + HavKrpHammaPlan + SamKrpHammaPlan);
    	model.addAttribute("KrpKritiPlan", planDto.getAndjKrpKritiPlan() + planDto.getHavKrpKritiPlan() + planDto.getSamKrpKritiPlan());
    	model.addAttribute("KrpPlatformaPlan", planDto.getAndjKrpPlatformaPlan() + planDto.getHavKrpPlatformaPlan() + planDto.getSamKrpPlatformaPlan());
    	model.addAttribute("KrpPoluvagonPlan",planDto.getAndjKrpPoluvagonPlan() + planDto.getHavKrpPoluvagonPlan() + planDto.getSamKrpPoluvagonPlan());
    	model.addAttribute("KrpSisternaPlan", planDto.getAndjKrpSisternaPlan() + planDto.getHavKrpSisternaPlan() + planDto.getSamKrpSisternaPlan());
    	model.addAttribute("KrpBoshqaPlan", planDto.getAndjKrpBoshqaPlan() + planDto.getHavKrpBoshqaPlan() + planDto.getSamKrpBoshqaPlan());
  
 // factlar 
    	//samarqand uchun depli tamir
    	int sdHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) +
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)", true) +
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
    	model.addAttribute("sdHamma",sdHamma);
    	model.addAttribute("sdKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
    	
    	//VCHD-3 uchun depli tamir
    	int hdHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)", true) +  
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)", true) +
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
    	model.addAttribute("hdHamma",hdHamma);
    	model.addAttribute("hdKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdBoshqaPlan", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
    
    	//VCHD-5 uchun depli tamir
    	int adHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)", true) +  
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)", true);
    	model.addAttribute("adHamma",adHamma);
    	model.addAttribute("adKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
    
    	// itogo Fact uchun depli tamir
    	 int factdhamma = sdHamma + hdHamma + adHamma;
    	 model.addAttribute("factdhamma",factdhamma);
    	 int boshqaPlan = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) +
    			 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) + 
    			 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
    	 model.addAttribute("boshqaPlan",boshqaPlan);
    	 
     	//samarqand uchun Kapital tamir
     	int skHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) + 
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)", true) +  
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) + 
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)", true) +
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
     	model.addAttribute("skHamma",skHamma);
     	model.addAttribute("skKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
     	
     	//VCHD-3 uchun kapital tamir
     	int hkHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) + 
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)", true) +  
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) + 
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)", true) +
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
     	model.addAttribute("hkHamma",hkHamma);
     	model.addAttribute("hkKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
    
     	//VCHD-5 uchun kapital tamir
     	int akHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) + 
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)", true) +  
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) + 
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)", true) +
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
     	model.addAttribute("akHamma",akHamma);
     	model.addAttribute("akKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
     
	     // itogo Fact uchun kapital tamir
	   	 int factkhamma = skHamma + hkHamma + akHamma;
	   	 model.addAttribute("factkhamma",factkhamma);
	   	 int boshqaKPlan = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true) +
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true) + 
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
	   	 			model.addAttribute("boshqaKPlan",boshqaKPlan);
	   	  
    	
      	//samarqand uchun KRP tamir
      	int skrHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)", true) +  
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)", true) +
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true);
      	model.addAttribute("skrHamma",skrHamma);
      	model.addAttribute("skrKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)", true));
      	model.addAttribute("skrPlatforma",vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)", true));
      	model.addAttribute("skrPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)", true));
      	model.addAttribute("skrSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)", true));
      	model.addAttribute("skrBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true));
      	
      	//VCHD-3 uchun KRP
      	int hkrHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)", true) +  
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)", true) +
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true);
      	model.addAttribute("hkrHamma",hkrHamma);
      	model.addAttribute("hkrKriti",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)", true));
      	model.addAttribute("hkrPlatforma",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)", true));
      	model.addAttribute("hkrPoluvagon",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)", true));
      	model.addAttribute("hkrSisterna",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)", true));
      	model.addAttribute("hkrBoshqa",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true));
     
      	//VCHD-5 uchun KRP
      	int akrHamma =  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)", true) +  
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)", true) +
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true);
      	model.addAttribute("akrHamma",akrHamma);
      	model.addAttribute("akrKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)", true));
      	model.addAttribute("akrPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)", true));
      	model.addAttribute("akrPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)", true));
      	model.addAttribute("akrSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)", true));
      	model.addAttribute("akrBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true));
      
 	     // itogo Fact uchun KRP
 	   	 int factkrhamma = skrHamma + hkrHamma + akrHamma;
 	   	 model.addAttribute("factkrhamma",factkrhamma);
 	   	 int boshqaKr = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true) +
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true) + 
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true);
	   	 model.addAttribute("boshqaKr",boshqaKr);
    	 
    	 return "AllPlanTable";
	}
    
 
	
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
   	@GetMapping("/vagons/planTableForMonths")
   	public String getPlanForAllMonths(Model model) {
    	PlanDto planDto = vagonTayyorService.getPlanDtoAllMonth();
    	//planlar kiritish
	    
    	//samarqand depo tamir plan
    	sdKriti += planDto.getSamDtKritiPlan();
    	sdPlatforma+=planDto.getSamDtPlatformaPlan();
    	sdPoluvagon+=planDto.getSamDtPoluvagonPlan();
    	sdSisterna+=planDto.getSamDtSisternaPlan();
    	sdBoshqa+=planDto.getSamDtBoshqaPlan();
    	SamDtHammaPlan=sdKriti+sdPlatforma+sdPoluvagon+sdSisterna+sdBoshqa;
    	
    	model.addAttribute("SamDtHammaPlan",SamDtHammaPlan);
    	model.addAttribute("SamDtKritiPlan", sdKriti);
    	model.addAttribute("SamDtPlatformaPlan", sdPlatforma);
    	model.addAttribute("SamDtPoluvagonPlan", sdPoluvagon);
    	model.addAttribute("SamDtSisternaPlan", sdSisterna);
    	model.addAttribute("SamDtBoshqaPlan", sdBoshqa);
    	
    	//havos depo tamir hamma plan
    	hdKriti += planDto.getHavDtKritiPlan();
    	hdPlatforma+=planDto.getHavDtPlatformaPlan();
    	hdPoluvagon+=planDto.getHavDtPoluvagonPlan();
    	hdSisterna+=planDto.getHavDtSisternaPlan();
    	hdBoshqa+=planDto.getHavDtBoshqaPlan();
    	HavDtHammaPlan = hdKriti + hdPlatforma + hdPoluvagon + hdSisterna + hdBoshqa;
    	
    	model.addAttribute("HavDtHammaPlan", HavDtHammaPlan);
    	model.addAttribute("HavDtKritiPlan", hdKriti);
    	model.addAttribute("HavDtPlatformaPlan", hdPlatforma);
    	model.addAttribute("HavDtPoluvagonPlan", hdPoluvagon);
    	model.addAttribute("HavDtSisternaPlan", hdSisterna);
    	model.addAttribute("HavDtBoshqaPlan", hdBoshqa);
    	
    	//VCHD-5 depo tamir plan
    	adKriti += planDto.getAndjDtKritiPlan();
    	adPlatforma+=planDto.getAndjDtPlatformaPlan();
    	adPoluvagon+=planDto.getAndjDtPoluvagonPlan();
    	adSisterna+=planDto.getAndjDtSisternaPlan();
    	adBoshqa+=planDto.getAndjDtBoshqaPlan();
    	AndjDtHammaPlan = adKriti + adPlatforma + adPoluvagon + adSisterna + adBoshqa;
    	
    	model.addAttribute("AndjDtHammaPlan", AndjDtHammaPlan);
    	model.addAttribute("AndjDtKritiPlan", adKriti);
    	model.addAttribute("AndjDtPlatformaPlan",adPlatforma);
    	model.addAttribute("AndjDtPoluvagonPlan", adPoluvagon);
    	model.addAttribute("AndjDtSisternaPlan", adSisterna);
    	model.addAttribute("AndjDtBoshqaPlan", adBoshqa);
    	
    	// Itogo planlar depo tamir
    	model.addAttribute("DtHammaPlan", AndjDtHammaPlan + HavDtHammaPlan + SamDtHammaPlan);
    	model.addAttribute("DtKritiPlan", sdKriti + hdKriti + adKriti);
    	model.addAttribute("DtPlatformaPlan", sdPlatforma + hdPlatforma + adPlatforma);
    	model.addAttribute("DtPoluvagonPlan",sdPoluvagon + hdPoluvagon + adPoluvagon);
    	model.addAttribute("DtSisternaPlan", sdSisterna + hdSisterna + adSisterna);
    	model.addAttribute("DtBoshqaPlan", sdBoshqa + hdBoshqa + adBoshqa);
    	
    	//Samrqand kapital plan
    	skKriti += planDto.getSamKtKritiPlan();
    	skPlatforma+=planDto.getSamKtPlatformaPlan();
    	skPoluvagon+=planDto.getSamKtPoluvagonPlan();
    	skSisterna+=planDto.getSamKtSisternaPlan();
    	skBoshqa+=planDto.getSamKtBoshqaPlan();
    	SamKtHammaPlan=skKriti+skPlatforma+skPoluvagon+skSisterna+skBoshqa;
    	
    	model.addAttribute("SamKtHammaPlan",SamKtHammaPlan);
    	model.addAttribute("SamKtKritiPlan", skKriti);
    	model.addAttribute("SamKtPlatformaPlan", skPlatforma);
    	model.addAttribute("SamKtPoluvagonPlan", skPoluvagon);
    	model.addAttribute("SamKtSisternaPlan", skSisterna);
    	model.addAttribute("SamKtBoshqaPlan", skBoshqa);
    	
    	//hovos kapital plan
    	hkKriti += planDto.getHavKtKritiPlan();
    	hkPlatforma+=planDto.getHavKtPlatformaPlan();
    	hkPoluvagon+=planDto.getHavKtPoluvagonPlan();
    	hkSisterna+=planDto.getHavKtSisternaPlan();
    	hkBoshqa+=planDto.getHavKtBoshqaPlan();
    	HavKtHammaPlan = hkKriti + hkPlatforma + hkPoluvagon + hkSisterna + hkBoshqa;
    	
    	model.addAttribute("HavKtHammaPlan", HavKtHammaPlan);
    	model.addAttribute("HavKtKritiPlan", hkKriti);
    	model.addAttribute("HavKtPlatformaPlan", hkPlatforma);
    	model.addAttribute("HavKtPoluvagonPlan", hkPoluvagon);
    	model.addAttribute("HavKtSisternaPlan", hkSisterna);
    	model.addAttribute("HavKtBoshqaPlan", hkBoshqa);
    	
    	//ANDIJON kapital plan
    	akKriti += planDto.getAndjKtKritiPlan();
    	akPlatforma+=planDto.getAndjKtPlatformaPlan();
    	akPoluvagon+=planDto.getAndjKtPoluvagonPlan();
    	akSisterna+=planDto.getAndjKtSisternaPlan();
    	akBoshqa+=planDto.getAndjKtBoshqaPlan();
    	AndjKtHammaPlan = akKriti + akPlatforma + akPoluvagon + akSisterna + akBoshqa;
    	
    	
    	model.addAttribute("AndjKtHammaPlan", AndjKtHammaPlan);
    	model.addAttribute("AndjKtKritiPlan", akKriti);
    	model.addAttribute("AndjKtPlatformaPlan", akPlatforma);
    	model.addAttribute("AndjKtPoluvagonPlan", akPoluvagon);
    	model.addAttribute("AndjKtSisternaPlan", akSisterna);
    	model.addAttribute("AndjKtBoshqaPlan", akBoshqa);
    	
    	//Itogo kapital plan
    	model.addAttribute("KtHammaPlan", AndjKtHammaPlan + HavKtHammaPlan + SamKtHammaPlan);
    	model.addAttribute("KtKritiPlan", skKriti + hkKriti + akKriti);
    	model.addAttribute("KtPlatformaPlan", skPlatforma + hkPlatforma + akPlatforma);
    	model.addAttribute("KtPoluvagonPlan",skPoluvagon + hkPoluvagon + akPoluvagon);
    	model.addAttribute("KtSisternaPlan", skSisterna + hkSisterna + akSisterna);
    	model.addAttribute("KtBoshqaPlan", skBoshqa + hkBoshqa + akBoshqa);
  
    	//Samarqankr Krp plan
    	skrKriti += planDto.getSamKrpKritiPlan();
    	skrPlatforma+=planDto.getSamKrpPlatformaPlan();
    	skrPoluvagon+=planDto.getSamKrpPoluvagonPlan();
    	skrSisterna+=planDto.getSamKrpSisternaPlan();
    	skrBoshqa+=planDto.getSamKrpBoshqaPlan();
    	SamKrpHammaPlan=skrKriti+skrPlatforma+skrPoluvagon+skrSisterna+skrBoshqa;
    	
    	model.addAttribute("SamKrpHammaPlan", SamKrpHammaPlan);
    	model.addAttribute("SamKrpKritiPlan", skrKriti);
    	model.addAttribute("SamKrpPlatformaPlan", skrPlatforma);
    	model.addAttribute("SamKrpPoluvagonPlan", skrPoluvagon);
    	model.addAttribute("SamKrpSisternaPlan", skrSisterna);
    	model.addAttribute("SamKrpBoshqaPlan", skrBoshqa);
    	
    	//Hovos krp plan
    	hkrKriti += planDto.getHavKrpKritiPlan();
    	hkrPlatforma+=planDto.getHavKrpPlatformaPlan();
    	hkrPoluvagon+=planDto.getHavKrpPoluvagonPlan();
    	hkrSisterna+=planDto.getHavKrpSisternaPlan();
    	hkrBoshqa+=planDto.getHavKrpBoshqaPlan();
    	HavKrpHammaPlan = hkrKriti + hkrPlatforma + hkrPoluvagon + hkrSisterna + hkrBoshqa;
    	
    	model.addAttribute("HavKrpHammaPlan",HavKrpHammaPlan);
    	model.addAttribute("HavKrpKritiPlan", hkrKriti);
    	model.addAttribute("HavKrpPlatformaPlan", hkrPlatforma);
    	model.addAttribute("HavKrpPoluvagonPlan", hkrPoluvagon);
    	model.addAttribute("HavKrpSisternaPlan", hkrSisterna);
    	model.addAttribute("HavKrpBoshqaPlan", hkrBoshqa);
    	
    	//andijon krp plan
    	akrKriti += planDto.getAndjKrpKritiPlan();
    	akrPlatforma+=planDto.getAndjKrpPlatformaPlan();
    	akrPoluvagon+=planDto.getAndjKrpPoluvagonPlan();
    	akrSisterna+=planDto.getAndjKrpSisternaPlan();
    	akrBoshqa+=planDto.getAndjKrpBoshqaPlan();
    	AndjKrpHammaPlan = akrKriti + akrPlatforma + akrPoluvagon + akrSisterna + akrBoshqa;
    	
    	model.addAttribute("AndjKrpHammaPlan",AndjKrpHammaPlan);
    	model.addAttribute("AndjKrpKritiPlan", akrKriti);
    	model.addAttribute("AndjKrpPlatformaPlan", akrPlatforma);
    	model.addAttribute("AndjKrpPoluvagonPlan", akrPoluvagon);
    	model.addAttribute("AndjKrpSisternaPlan", akrSisterna);
    	model.addAttribute("AndjKrpBoshqaPlan", akrBoshqa);
    	
    	//itogo krp
    	model.addAttribute("KrpHammaPlan", AndjKrpHammaPlan + HavKrpHammaPlan + SamKrpHammaPlan);
    	model.addAttribute("KrpKritiPlan", skrKriti + hkrKriti + akrKriti);
    	model.addAttribute("KrpPlatformaPlan", skrPlatforma + hkrPlatforma + akrPlatforma);
    	model.addAttribute("KrpPoluvagonPlan",akrPoluvagon + hkrPoluvagon + skrPoluvagon);
    	model.addAttribute("KrpSisternaPlan", skrSisterna + hkrSisterna + akrSisterna);
    	model.addAttribute("KrpBoshqaPlan", skrBoshqa + hkrBoshqa + akrBoshqa);

    	vagonTayyorService.makePlanNul();
    	//**//
    	// samarqand depo tamir hamma false vagonlar soni
    	int sdKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
    	int sdPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)");
    	int sdPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
    	int sdSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)");
    	int sdBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
    	int sdHammaFalse = sdKritiFalse + sdPlatformaFalse+ sdPoluvagonFalse+ sdSisternaFalse + sdBoshqaFalse;
    	
    	model.addAttribute("sdHammaFalse",sdHammaFalse);
    	model.addAttribute("sdKritiFalse",sdKritiFalse);
    	model.addAttribute("sdPlatformaFalse",sdPlatformaFalse);
    	model.addAttribute("sdPoluvagonFalse",sdPoluvagonFalse);
    	model.addAttribute("sdSisternaFalse",sdSisternaFalse);
    	model.addAttribute("sdBoshqaFalse",sdBoshqaFalse);
    	
    	// VCHD-3 depo tamir hamma false vagonlar soni
    	int hdKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
    	int hdPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)");
    	int hdPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
    	int hdSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)");
    	int hdBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
    	int hdHammaFalse = hdKritiFalse + hdPlatformaFalse+ hdPoluvagonFalse+ hdSisternaFalse + hdBoshqaFalse;
    	
    	model.addAttribute("hdHammaFalse",hdHammaFalse);
    	model.addAttribute("hdKritiFalse",hdKritiFalse);
    	model.addAttribute("hdPlatformaFalse",hdPlatformaFalse);
    	model.addAttribute("hdPoluvagonFalse",hdPoluvagonFalse);
    	model.addAttribute("hdSisternaFalse",hdSisternaFalse);
    	model.addAttribute("hdBoshqaFalse",hdBoshqaFalse);
    	
    	// VCHD-5 depo tamir hamma false vagonlar soni
    	int adKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
    	int adPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)");
    	int adPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
    	int adSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)");
    	int adBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
    	int adHammaFalse = adKritiFalse + adPlatformaFalse+ adPoluvagonFalse+ adSisternaFalse + adBoshqaFalse;
    	
    	model.addAttribute("adHammaFalse",adHammaFalse);
    	model.addAttribute("adKritiFalse",adKritiFalse);
    	model.addAttribute("adPlatformaFalse",adPlatformaFalse);
    	model.addAttribute("adPoluvagonFalse",adPoluvagonFalse);
    	model.addAttribute("adSisternaFalse",adSisternaFalse);
    	model.addAttribute("adBoshqaFalse",adBoshqaFalse);
    	
    	// depoli tamir itogo uchun
    	int dHammaFalse =  adHammaFalse + hdHammaFalse+sdHammaFalse;
    	int dKritiFalse = sdKritiFalse + hdKritiFalse + adKritiFalse;
    	int dPlatforma = adPlatformaFalse + sdPlatformaFalse + hdPlatformaFalse;
    	int dPoluvagon  = adPoluvagonFalse + sdPoluvagonFalse + hdPoluvagonFalse;
    	int dSisterna = adSisternaFalse + hdSisternaFalse + sdSisternaFalse;
    	int dBoshqa = adBoshqaFalse + hdBoshqaFalse + sdBoshqaFalse;
    	
    	model.addAttribute("dHammaFalse",dHammaFalse);
    	model.addAttribute("dKritiFalse",dKritiFalse);
    	model.addAttribute("dPlatforma",dPlatforma);
    	model.addAttribute("dPoluvagon",dPoluvagon);
    	model.addAttribute("dSisterna",dSisterna);
    	model.addAttribute("dBoshqa",dBoshqa);
    	
    	
    	// samarqand KApital tamir hamma false vagonlar soni
    	int skKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
    	int skPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)");
    	int skPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
    	int skSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)");
    	int skBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
    	int skHammaFalse = skKritiFalse + skPlatformaFalse+ skPoluvagonFalse+ skSisternaFalse + skBoshqaFalse;
    	
    	model.addAttribute("skHammaFalse",skHammaFalse);
    	model.addAttribute("skKritiFalse",skKritiFalse);
    	model.addAttribute("skPlatformaFalse",skPlatformaFalse);
    	model.addAttribute("skPoluvagonFalse",skPoluvagonFalse);
    	model.addAttribute("skSisternaFalse",skSisternaFalse);
    	model.addAttribute("skBoshqaFalse",skBoshqaFalse);
    	
    	// VCHD-3 kapital tamir hamma false vagonlar soni
    	int hkKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
    	int hkPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)");
    	int hkPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
    	int hkSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)");
    	int hkBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
    	int hkHammaFalse = hkKritiFalse + hkPlatformaFalse+ hkPoluvagonFalse+ hkSisternaFalse + hkBoshqaFalse;
    	
    	model.addAttribute("hkHammaFalse",hkHammaFalse);
    	model.addAttribute("hkKritiFalse",hkKritiFalse);
    	model.addAttribute("hkPlatformaFalse",hkPlatformaFalse);
    	model.addAttribute("hkPoluvagonFalse",hkPoluvagonFalse);
    	model.addAttribute("hkSisternaFalse",hkSisternaFalse);
    	model.addAttribute("hkBoshqaFalse",hkBoshqaFalse);
    	
    	// VCHD-5 kapital tamir hamma false vagonlar soni
    	int akKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
    	int akPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)");
    	int akPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
    	int akSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)");
    	int akBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
    	int akHammaFalse = akKritiFalse + akPlatformaFalse+ akPoluvagonFalse+ akSisternaFalse + akBoshqaFalse;
    	
    	model.addAttribute("akHammaFalse",akHammaFalse);
    	model.addAttribute("akKritiFalse",akKritiFalse);
    	model.addAttribute("akPlatformaFalse",akPlatformaFalse);
    	model.addAttribute("akPoluvagonFalse",akPoluvagonFalse);
    	model.addAttribute("akSisternaFalse",akSisternaFalse);
    	model.addAttribute("akBoshqaFalse",akBoshqaFalse);
    	
    	// Kapital tamir itogo uchun
    	int kHammaFalse =  akHammaFalse + hkHammaFalse+skHammaFalse;
    	int kKritiFalse = skKritiFalse + hkKritiFalse + akKritiFalse;
    	int kPlatforma = akPlatformaFalse + skPlatformaFalse + hkPlatformaFalse;
    	int kPoluvagon  = akPoluvagonFalse + skPoluvagonFalse + hkPoluvagonFalse;
    	int kSisterna = akSisternaFalse + hkSisternaFalse + skSisternaFalse;
    	int kBoshqa = akBoshqaFalse + hkBoshqaFalse + skBoshqaFalse;
    	
    	model.addAttribute("kHammaFalse",kHammaFalse);
    	model.addAttribute("kKritiFalse",kKritiFalse);
    	model.addAttribute("kPlatforma",kPlatforma);
    	model.addAttribute("kPoluvagon",kPoluvagon);
    	model.addAttribute("kSisterna",kSisterna);
    	model.addAttribute("kBoshqa",kBoshqa);
    	
    	//**
    	// samarqand KRP tamir hamma false vagonlar soni
    	int skrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)");
    	int skrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)");
    	int skrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)");
    	int skrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)");
    	int skrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)");
    	int skrHammaFalse = skrKritiFalse + skrPlatformaFalse+ skrPoluvagonFalse+ skrSisternaFalse + skrBoshqaFalse;
    	
    	model.addAttribute("skrHammaFalse",skrHammaFalse);
    	model.addAttribute("skrKritiFalse",skrKritiFalse);
    	model.addAttribute("skrPlatformaFalse",skrPlatformaFalse);
    	model.addAttribute("skrPoluvagonFalse",skrPoluvagonFalse);
    	model.addAttribute("skrSisternaFalse",skrSisternaFalse);
    	model.addAttribute("skrBoshqaFalse",skrBoshqaFalse);
    	
    	// VCHD-3 KRP tamir hamma false vagonlar soni
    	int hkrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)");
    	int hkrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)");
    	int hkrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)");
    	int hkrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)");
    	int hkrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)");
    	int hkrHammaFalse = hkrKritiFalse + hkrPlatformaFalse+ hkrPoluvagonFalse+ hkrSisternaFalse + hkrBoshqaFalse;
    	
    	model.addAttribute("hkrHammaFalse",hkrHammaFalse);
    	model.addAttribute("hkrKritiFalse",hkrKritiFalse);
    	model.addAttribute("hkrPlatformaFalse",hkrPlatformaFalse);
    	model.addAttribute("hkrPoluvagonFalse",hkrPoluvagonFalse);
    	model.addAttribute("hkrSisternaFalse",hkrSisternaFalse);
    	model.addAttribute("hkrBoshqaFalse",hkrBoshqaFalse);
    	
    	// VCHD-5 KRP tamir hamma false vagonlar soni
    	int akrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)");
    	int akrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)");
    	int akrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)");
    	int akrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)");
    	int akrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)");
    	int akrHammaFalse = akrKritiFalse + akrPlatformaFalse+ akrPoluvagonFalse+ akrSisternaFalse + akrBoshqaFalse;
    	
    	model.addAttribute("akrHammaFalse",akrHammaFalse);
    	model.addAttribute("akrKritiFalse",akrKritiFalse);
    	model.addAttribute("akrPlatformaFalse",akrPlatformaFalse);
    	model.addAttribute("akrPoluvagonFalse",akrPoluvagonFalse);
    	model.addAttribute("akrSisternaFalse",akrSisternaFalse);
    	model.addAttribute("akBoshqaFalse",akBoshqaFalse);
    	
    	// Krp itogo uchun
    	int krHammaFalse =  akrHammaFalse + hkrHammaFalse+skrHammaFalse;
    	int krKritiFalse = skrKritiFalse + hkrKritiFalse + akrKritiFalse;
    	int krPlatforma = akrPlatformaFalse + skrPlatformaFalse + hkrPlatformaFalse;
    	int krPoluvagon  = akrPoluvagonFalse + skrPoluvagonFalse + hkrPoluvagonFalse;
    	int krSisterna = akrSisternaFalse + hkrSisternaFalse + skrSisternaFalse;
    	int krBoshqa = akrBoshqaFalse + hkrBoshqaFalse + skrBoshqaFalse;
    	
    	model.addAttribute("krHammaFalse",krHammaFalse);
    	model.addAttribute("krKritiFalse",krKritiFalse);
    	model.addAttribute("krPlatforma",krPlatforma);
    	model.addAttribute("krPoluvagon",krPoluvagon);
    	model.addAttribute("krSisterna",krSisterna);
    	model.addAttribute("krBoshqa",krBoshqa);
    	
    	// hamma false vagonlarni list qilib chiqarish
    	vagonsToDownload = vagonTayyorService.findAllBoth();
    	model.addAttribute("vagons", vagonTayyorService.findAllBoth());
  	
    	return "planTableForMonths";
    }

    // wagon nomer orqali qidirish 1 oylida
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("/vagons/searchTayyor")
	public String searchByNomer(Model model,  @RequestParam(value = "participant", required = false) Integer participant) {
		if(participant==null  ) {
			model.addAttribute("vagons", vagonTayyorService.findAllActive());
			vagonsToDownload = vagonTayyorService.findAllActive();
		}else { 
			model.addAttribute("vagons", vagonTayyorService.searchByNomer(participant));
			List<VagonTayyor> emptyList = new ArrayList<>();
			vagonsToDownload = emptyList;
			vagonsToDownload.add( vagonTayyorService.searchByNomer(participant));
		}
    	PlanDto planDto = vagonTayyorService.getPlanDto();
    	//planlar kiritish
    	//samarqand depo tamir
    	int SamDtHammaPlan=planDto.getSamDtKritiPlan() + planDto.getSamDtPlatformaPlan() + planDto.getSamDtPoluvagonPlan() + planDto.getSamDtSisternaPlan() + planDto.getSamDtBoshqaPlan();
    	model.addAttribute("SamDtHammaPlan",SamDtHammaPlan);
    	model.addAttribute("SamDtKritiPlan", planDto.getSamDtKritiPlan());
    	model.addAttribute("SamDtPlatformaPlan", planDto.getSamDtPlatformaPlan());
    	model.addAttribute("SamDtPoluvagonPlan", planDto.getSamDtPoluvagonPlan());
    	model.addAttribute("SamDtSisternaPlan", planDto.getSamDtSisternaPlan());
    	model.addAttribute("SamDtBoshqaPlan", planDto.getSamDtBoshqaPlan());
    	
    	//havos hamma plan
    	int HavDtHammaPlan = planDto.getHavDtKritiPlan() + planDto.getHavDtPlatformaPlan() + planDto.getHavDtPoluvagonPlan() + planDto.getHavDtSisternaPlan() + planDto.getHavDtBoshqaPlan();
    	model.addAttribute("HavDtHammaPlan", HavDtHammaPlan);
    	model.addAttribute("HavDtKritiPlan", planDto.getHavDtKritiPlan());
    	model.addAttribute("HavDtPlatformaPlan", planDto.getHavDtPlatformaPlan());
    	model.addAttribute("HavDtPoluvagonPlan", planDto.getHavDtPoluvagonPlan());
    	model.addAttribute("HavDtSisternaPlan", planDto.getHavDtSisternaPlan());
    	model.addAttribute("HavDtBoshqaPlan", planDto.getHavDtBoshqaPlan());
    	
    	//andijon hamma plan depo tamir
    	int AndjDtHammaPlan = planDto.getAndjDtKritiPlan() + planDto.getAndjDtPlatformaPlan() + planDto.getAndjDtPoluvagonPlan() + planDto.getAndjDtSisternaPlan() + planDto.getAndjDtBoshqaPlan();
    	model.addAttribute("AndjDtHammaPlan", AndjDtHammaPlan);
    	model.addAttribute("AndjDtKritiPlan", planDto.getAndjDtKritiPlan());
    	model.addAttribute("AndjDtPlatformaPlan", planDto.getAndjDtPlatformaPlan());
    	model.addAttribute("AndjDtPoluvagonPlan", planDto.getAndjDtPoluvagonPlan());
    	model.addAttribute("AndjDtSisternaPlan", planDto.getAndjDtSisternaPlan());
    	model.addAttribute("AndjDtBoshqaPlan", planDto.getAndjDtBoshqaPlan());
    	
    	// Itogo planlar depo tamir
    	model.addAttribute("DtHammaPlan", AndjDtHammaPlan + HavDtHammaPlan + SamDtHammaPlan);
    	model.addAttribute("DtKritiPlan", planDto.getAndjDtKritiPlan() + planDto.getHavDtKritiPlan() + planDto.getSamDtKritiPlan());
    	model.addAttribute("DtPlatformaPlan", planDto.getAndjDtPlatformaPlan() + planDto.getHavDtPlatformaPlan() + planDto.getSamDtPlatformaPlan());
    	model.addAttribute("DtPoluvagonPlan",planDto.getAndjDtPoluvagonPlan() + planDto.getHavDtPoluvagonPlan() + planDto.getSamDtPoluvagonPlan());
    	model.addAttribute("DtSisternaPlan", planDto.getAndjDtSisternaPlan() + planDto.getHavDtSisternaPlan() + planDto.getSamDtSisternaPlan());
    	model.addAttribute("DtBoshqaPlan", planDto.getAndjDtBoshqaPlan() + planDto.getHavDtBoshqaPlan() + planDto.getSamDtBoshqaPlan());
    	
    	//VCHD-6 kapital tamir uchun plan
    	int SamKtHammaPlan =  planDto.getSamKtKritiPlan() + planDto.getSamKtPlatformaPlan() + planDto.getSamKtPoluvagonPlan() + planDto.getSamKtSisternaPlan() + planDto.getSamKtBoshqaPlan();
    	model.addAttribute("SamKtHammaPlan",SamKtHammaPlan);
    	model.addAttribute("SamKtKritiPlan", planDto.getSamKtKritiPlan());
    	model.addAttribute("SamKtPlatformaPlan", planDto.getSamKtPlatformaPlan());
    	model.addAttribute("SamKtPoluvagonPlan", planDto.getSamKtPoluvagonPlan());
    	model.addAttribute("SamKtSisternaPlan", planDto.getSamKtSisternaPlan());
    	model.addAttribute("SamKtBoshqaPlan", planDto.getSamKtBoshqaPlan());
    	
    	//havos kapital tamir uchun plan
    	int HavKtHammaPlan = planDto.getHavKtKritiPlan() + planDto.getHavKtPlatformaPlan() + planDto.getHavKtPoluvagonPlan() + planDto.getHavKtSisternaPlan() + planDto.getHavKtBoshqaPlan();
    	model.addAttribute("HavKtHammaPlan", HavKtHammaPlan);
    	model.addAttribute("HavKtKritiPlan", planDto.getHavKtKritiPlan());
    	model.addAttribute("HavKtPlatformaPlan", planDto.getHavKtPlatformaPlan());
    	model.addAttribute("HavKtPoluvagonPlan", planDto.getHavKtPoluvagonPlan());
    	model.addAttribute("HavKtSisternaPlan", planDto.getHavKtSisternaPlan());
    	model.addAttribute("HavKtBoshqaPlan", planDto.getHavKtBoshqaPlan());
    	
    	//VCHD-5 kapital tamir uchun plan
    	int AndjKtHammaPlan = planDto.getAndjKtKritiPlan() + planDto.getAndjKtPlatformaPlan() + planDto.getAndjKtPoluvagonPlan() + planDto.getAndjKtSisternaPlan() + planDto.getAndjKtBoshqaPlan();
    	model.addAttribute("AndjKtHammaPlan", AndjKtHammaPlan);
    	model.addAttribute("AndjKtKritiPlan", planDto.getAndjKtKritiPlan());
    	model.addAttribute("AndjKtPlatformaPlan", planDto.getAndjKtPlatformaPlan());
    	model.addAttribute("AndjKtPoluvagonPlan", planDto.getAndjKtPoluvagonPlan());
    	model.addAttribute("AndjKtSisternaPlan", planDto.getAndjKtSisternaPlan());
    	model.addAttribute("AndjKtBoshqaPlan", planDto.getAndjKtBoshqaPlan());
    	
    	//kapital itogo
    	model.addAttribute("KtHammaPlan", AndjKtHammaPlan + HavKtHammaPlan + SamKtHammaPlan);
    	model.addAttribute("KtKritiPlan", planDto.getAndjKtKritiPlan() + planDto.getHavKtKritiPlan() + planDto.getSamKtKritiPlan());
    	model.addAttribute("KtPlatformaPlan", planDto.getAndjKtPlatformaPlan() + planDto.getHavKtPlatformaPlan() + planDto.getSamKtPlatformaPlan());
    	model.addAttribute("KtPoluvagonPlan",planDto.getAndjKtPoluvagonPlan() + planDto.getHavKtPoluvagonPlan() + planDto.getSamKtPoluvagonPlan());
    	model.addAttribute("KtSisternaPlan", planDto.getAndjKtSisternaPlan() + planDto.getHavKtSisternaPlan() + planDto.getSamKtSisternaPlan());
    	model.addAttribute("KtBoshqaPlan", planDto.getAndjKtBoshqaPlan() + planDto.getHavKtBoshqaPlan() + planDto.getSamKtBoshqaPlan());
  
    	//samarqand KRP plan
    	int SamKrpHammaPlan = planDto.getSamKrpKritiPlan() + planDto.getSamKrpPlatformaPlan() + planDto.getSamKrpPoluvagonPlan() + planDto.getSamKrpSisternaPlan() + planDto.getSamKrpBoshqaPlan();
    	model.addAttribute("SamKrpHammaPlan", SamKrpHammaPlan);
    	model.addAttribute("SamKrpKritiPlan", planDto.getSamKrpKritiPlan());
    	model.addAttribute("SamKrpPlatformaPlan", planDto.getSamKrpPlatformaPlan());
    	model.addAttribute("SamKrpPoluvagonPlan", planDto.getSamKrpPoluvagonPlan());
    	model.addAttribute("SamKrpSisternaPlan", planDto.getSamKrpSisternaPlan());
    	model.addAttribute("SamKrpBoshqaPlan", planDto.getSamKrpBoshqaPlan());
    	
    	//VCHD-3 KRP plan
    	int HavKrpHammaPlan =  planDto.getHavKrpKritiPlan() + planDto.getHavKrpPlatformaPlan() + planDto.getHavKrpPoluvagonPlan() + planDto.getHavKrpSisternaPlan() + planDto.getHavKrpBoshqaPlan();
    	model.addAttribute("HavKrpHammaPlan",HavKrpHammaPlan);
    	model.addAttribute("HavKrpKritiPlan", planDto.getHavKrpKritiPlan());
    	model.addAttribute("HavKrpPlatformaPlan", planDto.getHavKrpPlatformaPlan());
    	model.addAttribute("HavKrpPoluvagonPlan", planDto.getHavKrpPoluvagonPlan());
    	model.addAttribute("HavKrpSisternaPlan", planDto.getHavKrpSisternaPlan());
    	model.addAttribute("HavKrpBoshqaPlan", planDto.getHavKrpBoshqaPlan());
    	
    	//VCHD-5 Krp plan
    	int AndjKrpHammaPlan =  planDto.getAndjKrpKritiPlan() + planDto.getAndjKrpPlatformaPlan() + planDto.getAndjKrpPoluvagonPlan() + planDto.getAndjKrpSisternaPlan() + planDto.getAndjKrpBoshqaPlan();
    	model.addAttribute("AndjKrpHammaPlan",AndjKrpHammaPlan);
    	model.addAttribute("AndjKrpKritiPlan", planDto.getAndjKrpKritiPlan());
    	model.addAttribute("AndjKrpPlatformaPlan", planDto.getAndjKrpPlatformaPlan());
    	model.addAttribute("AndjKrpPoluvagonPlan", planDto.getAndjKrpPoluvagonPlan());
    	model.addAttribute("AndjKrpSisternaPlan", planDto.getAndjKrpSisternaPlan());
    	model.addAttribute("AndjKrpBoshqaPlan", planDto.getAndjKrpBoshqaPlan());
    	
    	//Krp itogo plan
    	model.addAttribute("KrpHammaPlan", AndjKrpHammaPlan + HavKrpHammaPlan + SamKrpHammaPlan);
    	model.addAttribute("KrpKritiPlan", planDto.getAndjKrpKritiPlan() + planDto.getHavKrpKritiPlan() + planDto.getSamKrpKritiPlan());
    	model.addAttribute("KrpPlatformaPlan", planDto.getAndjKrpPlatformaPlan() + planDto.getHavKrpPlatformaPlan() + planDto.getSamKrpPlatformaPlan());
    	model.addAttribute("KrpPoluvagonPlan",planDto.getAndjKrpPoluvagonPlan() + planDto.getHavKrpPoluvagonPlan() + planDto.getSamKrpPoluvagonPlan());
    	model.addAttribute("KrpSisternaPlan", planDto.getAndjKrpSisternaPlan() + planDto.getHavKrpSisternaPlan() + planDto.getSamKrpSisternaPlan());
    	model.addAttribute("KrpBoshqaPlan", planDto.getAndjKrpBoshqaPlan() + planDto.getHavKrpBoshqaPlan() + planDto.getSamKrpBoshqaPlan());
  
 // factlar 
    	//samarqand uchun depli tamir
    	int sdHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) +
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)", true) +
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
    	model.addAttribute("sdHamma",sdHamma);
    	model.addAttribute("sdKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
    	
    	//VCHD-3 uchun depli tamir
    	int hdHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)", true) +  
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)", true) +
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
    	model.addAttribute("hdHamma",hdHamma);
    	model.addAttribute("hdKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdBoshqaPlan", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
    
    	//VCHD-5 uchun depli tamir
    	int adHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)", true) +  
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)", true);
    	model.addAttribute("adHamma",adHamma);
    	model.addAttribute("adKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
    
    	// itogo Fact uchun depli tamir
    	 int factdhamma = sdHamma + hdHamma + adHamma;
    	 model.addAttribute("factdhamma",factdhamma);
    	 int boshqaPlan = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) +
    			 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) + 
    			 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
    	 model.addAttribute("boshqaPlan",boshqaPlan);
    	 
     	//samarqand uchun Kapital tamir
     	int skHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) + 
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)", true) +  
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) + 
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)", true) +
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
     	model.addAttribute("skHamma",skHamma);
     	model.addAttribute("skKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
     	
     	//VCHD-3 uchun kapital tamir
     	int hkHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) + 
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)", true) +  
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) + 
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)", true) +
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
     	model.addAttribute("hkHamma",hkHamma);
     	model.addAttribute("hkKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
    
     	//VCHD-5 uchun kapital tamir
     	int akHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) + 
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)", true) +  
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) + 
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)", true) +
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
     	model.addAttribute("akHamma",akHamma);
     	model.addAttribute("akKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
     
	     // itogo Fact uchun kapital tamir
	   	 int factkhamma = skHamma + hkHamma + akHamma;
	   	 model.addAttribute("factkhamma",factkhamma);
	   	 int boshqaKPlan = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true) +
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true) + 
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
	   	 			model.addAttribute("boshqaKPlan",boshqaKPlan);
	   	  
    	
      	//samarqand uchun KRP tamir
      	int skrHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)", true) +  
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)", true) +
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true);
      	model.addAttribute("skrHamma",skrHamma);
      	model.addAttribute("skrKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)", true));
      	model.addAttribute("skrPlatforma",vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)", true));
      	model.addAttribute("skrPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)", true));
      	model.addAttribute("skrSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)", true));
      	model.addAttribute("skrBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true));
      	
      	//VCHD-3 uchun KRP
      	int hkrHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)", true) +  
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)", true) +
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true);
      	model.addAttribute("hkrHamma",hkrHamma);
      	model.addAttribute("hkrKriti",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)", true));
      	model.addAttribute("hkrPlatforma",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)", true));
      	model.addAttribute("hkrPoluvagon",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)", true));
      	model.addAttribute("hkrSisterna",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)", true));
      	model.addAttribute("hkrBoshqa",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true));
     
      	//VCHD-5 uchun KRP
      	int akrHamma =  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)", true) +  
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)", true) +
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true);
      	model.addAttribute("akrHamma",akrHamma);
      	model.addAttribute("akrKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)", true));
      	model.addAttribute("akrPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)", true));
      	model.addAttribute("akrPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)", true));
      	model.addAttribute("akrSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)", true));
      	model.addAttribute("akrBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true));
      
 	     // itogo Fact uchun KRP
 	   	 int factkrhamma = skrHamma + hkrHamma + akrHamma;
 	   	 model.addAttribute("factkrhamma",factkrhamma);
 	   	 int boshqaKr = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true) +
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true) + 
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true);
	   	 model.addAttribute("boshqaKr",boshqaKr);
		return "AllPlanTable";
    }
    
    // wagon nomer orqali qidirish shu oygacha hammasidan
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
	@GetMapping("/vagons/searchTayyorAllMonths")
	public String search(Model model,  @RequestParam(value = "participant", required = false) Integer participant) {
		if(participant==null  ) {
			model.addAttribute("vagons", vagonTayyorService.findAllBoth());
			vagonsToDownload = vagonTayyorService.findAllBoth();
		}else {
			model.addAttribute("vagons", vagonTayyorService.findByNomer(participant));
			List<VagonTayyor> emptyList = new ArrayList<>();
			vagonsToDownload = emptyList;
			vagonsToDownload.add( vagonTayyorService.findByNomer(participant));
		}
		PlanDto planDto = vagonTayyorService.getPlanDtoAllMonth();
    	//planlar kiritish
	    
    	//samarqand depo tamir plan
    	sdKriti += planDto.getSamDtKritiPlan();
    	sdPlatforma+=planDto.getSamDtPlatformaPlan();
    	sdPoluvagon+=planDto.getSamDtPoluvagonPlan();
    	sdSisterna+=planDto.getSamDtSisternaPlan();
    	sdBoshqa+=planDto.getSamDtBoshqaPlan();
    	SamDtHammaPlan=sdKriti+sdPlatforma+sdPoluvagon+sdSisterna+sdBoshqa;
    	
    	model.addAttribute("SamDtHammaPlan",SamDtHammaPlan);
    	model.addAttribute("SamDtKritiPlan", sdKriti);
    	model.addAttribute("SamDtPlatformaPlan", sdPlatforma);
    	model.addAttribute("SamDtPoluvagonPlan", sdPoluvagon);
    	model.addAttribute("SamDtSisternaPlan", sdSisterna);
    	model.addAttribute("SamDtBoshqaPlan", sdBoshqa);
    	
    	//havos depo tamir hamma plan
    	hdKriti += planDto.getHavDtKritiPlan();
    	hdPlatforma+=planDto.getHavDtPlatformaPlan();
    	hdPoluvagon+=planDto.getHavDtPoluvagonPlan();
    	hdSisterna+=planDto.getHavDtSisternaPlan();
    	hdBoshqa+=planDto.getHavDtBoshqaPlan();
    	HavDtHammaPlan = hdKriti + hdPlatforma + hdPoluvagon + hdSisterna + hdBoshqa;
    	
    	model.addAttribute("HavDtHammaPlan", HavDtHammaPlan);
    	model.addAttribute("HavDtKritiPlan", hdKriti);
    	model.addAttribute("HavDtPlatformaPlan", hdPlatforma);
    	model.addAttribute("HavDtPoluvagonPlan", hdPoluvagon);
    	model.addAttribute("HavDtSisternaPlan", hdSisterna);
    	model.addAttribute("HavDtBoshqaPlan", hdBoshqa);
    	
    	//VCHD-5 depo tamir plan
    	adKriti += planDto.getAndjDtKritiPlan();
    	adPlatforma+=planDto.getAndjDtPlatformaPlan();
    	adPoluvagon+=planDto.getAndjDtPoluvagonPlan();
    	adSisterna+=planDto.getAndjDtSisternaPlan();
    	adBoshqa+=planDto.getAndjDtBoshqaPlan();
    	AndjDtHammaPlan = adKriti + adPlatforma + adPoluvagon + adSisterna + adBoshqa;
    	
    	model.addAttribute("AndjDtHammaPlan", AndjDtHammaPlan);
    	model.addAttribute("AndjDtKritiPlan", adKriti);
    	model.addAttribute("AndjDtPlatformaPlan",adPlatforma);
    	model.addAttribute("AndjDtPoluvagonPlan", adPoluvagon);
    	model.addAttribute("AndjDtSisternaPlan", adSisterna);
    	model.addAttribute("AndjDtBoshqaPlan", adBoshqa);
    	
    	// Itogo planlar depo tamir
    	model.addAttribute("DtHammaPlan", AndjDtHammaPlan + HavDtHammaPlan + SamDtHammaPlan);
    	model.addAttribute("DtKritiPlan", sdKriti + hdKriti + adKriti);
    	model.addAttribute("DtPlatformaPlan", sdPlatforma + hdPlatforma + adPlatforma);
    	model.addAttribute("DtPoluvagonPlan",sdPoluvagon + hdPoluvagon + adPoluvagon);
    	model.addAttribute("DtSisternaPlan", sdSisterna + hdSisterna + adSisterna);
    	model.addAttribute("DtBoshqaPlan", sdBoshqa + hdBoshqa + adBoshqa);
    	
    	//Samrqand kapital plan
    	skKriti += planDto.getSamKtKritiPlan();
    	skPlatforma+=planDto.getSamKtPlatformaPlan();
    	skPoluvagon+=planDto.getSamKtPoluvagonPlan();
    	skSisterna+=planDto.getSamKtSisternaPlan();
    	skBoshqa+=planDto.getSamKtBoshqaPlan();
    	SamKtHammaPlan=skKriti+skPlatforma+skPoluvagon+skSisterna+skBoshqa;
    	
    	model.addAttribute("SamKtHammaPlan",SamKtHammaPlan);
    	model.addAttribute("SamKtKritiPlan", skKriti);
    	model.addAttribute("SamKtPlatformaPlan", skPlatforma);
    	model.addAttribute("SamKtPoluvagonPlan", skPoluvagon);
    	model.addAttribute("SamKtSisternaPlan", skSisterna);
    	model.addAttribute("SamKtBoshqaPlan", skBoshqa);
    	
    	//hovos kapital plan
    	hkKriti += planDto.getHavKtKritiPlan();
    	hkPlatforma+=planDto.getHavKtPlatformaPlan();
    	hkPoluvagon+=planDto.getHavKtPoluvagonPlan();
    	hkSisterna+=planDto.getHavKtSisternaPlan();
    	hkBoshqa+=planDto.getHavKtBoshqaPlan();
    	HavKtHammaPlan = hkKriti + hkPlatforma + hkPoluvagon + hkSisterna + hkBoshqa;
    	
    	model.addAttribute("HavKtHammaPlan", HavKtHammaPlan);
    	model.addAttribute("HavKtKritiPlan", hkKriti);
    	model.addAttribute("HavKtPlatformaPlan", hkPlatforma);
    	model.addAttribute("HavKtPoluvagonPlan", hkPoluvagon);
    	model.addAttribute("HavKtSisternaPlan", hkSisterna);
    	model.addAttribute("HavKtBoshqaPlan", hkBoshqa);
    	
    	//ANDIJON kapital plan
    	akKriti += planDto.getAndjKtKritiPlan();
    	akPlatforma+=planDto.getAndjKtPlatformaPlan();
    	akPoluvagon+=planDto.getAndjKtPoluvagonPlan();
    	akSisterna+=planDto.getAndjKtSisternaPlan();
    	akBoshqa+=planDto.getAndjKtBoshqaPlan();
    	AndjKtHammaPlan = akKriti + akPlatforma + akPoluvagon + akSisterna + akBoshqa;
    	
    	
    	model.addAttribute("AndjKtHammaPlan", AndjKtHammaPlan);
    	model.addAttribute("AndjKtKritiPlan", akKriti);
    	model.addAttribute("AndjKtPlatformaPlan", akPlatforma);
    	model.addAttribute("AndjKtPoluvagonPlan", akPoluvagon);
    	model.addAttribute("AndjKtSisternaPlan", akSisterna);
    	model.addAttribute("AndjKtBoshqaPlan", akBoshqa);
    	
    	//Itogo kapital plan
    	model.addAttribute("KtHammaPlan", AndjKtHammaPlan + HavKtHammaPlan + SamKtHammaPlan);
    	model.addAttribute("KtKritiPlan", skKriti + hkKriti + akKriti);
    	model.addAttribute("KtPlatformaPlan", skPlatforma + hkPlatforma + akPlatforma);
    	model.addAttribute("KtPoluvagonPlan",skPoluvagon + hkPoluvagon + akPoluvagon);
    	model.addAttribute("KtSisternaPlan", skSisterna + hkSisterna + akSisterna);
    	model.addAttribute("KtBoshqaPlan", skBoshqa + hkBoshqa + akBoshqa);
  
    	//Samarqankr Krp plan
    	skrKriti += planDto.getSamKrpKritiPlan();
    	skrPlatforma+=planDto.getSamKrpPlatformaPlan();
    	skrPoluvagon+=planDto.getSamKrpPoluvagonPlan();
    	skrSisterna+=planDto.getSamKrpSisternaPlan();
    	skrBoshqa+=planDto.getSamKrpBoshqaPlan();
    	SamKrpHammaPlan=skrKriti+skrPlatforma+skrPoluvagon+skrSisterna+skrBoshqa;
    	
    	model.addAttribute("SamKrpHammaPlan", SamKrpHammaPlan);
    	model.addAttribute("SamKrpKritiPlan", skrKriti);
    	model.addAttribute("SamKrpPlatformaPlan", skrPlatforma);
    	model.addAttribute("SamKrpPoluvagonPlan", skrPoluvagon);
    	model.addAttribute("SamKrpSisternaPlan", skrSisterna);
    	model.addAttribute("SamKrpBoshqaPlan", skrBoshqa);
    	
    	//Hovos krp plan
    	hkrKriti += planDto.getHavKrpKritiPlan();
    	hkrPlatforma+=planDto.getHavKrpPlatformaPlan();
    	hkrPoluvagon+=planDto.getHavKrpPoluvagonPlan();
    	hkrSisterna+=planDto.getHavKrpSisternaPlan();
    	hkrBoshqa+=planDto.getHavKrpBoshqaPlan();
    	HavKrpHammaPlan = hkrKriti + hkrPlatforma + hkrPoluvagon + hkrSisterna + hkrBoshqa;
    	
    	model.addAttribute("HavKrpHammaPlan",HavKrpHammaPlan);
    	model.addAttribute("HavKrpKritiPlan", hkrKriti);
    	model.addAttribute("HavKrpPlatformaPlan", hkrPlatforma);
    	model.addAttribute("HavKrpPoluvagonPlan", hkrPoluvagon);
    	model.addAttribute("HavKrpSisternaPlan", hkrSisterna);
    	model.addAttribute("HavKrpBoshqaPlan", hkrBoshqa);
    	
    	//andijon krp plan
    	akrKriti += planDto.getAndjKrpKritiPlan();
    	akrPlatforma+=planDto.getAndjKrpPlatformaPlan();
    	akrPoluvagon+=planDto.getAndjKrpPoluvagonPlan();
    	akrSisterna+=planDto.getAndjKrpSisternaPlan();
    	akrBoshqa+=planDto.getAndjKrpBoshqaPlan();
    	AndjKrpHammaPlan = akrKriti + akrPlatforma + akrPoluvagon + akrSisterna + akrBoshqa;
    	
    	model.addAttribute("AndjKrpHammaPlan",AndjKrpHammaPlan);
    	model.addAttribute("AndjKrpKritiPlan", akrKriti);
    	model.addAttribute("AndjKrpPlatformaPlan", akrPlatforma);
    	model.addAttribute("AndjKrpPoluvagonPlan", akrPoluvagon);
    	model.addAttribute("AndjKrpSisternaPlan", akrSisterna);
    	model.addAttribute("AndjKrpBoshqaPlan", akrBoshqa);
    	
    	//itogo krp
    	model.addAttribute("KrpHammaPlan", AndjKrpHammaPlan + HavKrpHammaPlan + SamKrpHammaPlan);
    	model.addAttribute("KrpKritiPlan", skrKriti + hkrKriti + akrKriti);
    	model.addAttribute("KrpPlatformaPlan", skrPlatforma + hkrPlatforma + akrPlatforma);
    	model.addAttribute("KrpPoluvagonPlan",akrPoluvagon + hkrPoluvagon + skrPoluvagon);
    	model.addAttribute("KrpSisternaPlan", skrSisterna + hkrSisterna + akrSisterna);
    	model.addAttribute("KrpBoshqaPlan", skrBoshqa + hkrBoshqa + akrBoshqa);
    	
//    	planDto=planDto0;
    	vagonTayyorService.makePlanNul();

    	//**//
    	// samarqand depo tamir hamma false vagonlar soni
    	int sdKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
    	int sdPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)");
    	int sdPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
    	int sdSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)");
    	int sdBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
    	int sdHammaFalse = sdKritiFalse + sdPlatformaFalse+ sdPoluvagonFalse+ sdSisternaFalse + sdBoshqaFalse;
    	
    	model.addAttribute("sdHammaFalse",sdHammaFalse);
    	model.addAttribute("sdKritiFalse",sdKritiFalse);
    	model.addAttribute("sdPlatformaFalse",sdPlatformaFalse);
    	model.addAttribute("sdPoluvagonFalse",sdPoluvagonFalse);
    	model.addAttribute("sdSisternaFalse",sdSisternaFalse);
    	model.addAttribute("sdBoshqaFalse",sdBoshqaFalse);
    	
    	// VCHD-3 depo tamir hamma false vagonlar soni
    	int hdKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
    	int hdPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)");
    	int hdPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
    	int hdSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)");
    	int hdBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
    	int hdHammaFalse = hdKritiFalse + hdPlatformaFalse+ hdPoluvagonFalse+ hdSisternaFalse + hdBoshqaFalse;
    	
    	model.addAttribute("hdHammaFalse",hdHammaFalse);
    	model.addAttribute("hdKritiFalse",hdKritiFalse);
    	model.addAttribute("hdPlatformaFalse",hdPlatformaFalse);
    	model.addAttribute("hdPoluvagonFalse",hdPoluvagonFalse);
    	model.addAttribute("hdSisternaFalse",hdSisternaFalse);
    	model.addAttribute("hdBoshqaFalse",hdBoshqaFalse);
    	
    	// VCHD-5 depo tamir hamma false vagonlar soni
    	int adKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
    	int adPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)");
    	int adPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
    	int adSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)");
    	int adBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
    	int adHammaFalse = adKritiFalse + adPlatformaFalse+ adPoluvagonFalse+ adSisternaFalse + adBoshqaFalse;
    	
    	model.addAttribute("adHammaFalse",adHammaFalse);
    	model.addAttribute("adKritiFalse",adKritiFalse);
    	model.addAttribute("adPlatformaFalse",adPlatformaFalse);
    	model.addAttribute("adPoluvagonFalse",adPoluvagonFalse);
    	model.addAttribute("adSisternaFalse",adSisternaFalse);
    	model.addAttribute("adBoshqaFalse",adBoshqaFalse);
    	
    	// depoli tamir itogo uchun
    	int dHammaFalse =  adHammaFalse + hdHammaFalse+sdHammaFalse;
    	int dKritiFalse = sdKritiFalse + hdKritiFalse + adKritiFalse;
    	int dPlatforma = adPlatformaFalse + sdPlatformaFalse + hdPlatformaFalse;
    	int dPoluvagon  = adPoluvagonFalse + sdPoluvagonFalse + hdPoluvagonFalse;
    	int dSisterna = adSisternaFalse + hdSisternaFalse + sdSisternaFalse;
    	int dBoshqa = adBoshqaFalse + hdBoshqaFalse + sdBoshqaFalse;
    	
    	model.addAttribute("dHammaFalse",dHammaFalse);
    	model.addAttribute("dKritiFalse",dKritiFalse);
    	model.addAttribute("dPlatforma",dPlatforma);
    	model.addAttribute("dPoluvagon",dPoluvagon);
    	model.addAttribute("dSisterna",dSisterna);
    	model.addAttribute("dBoshqa",dBoshqa);
    	
    	
    	// samarqand KApital tamir hamma false vagonlar soni
    	int skKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
    	int skPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)");
    	int skPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
    	int skSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)");
    	int skBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
    	int skHammaFalse = skKritiFalse + skPlatformaFalse+ skPoluvagonFalse+ skSisternaFalse + skBoshqaFalse;
    	
    	model.addAttribute("skHammaFalse",skHammaFalse);
    	model.addAttribute("skKritiFalse",skKritiFalse);
    	model.addAttribute("skPlatformaFalse",skPlatformaFalse);
    	model.addAttribute("skPoluvagonFalse",skPoluvagonFalse);
    	model.addAttribute("skSisternaFalse",skSisternaFalse);
    	model.addAttribute("skBoshqaFalse",skBoshqaFalse);
    	
    	// VCHD-3 kapital tamir hamma false vagonlar soni
    	int hkKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
    	int hkPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)");
    	int hkPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
    	int hkSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)");
    	int hkBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
    	int hkHammaFalse = hkKritiFalse + hkPlatformaFalse+ hkPoluvagonFalse+ hkSisternaFalse + hkBoshqaFalse;
    	
    	model.addAttribute("hkHammaFalse",hkHammaFalse);
    	model.addAttribute("hkKritiFalse",hkKritiFalse);
    	model.addAttribute("hkPlatformaFalse",hkPlatformaFalse);
    	model.addAttribute("hkPoluvagonFalse",hkPoluvagonFalse);
    	model.addAttribute("hkSisternaFalse",hkSisternaFalse);
    	model.addAttribute("hkBoshqaFalse",hkBoshqaFalse);
    	
    	// VCHD-5 kapital tamir hamma false vagonlar soni
    	int akKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
    	int akPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)");
    	int akPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
    	int akSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)");
    	int akBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
    	int akHammaFalse = akKritiFalse + akPlatformaFalse+ akPoluvagonFalse+ akSisternaFalse + akBoshqaFalse;
    	
    	model.addAttribute("akHammaFalse",akHammaFalse);
    	model.addAttribute("akKritiFalse",akKritiFalse);
    	model.addAttribute("akPlatformaFalse",akPlatformaFalse);
    	model.addAttribute("akPoluvagonFalse",akPoluvagonFalse);
    	model.addAttribute("akSisternaFalse",akSisternaFalse);
    	model.addAttribute("akBoshqaFalse",akBoshqaFalse);
    	
    	// Kapital tamir itogo uchun
    	int kHammaFalse =  akHammaFalse + hkHammaFalse+skHammaFalse;
    	int kKritiFalse = skKritiFalse + hkKritiFalse + akKritiFalse;
    	int kPlatforma = akPlatformaFalse + skPlatformaFalse + hkPlatformaFalse;
    	int kPoluvagon  = akPoluvagonFalse + skPoluvagonFalse + hkPoluvagonFalse;
    	int kSisterna = akSisternaFalse + hkSisternaFalse + skSisternaFalse;
    	int kBoshqa = akBoshqaFalse + hkBoshqaFalse + skBoshqaFalse;
    	
    	model.addAttribute("kHammaFalse",kHammaFalse);
    	model.addAttribute("kKritiFalse",kKritiFalse);
    	model.addAttribute("kPlatforma",kPlatforma);
    	model.addAttribute("kPoluvagon",kPoluvagon);
    	model.addAttribute("kSisterna",kSisterna);
    	model.addAttribute("kBoshqa",kBoshqa);
    	
    	//**
    	// samarqand KRP tamir hamma false vagonlar soni
    	int skrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)");
    	int skrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)");
    	int skrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)");
    	int skrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)");
    	int skrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)");
    	int skrHammaFalse = skrKritiFalse + skrPlatformaFalse+ skrPoluvagonFalse+ skrSisternaFalse + skrBoshqaFalse;
    	
    	model.addAttribute("skrHammaFalse",skrHammaFalse);
    	model.addAttribute("skrKritiFalse",skrKritiFalse);
    	model.addAttribute("skrPlatformaFalse",skrPlatformaFalse);
    	model.addAttribute("skrPoluvagonFalse",skrPoluvagonFalse);
    	model.addAttribute("skrSisternaFalse",skrSisternaFalse);
    	model.addAttribute("skrBoshqaFalse",skrBoshqaFalse);
    	
    	// VCHD-3 KRP tamir hamma false vagonlar soni
    	int hkrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)");
    	int hkrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)");
    	int hkrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)");
    	int hkrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)");
    	int hkrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)");
    	int hkrHammaFalse = hkrKritiFalse + hkrPlatformaFalse+ hkrPoluvagonFalse+ hkrSisternaFalse + hkrBoshqaFalse;
    	
    	model.addAttribute("hkrHammaFalse",hkrHammaFalse);
    	model.addAttribute("hkrKritiFalse",hkrKritiFalse);
    	model.addAttribute("hkrPlatformaFalse",hkrPlatformaFalse);
    	model.addAttribute("hkrPoluvagonFalse",hkrPoluvagonFalse);
    	model.addAttribute("hkrSisternaFalse",hkrSisternaFalse);
    	model.addAttribute("hkrBoshqaFalse",hkrBoshqaFalse);
    	
    	// VCHD-5 KRP tamir hamma false vagonlar soni
    	int akrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)");
    	int akrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)");
    	int akrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)");
    	int akrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)");
    	int akrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)");
    	int akrHammaFalse = akrKritiFalse + akrPlatformaFalse+ akrPoluvagonFalse+ akrSisternaFalse + akrBoshqaFalse;
    	
    	model.addAttribute("akrHammaFalse",akrHammaFalse);
    	model.addAttribute("akrKritiFalse",akrKritiFalse);
    	model.addAttribute("akrPlatformaFalse",akrPlatformaFalse);
    	model.addAttribute("akrPoluvagonFalse",akrPoluvagonFalse);
    	model.addAttribute("akrSisternaFalse",akrSisternaFalse);
    	model.addAttribute("akBoshqaFalse",akBoshqaFalse);
    	
    	// Krp itogo uchun
    	int krHammaFalse =  akrHammaFalse + hkrHammaFalse+skrHammaFalse;
    	int krKritiFalse = skrKritiFalse + hkrKritiFalse + akrKritiFalse;
    	int krPlatforma = akrPlatformaFalse + skrPlatformaFalse + hkrPlatformaFalse;
    	int krPoluvagon  = akrPoluvagonFalse + skrPoluvagonFalse + hkrPoluvagonFalse;
    	int krSisterna = akrSisternaFalse + hkrSisternaFalse + skrSisternaFalse;
    	int krBoshqa = akrBoshqaFalse + hkrBoshqaFalse + skrBoshqaFalse;
    	
    	model.addAttribute("krHammaFalse",krHammaFalse);
    	model.addAttribute("krKritiFalse",krKritiFalse);
    	model.addAttribute("krPlatforma",krPlatforma);
    	model.addAttribute("krPoluvagon",krPoluvagon);
    	model.addAttribute("krSisterna",krSisterna);
    	model.addAttribute("krBoshqa",krBoshqa);
    	
    	return "planTableForMonths";
    }
    
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
   	@GetMapping("/vagons/filterOneMonth")
   	public String filterByDepoNomi(Model model,  @RequestParam(value = "depoNomi", required = false) String depoNomi,
   												@RequestParam(value = "vagonTuri", required = false) String vagonTuri,
   												@RequestParam(value = "country", required = false) String country) {
   		if(!depoNomi.equalsIgnoreCase("Hammasi") && !vagonTuri.equalsIgnoreCase("Hammasi") && !country.equalsIgnoreCase("Hammasi") ) {
   			vagonsToDownload = vagonTayyorService.findAllByDepoNomiVagonTuriAndCountry(depoNomi, vagonTuri, country);
   			model.addAttribute("vagons", vagonTayyorService.findAllByDepoNomiVagonTuriAndCountry(depoNomi, vagonTuri, country));
   		}else if(depoNomi.equalsIgnoreCase("Hammasi") && !vagonTuri.equalsIgnoreCase("Hammasi") && !country.equalsIgnoreCase("Hammasi")){
   			vagonsToDownload = vagonTayyorService.findAllByVagonTuriAndCountry(vagonTuri , country);
   			model.addAttribute("vagons", vagonTayyorService.findAllByVagonTuriAndCountry(vagonTuri , country));
   		}else if(depoNomi.equalsIgnoreCase("Hammasi") && vagonTuri.equalsIgnoreCase("Hammasi")&& !country.equalsIgnoreCase("Hammasi")){
   			vagonsToDownload = vagonTayyorService.findAllBycountry(country );
   			model.addAttribute("vagons", vagonTayyorService.findAllBycountry(country ));
   		}else if(!depoNomi.equalsIgnoreCase("Hammasi") && !vagonTuri.equalsIgnoreCase("Hammasi") && country.equalsIgnoreCase("Hammasi")){
   			vagonsToDownload = vagonTayyorService.findAllByDepoNomiAndVagonTuri(depoNomi, vagonTuri);
   			model.addAttribute("vagons", vagonTayyorService.findAllByDepoNomiAndVagonTuri(depoNomi, vagonTuri));
   		}else if(!depoNomi.equalsIgnoreCase("Hammasi") && vagonTuri.equalsIgnoreCase("Hammasi") && !country.equalsIgnoreCase("Hammasi")){
   			vagonsToDownload = vagonTayyorService.findAllByDepoNomiAndCountry(depoNomi, country);
   			model.addAttribute("vagons", vagonTayyorService.findAllByDepoNomiAndCountry(depoNomi, country));
   		}else if(depoNomi.equalsIgnoreCase("Hammasi") && !vagonTuri.equalsIgnoreCase("Hammasi") && country.equalsIgnoreCase("Hammasi")){
   			vagonsToDownload = vagonTayyorService.findAllByVagonTuri(vagonTuri);
   			model.addAttribute("vagons", vagonTayyorService.findAllByVagonTuri(vagonTuri));
   		}else if(!depoNomi.equalsIgnoreCase("Hammasi") && vagonTuri.equalsIgnoreCase("Hammasi") && country.equalsIgnoreCase("Hammasi")){
   			model.addAttribute("vagons", vagonTayyorService.findAllByDepoNomi(depoNomi ));
   			vagonsToDownload = vagonTayyorService.findAllByDepoNomi(depoNomi );
   		}else {
   			model.addAttribute("vagons", vagonTayyorService.findAllActive());
   			vagonsToDownload = vagonTayyorService.findAllActive();
   		}
   		
   		
    	PlanDto planDto = vagonTayyorService.getPlanDto();
    	//planlar kiritish
    	//samarqand depo tamir
    	int SamDtHammaPlan=planDto.getSamDtKritiPlan() + planDto.getSamDtPlatformaPlan() + planDto.getSamDtPoluvagonPlan() + planDto.getSamDtSisternaPlan() + planDto.getSamDtBoshqaPlan();
    	model.addAttribute("SamDtHammaPlan",SamDtHammaPlan);
    	model.addAttribute("SamDtKritiPlan", planDto.getSamDtKritiPlan());
    	model.addAttribute("SamDtPlatformaPlan", planDto.getSamDtPlatformaPlan());
    	model.addAttribute("SamDtPoluvagonPlan", planDto.getSamDtPoluvagonPlan());
    	model.addAttribute("SamDtSisternaPlan", planDto.getSamDtSisternaPlan());
    	model.addAttribute("SamDtBoshqaPlan", planDto.getSamDtBoshqaPlan());
    	
    	//havos hamma plan
    	int HavDtHammaPlan = planDto.getHavDtKritiPlan() + planDto.getHavDtPlatformaPlan() + planDto.getHavDtPoluvagonPlan() + planDto.getHavDtSisternaPlan() + planDto.getHavDtBoshqaPlan();
    	model.addAttribute("HavDtHammaPlan", HavDtHammaPlan);
    	model.addAttribute("HavDtKritiPlan", planDto.getHavDtKritiPlan());
    	model.addAttribute("HavDtPlatformaPlan", planDto.getHavDtPlatformaPlan());
    	model.addAttribute("HavDtPoluvagonPlan", planDto.getHavDtPoluvagonPlan());
    	model.addAttribute("HavDtSisternaPlan", planDto.getHavDtSisternaPlan());
    	model.addAttribute("HavDtBoshqaPlan", planDto.getHavDtBoshqaPlan());
    	
    	//andijon hamma plan depo tamir
    	int AndjDtHammaPlan = planDto.getAndjDtKritiPlan() + planDto.getAndjDtPlatformaPlan() + planDto.getAndjDtPoluvagonPlan() + planDto.getAndjDtSisternaPlan() + planDto.getAndjDtBoshqaPlan();
    	model.addAttribute("AndjDtHammaPlan", AndjDtHammaPlan);
    	model.addAttribute("AndjDtKritiPlan", planDto.getAndjDtKritiPlan());
    	model.addAttribute("AndjDtPlatformaPlan", planDto.getAndjDtPlatformaPlan());
    	model.addAttribute("AndjDtPoluvagonPlan", planDto.getAndjDtPoluvagonPlan());
    	model.addAttribute("AndjDtSisternaPlan", planDto.getAndjDtSisternaPlan());
    	model.addAttribute("AndjDtBoshqaPlan", planDto.getAndjDtBoshqaPlan());
    	
    	// Itogo planlar depo tamir
    	model.addAttribute("DtHammaPlan", AndjDtHammaPlan + HavDtHammaPlan + SamDtHammaPlan);
    	model.addAttribute("DtKritiPlan", planDto.getAndjDtKritiPlan() + planDto.getHavDtKritiPlan() + planDto.getSamDtKritiPlan());
    	model.addAttribute("DtPlatformaPlan", planDto.getAndjDtPlatformaPlan() + planDto.getHavDtPlatformaPlan() + planDto.getSamDtPlatformaPlan());
    	model.addAttribute("DtPoluvagonPlan",planDto.getAndjDtPoluvagonPlan() + planDto.getHavDtPoluvagonPlan() + planDto.getSamDtPoluvagonPlan());
    	model.addAttribute("DtSisternaPlan", planDto.getAndjDtSisternaPlan() + planDto.getHavDtSisternaPlan() + planDto.getSamDtSisternaPlan());
    	model.addAttribute("DtBoshqaPlan", planDto.getAndjDtBoshqaPlan() + planDto.getHavDtBoshqaPlan() + planDto.getSamDtBoshqaPlan());
    	
    	//VCHD-6 kapital tamir uchun plan
    	int SamKtHammaPlan =  planDto.getSamKtKritiPlan() + planDto.getSamKtPlatformaPlan() + planDto.getSamKtPoluvagonPlan() + planDto.getSamKtSisternaPlan() + planDto.getSamKtBoshqaPlan();
    	model.addAttribute("SamKtHammaPlan",SamKtHammaPlan);
    	model.addAttribute("SamKtKritiPlan", planDto.getSamKtKritiPlan());
    	model.addAttribute("SamKtPlatformaPlan", planDto.getSamKtPlatformaPlan());
    	model.addAttribute("SamKtPoluvagonPlan", planDto.getSamKtPoluvagonPlan());
    	model.addAttribute("SamKtSisternaPlan", planDto.getSamKtSisternaPlan());
    	model.addAttribute("SamKtBoshqaPlan", planDto.getSamKtBoshqaPlan());
    	
    	//havos kapital tamir uchun plan
    	int HavKtHammaPlan = planDto.getHavKtKritiPlan() + planDto.getHavKtPlatformaPlan() + planDto.getHavKtPoluvagonPlan() + planDto.getHavKtSisternaPlan() + planDto.getHavKtBoshqaPlan();
    	model.addAttribute("HavKtHammaPlan", HavKtHammaPlan);
    	model.addAttribute("HavKtKritiPlan", planDto.getHavKtKritiPlan());
    	model.addAttribute("HavKtPlatformaPlan", planDto.getHavKtPlatformaPlan());
    	model.addAttribute("HavKtPoluvagonPlan", planDto.getHavKtPoluvagonPlan());
    	model.addAttribute("HavKtSisternaPlan", planDto.getHavKtSisternaPlan());
    	model.addAttribute("HavKtBoshqaPlan", planDto.getHavKtBoshqaPlan());
    	
    	//VCHD-5 kapital tamir uchun plan
    	int AndjKtHammaPlan = planDto.getAndjKtKritiPlan() + planDto.getAndjKtPlatformaPlan() + planDto.getAndjKtPoluvagonPlan() + planDto.getAndjKtSisternaPlan() + planDto.getAndjKtBoshqaPlan();
    	model.addAttribute("AndjKtHammaPlan", AndjKtHammaPlan);
    	model.addAttribute("AndjKtKritiPlan", planDto.getAndjKtKritiPlan());
    	model.addAttribute("AndjKtPlatformaPlan", planDto.getAndjKtPlatformaPlan());
    	model.addAttribute("AndjKtPoluvagonPlan", planDto.getAndjKtPoluvagonPlan());
    	model.addAttribute("AndjKtSisternaPlan", planDto.getAndjKtSisternaPlan());
    	model.addAttribute("AndjKtBoshqaPlan", planDto.getAndjKtBoshqaPlan());
    	
    	//kapital itogo
    	model.addAttribute("KtHammaPlan", AndjKtHammaPlan + HavKtHammaPlan + SamKtHammaPlan);
    	model.addAttribute("KtKritiPlan", planDto.getAndjKtKritiPlan() + planDto.getHavKtKritiPlan() + planDto.getSamKtKritiPlan());
    	model.addAttribute("KtPlatformaPlan", planDto.getAndjKtPlatformaPlan() + planDto.getHavKtPlatformaPlan() + planDto.getSamKtPlatformaPlan());
    	model.addAttribute("KtPoluvagonPlan",planDto.getAndjKtPoluvagonPlan() + planDto.getHavKtPoluvagonPlan() + planDto.getSamKtPoluvagonPlan());
    	model.addAttribute("KtSisternaPlan", planDto.getAndjKtSisternaPlan() + planDto.getHavKtSisternaPlan() + planDto.getSamKtSisternaPlan());
    	model.addAttribute("KtBoshqaPlan", planDto.getAndjKtBoshqaPlan() + planDto.getHavKtBoshqaPlan() + planDto.getSamKtBoshqaPlan());
  
    	//samarqand KRP plan
    	int SamKrpHammaPlan = planDto.getSamKrpKritiPlan() + planDto.getSamKrpPlatformaPlan() + planDto.getSamKrpPoluvagonPlan() + planDto.getSamKrpSisternaPlan() + planDto.getSamKrpBoshqaPlan();
    	model.addAttribute("SamKrpHammaPlan", SamKrpHammaPlan);
    	model.addAttribute("SamKrpKritiPlan", planDto.getSamKrpKritiPlan());
    	model.addAttribute("SamKrpPlatformaPlan", planDto.getSamKrpPlatformaPlan());
    	model.addAttribute("SamKrpPoluvagonPlan", planDto.getSamKrpPoluvagonPlan());
    	model.addAttribute("SamKrpSisternaPlan", planDto.getSamKrpSisternaPlan());
    	model.addAttribute("SamKrpBoshqaPlan", planDto.getSamKrpBoshqaPlan());
    	
    	//VCHD-3 KRP plan
    	int HavKrpHammaPlan =  planDto.getHavKrpKritiPlan() + planDto.getHavKrpPlatformaPlan() + planDto.getHavKrpPoluvagonPlan() + planDto.getHavKrpSisternaPlan() + planDto.getHavKrpBoshqaPlan();
    	model.addAttribute("HavKrpHammaPlan",HavKrpHammaPlan);
    	model.addAttribute("HavKrpKritiPlan", planDto.getHavKrpKritiPlan());
    	model.addAttribute("HavKrpPlatformaPlan", planDto.getHavKrpPlatformaPlan());
    	model.addAttribute("HavKrpPoluvagonPlan", planDto.getHavKrpPoluvagonPlan());
    	model.addAttribute("HavKrpSisternaPlan", planDto.getHavKrpSisternaPlan());
    	model.addAttribute("HavKrpBoshqaPlan", planDto.getHavKrpBoshqaPlan());
    	
    	//VCHD-5 Krp plan
    	int AndjKrpHammaPlan =  planDto.getAndjKrpKritiPlan() + planDto.getAndjKrpPlatformaPlan() + planDto.getAndjKrpPoluvagonPlan() + planDto.getAndjKrpSisternaPlan() + planDto.getAndjKrpBoshqaPlan();
    	model.addAttribute("AndjKrpHammaPlan",AndjKrpHammaPlan);
    	model.addAttribute("AndjKrpKritiPlan", planDto.getAndjKrpKritiPlan());
    	model.addAttribute("AndjKrpPlatformaPlan", planDto.getAndjKrpPlatformaPlan());
    	model.addAttribute("AndjKrpPoluvagonPlan", planDto.getAndjKrpPoluvagonPlan());
    	model.addAttribute("AndjKrpSisternaPlan", planDto.getAndjKrpSisternaPlan());
    	model.addAttribute("AndjKrpBoshqaPlan", planDto.getAndjKrpBoshqaPlan());
    	
    	//Krp itogo plan
    	model.addAttribute("KrpHammaPlan", AndjKrpHammaPlan + HavKrpHammaPlan + SamKrpHammaPlan);
    	model.addAttribute("KrpKritiPlan", planDto.getAndjKrpKritiPlan() + planDto.getHavKrpKritiPlan() + planDto.getSamKrpKritiPlan());
    	model.addAttribute("KrpPlatformaPlan", planDto.getAndjKrpPlatformaPlan() + planDto.getHavKrpPlatformaPlan() + planDto.getSamKrpPlatformaPlan());
    	model.addAttribute("KrpPoluvagonPlan",planDto.getAndjKrpPoluvagonPlan() + planDto.getHavKrpPoluvagonPlan() + planDto.getSamKrpPoluvagonPlan());
    	model.addAttribute("KrpSisternaPlan", planDto.getAndjKrpSisternaPlan() + planDto.getHavKrpSisternaPlan() + planDto.getSamKrpSisternaPlan());
    	model.addAttribute("KrpBoshqaPlan", planDto.getAndjKrpBoshqaPlan() + planDto.getHavKrpBoshqaPlan() + planDto.getSamKrpBoshqaPlan());
  
 // factlar 
    	//samarqand uchun depli tamir
    	int sdHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) +
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)", true) +
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
    	model.addAttribute("sdHamma",sdHamma);
    	model.addAttribute("sdKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("sdBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
    	
    	//VCHD-3 uchun depli tamir
    	int hdHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)", true) +  
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)", true) +
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
    	model.addAttribute("hdHamma",hdHamma);
    	model.addAttribute("hdKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("hdBoshqaPlan", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
    
    	//VCHD-5 uchun depli tamir
    	int adHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)", true) +  
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) + 
    					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)", true);
    	model.addAttribute("adHamma",adHamma);
    	model.addAttribute("adKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)", true));
    	model.addAttribute("adBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true));
    
    	// itogo Fact uchun depli tamir
    	 int factdhamma = sdHamma + hdHamma + adHamma;
    	 model.addAttribute("factdhamma",factdhamma);
    	 int boshqaPlan = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) +
    			 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true) + 
    			 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)", true);
    	 model.addAttribute("boshqaPlan",boshqaPlan);
    	 
     	//samarqand uchun Kapital tamir
     	int skHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) + 
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)", true) +  
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) + 
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)", true) +
		     			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
     	model.addAttribute("skHamma",skHamma);
     	model.addAttribute("skKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)", true));
     	model.addAttribute("skBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
     	
     	//VCHD-3 uchun kapital tamir
     	int hkHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) + 
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)", true) +  
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) + 
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)", true) +
     					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
     	model.addAttribute("hkHamma",hkHamma);
     	model.addAttribute("hkKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)", true));
     	model.addAttribute("hkBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
    
     	//VCHD-5 uchun kapital tamir
     	int akHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true) + 
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)", true) +  
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true) + 
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)", true) +
					vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
     	model.addAttribute("akHamma",akHamma);
     	model.addAttribute("akKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)", true));
     	model.addAttribute("akBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true));
     
	     // itogo Fact uchun kapital tamir
	   	 int factkhamma = skHamma + hkHamma + akHamma;
	   	 model.addAttribute("factkhamma",factkhamma);
	   	 int boshqaKPlan = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true) +
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true) + 
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)", true);
	   	 			model.addAttribute("boshqaKPlan",boshqaKPlan);
	   	  
    	
      	//samarqand uchun KRP tamir
      	int skrHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)", true) +  
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)", true) +
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true);
      	model.addAttribute("skrHamma",skrHamma);
      	model.addAttribute("skrKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)", true));
      	model.addAttribute("skrPlatforma",vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)", true));
      	model.addAttribute("skrPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)", true));
      	model.addAttribute("skrSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)", true));
      	model.addAttribute("skrBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true));
      	
      	//VCHD-3 uchun KRP
      	int hkrHamma = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)", true) +  
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)", true) +
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true);
      	model.addAttribute("hkrHamma",hkrHamma);
      	model.addAttribute("hkrKriti",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)", true));
      	model.addAttribute("hkrPlatforma",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)", true));
      	model.addAttribute("hkrPoluvagon",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)", true));
      	model.addAttribute("hkrSisterna",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)", true));
      	model.addAttribute("hkrBoshqa",  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true));
     
      	//VCHD-5 uchun KRP
      	int akrHamma =  vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)", true) +  
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)", true) + 
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)", true) +
				vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true);
      	model.addAttribute("akrHamma",akrHamma);
      	model.addAttribute("akrKriti", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)", true));
      	model.addAttribute("akrPlatforma", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)", true));
      	model.addAttribute("akrPoluvagon", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)", true));
      	model.addAttribute("akrSisterna", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)", true));
      	model.addAttribute("akrBoshqa", vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true));
      
 	     // itogo Fact uchun KRP
 	   	 int factkrhamma = skrHamma + hkrHamma + akrHamma;
 	   	 model.addAttribute("factkrhamma",factkrhamma);
 	   	 int boshqaKr = vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)", true) +
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)", true) + 
		 			vagonTayyorService.countAllActiveByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)", true);
	   	 model.addAttribute("boshqaKr",boshqaKr);
		
	   	 return "AllPlanTable";
    }
    
    @PreAuthorize(value = "hasAnyRole('DIRECTOR', 'SAM','HAV','ANDJ')")
   	@GetMapping("/vagons/filterAllMonth")
   	public String filterByDepoNomiForAllMonths(Model model,  @RequestParam(value = "depoNomi", required = false) String depoNomi,
   												@RequestParam(value = "vagonTuri", required = false) String vagonTuri,
   												@RequestParam(value = "country", required = false) String country) {
   		if(!depoNomi.equalsIgnoreCase("Hammasi") && !vagonTuri.equalsIgnoreCase("Hammasi") && !country.equalsIgnoreCase("Hammasi") ) {
   			vagonsToDownload = vagonTayyorService.findByDepoNomiVagonTuriAndCountry(depoNomi, vagonTuri, country);
   			model.addAttribute("vagons", vagonTayyorService.findByDepoNomiVagonTuriAndCountry(depoNomi, vagonTuri, country));
   		}else if(depoNomi.equalsIgnoreCase("Hammasi") && !vagonTuri.equalsIgnoreCase("Hammasi") && !country.equalsIgnoreCase("Hammasi")){
   			vagonsToDownload = vagonTayyorService.findByVagonTuriAndCountry(vagonTuri , country);
   			model.addAttribute("vagons", vagonTayyorService.findByVagonTuriAndCountry(vagonTuri , country));
   		}else if(depoNomi.equalsIgnoreCase("Hammasi") && vagonTuri.equalsIgnoreCase("Hammasi")&& !country.equalsIgnoreCase("Hammasi")){
   			vagonsToDownload = vagonTayyorService.findBycountry(country );
   			model.addAttribute("vagons", vagonTayyorService.findBycountry(country ));
   		}else if(!depoNomi.equalsIgnoreCase("Hammasi") && !vagonTuri.equalsIgnoreCase("Hammasi") && country.equalsIgnoreCase("Hammasi")){
   			vagonsToDownload = vagonTayyorService.findByDepoNomiAndVagonTuri(depoNomi, vagonTuri);
   			model.addAttribute("vagons", vagonTayyorService.findByDepoNomiAndVagonTuri(depoNomi, vagonTuri));
   		}else if(!depoNomi.equalsIgnoreCase("Hammasi") && vagonTuri.equalsIgnoreCase("Hammasi") && !country.equalsIgnoreCase("Hammasi")){
   			vagonsToDownload = vagonTayyorService.findByDepoNomiAndCountry(depoNomi, country);
   			model.addAttribute("vagons", vagonTayyorService.findByDepoNomiAndCountry(depoNomi, country));
   		}else if(depoNomi.equalsIgnoreCase("Hammasi") && !vagonTuri.equalsIgnoreCase("Hammasi") && country.equalsIgnoreCase("Hammasi")){
   			vagonsToDownload = vagonTayyorService.findByVagonTuri(vagonTuri);
   			model.addAttribute("vagons", vagonTayyorService.findByVagonTuri(vagonTuri));
   		}else if(!depoNomi.equalsIgnoreCase("Hammasi") && vagonTuri.equalsIgnoreCase("Hammasi") && country.equalsIgnoreCase("Hammasi")){
   			vagonsToDownload = vagonTayyorService.findByDepoNomi(depoNomi );
   			model.addAttribute("vagons", vagonTayyorService.findByDepoNomi(depoNomi ));
   		}else {
   			vagonsToDownload = vagonTayyorService.findAllBoth( );
   			model.addAttribute("vagons", vagonTayyorService.findAllBoth());
   		}

		PlanDto planDto = vagonTayyorService.getPlanDtoAllMonth();
    	//planlar kiritish
	    
    	//samarqand depo tamir plan
    	sdKriti += planDto.getSamDtKritiPlan();
    	sdPlatforma+=planDto.getSamDtPlatformaPlan();
    	sdPoluvagon+=planDto.getSamDtPoluvagonPlan();
    	sdSisterna+=planDto.getSamDtSisternaPlan();
    	sdBoshqa+=planDto.getSamDtBoshqaPlan();
    	SamDtHammaPlan=sdKriti+sdPlatforma+sdPoluvagon+sdSisterna+sdBoshqa;
    	
    	model.addAttribute("SamDtHammaPlan",SamDtHammaPlan);
    	model.addAttribute("SamDtKritiPlan", sdKriti);
    	model.addAttribute("SamDtPlatformaPlan", sdPlatforma);
    	model.addAttribute("SamDtPoluvagonPlan", sdPoluvagon);
    	model.addAttribute("SamDtSisternaPlan", sdSisterna);
    	model.addAttribute("SamDtBoshqaPlan", sdBoshqa);
    	
    	//havos depo tamir hamma plan
    	hdKriti += planDto.getHavDtKritiPlan();
    	hdPlatforma+=planDto.getHavDtPlatformaPlan();
    	hdPoluvagon+=planDto.getHavDtPoluvagonPlan();
    	hdSisterna+=planDto.getHavDtSisternaPlan();
    	hdBoshqa+=planDto.getHavDtBoshqaPlan();
    	HavDtHammaPlan = hdKriti + hdPlatforma + hdPoluvagon + hdSisterna + hdBoshqa;
    	
    	model.addAttribute("HavDtHammaPlan", HavDtHammaPlan);
    	model.addAttribute("HavDtKritiPlan", hdKriti);
    	model.addAttribute("HavDtPlatformaPlan", hdPlatforma);
    	model.addAttribute("HavDtPoluvagonPlan", hdPoluvagon);
    	model.addAttribute("HavDtSisternaPlan", hdSisterna);
    	model.addAttribute("HavDtBoshqaPlan", hdBoshqa);
    	
    	//VCHD-5 depo tamir plan
    	adKriti += planDto.getAndjDtKritiPlan();
    	adPlatforma+=planDto.getAndjDtPlatformaPlan();
    	adPoluvagon+=planDto.getAndjDtPoluvagonPlan();
    	adSisterna+=planDto.getAndjDtSisternaPlan();
    	adBoshqa+=planDto.getAndjDtBoshqaPlan();
    	AndjDtHammaPlan = adKriti + adPlatforma + adPoluvagon + adSisterna + adBoshqa;
    	
    	model.addAttribute("AndjDtHammaPlan", AndjDtHammaPlan);
    	model.addAttribute("AndjDtKritiPlan", adKriti);
    	model.addAttribute("AndjDtPlatformaPlan",adPlatforma);
    	model.addAttribute("AndjDtPoluvagonPlan", adPoluvagon);
    	model.addAttribute("AndjDtSisternaPlan", adSisterna);
    	model.addAttribute("AndjDtBoshqaPlan", adBoshqa);
    	
    	// Itogo planlar depo tamir
    	model.addAttribute("DtHammaPlan", AndjDtHammaPlan + HavDtHammaPlan + SamDtHammaPlan);
    	model.addAttribute("DtKritiPlan", sdKriti + hdKriti + adKriti);
    	model.addAttribute("DtPlatformaPlan", sdPlatforma + hdPlatforma + adPlatforma);
    	model.addAttribute("DtPoluvagonPlan",sdPoluvagon + hdPoluvagon + adPoluvagon);
    	model.addAttribute("DtSisternaPlan", sdSisterna + hdSisterna + adSisterna);
    	model.addAttribute("DtBoshqaPlan", sdBoshqa + hdBoshqa + adBoshqa);
    	
    	//Samrqand kapital plan
    	skKriti += planDto.getSamKtKritiPlan();
    	skPlatforma+=planDto.getSamKtPlatformaPlan();
    	skPoluvagon+=planDto.getSamKtPoluvagonPlan();
    	skSisterna+=planDto.getSamKtSisternaPlan();
    	skBoshqa+=planDto.getSamKtBoshqaPlan();
    	SamKtHammaPlan=skKriti+skPlatforma+skPoluvagon+skSisterna+skBoshqa;
    	
    	model.addAttribute("SamKtHammaPlan",SamKtHammaPlan);
    	model.addAttribute("SamKtKritiPlan", skKriti);
    	model.addAttribute("SamKtPlatformaPlan", skPlatforma);
    	model.addAttribute("SamKtPoluvagonPlan", skPoluvagon);
    	model.addAttribute("SamKtSisternaPlan", skSisterna);
    	model.addAttribute("SamKtBoshqaPlan", skBoshqa);
    	
    	//hovos kapital plan
    	hkKriti += planDto.getHavKtKritiPlan();
    	hkPlatforma+=planDto.getHavKtPlatformaPlan();
    	hkPoluvagon+=planDto.getHavKtPoluvagonPlan();
    	hkSisterna+=planDto.getHavKtSisternaPlan();
    	hkBoshqa+=planDto.getHavKtBoshqaPlan();
    	HavKtHammaPlan = hkKriti + hkPlatforma + hkPoluvagon + hkSisterna + hkBoshqa;
    	
    	model.addAttribute("HavKtHammaPlan", HavKtHammaPlan);
    	model.addAttribute("HavKtKritiPlan", hkKriti);
    	model.addAttribute("HavKtPlatformaPlan", hkPlatforma);
    	model.addAttribute("HavKtPoluvagonPlan", hkPoluvagon);
    	model.addAttribute("HavKtSisternaPlan", hkSisterna);
    	model.addAttribute("HavKtBoshqaPlan", hkBoshqa);
    	
    	//ANDIJON kapital plan
    	akKriti += planDto.getAndjKtKritiPlan();
    	akPlatforma+=planDto.getAndjKtPlatformaPlan();
    	akPoluvagon+=planDto.getAndjKtPoluvagonPlan();
    	akSisterna+=planDto.getAndjKtSisternaPlan();
    	akBoshqa+=planDto.getAndjKtBoshqaPlan();
    	AndjKtHammaPlan = akKriti + akPlatforma + akPoluvagon + akSisterna + akBoshqa;
    	
    	
    	model.addAttribute("AndjKtHammaPlan", AndjKtHammaPlan);
    	model.addAttribute("AndjKtKritiPlan", akKriti);
    	model.addAttribute("AndjKtPlatformaPlan", akPlatforma);
    	model.addAttribute("AndjKtPoluvagonPlan", akPoluvagon);
    	model.addAttribute("AndjKtSisternaPlan", akSisterna);
    	model.addAttribute("AndjKtBoshqaPlan", akBoshqa);
    	
    	//Itogo kapital plan
    	model.addAttribute("KtHammaPlan", AndjKtHammaPlan + HavKtHammaPlan + SamKtHammaPlan);
    	model.addAttribute("KtKritiPlan", skKriti + hkKriti + akKriti);
    	model.addAttribute("KtPlatformaPlan", skPlatforma + hkPlatforma + akPlatforma);
    	model.addAttribute("KtPoluvagonPlan",skPoluvagon + hkPoluvagon + akPoluvagon);
    	model.addAttribute("KtSisternaPlan", skSisterna + hkSisterna + akSisterna);
    	model.addAttribute("KtBoshqaPlan", skBoshqa + hkBoshqa + akBoshqa);
  
    	//Samarqankr Krp plan
    	skrKriti += planDto.getSamKrpKritiPlan();
    	skrPlatforma+=planDto.getSamKrpPlatformaPlan();
    	skrPoluvagon+=planDto.getSamKrpPoluvagonPlan();
    	skrSisterna+=planDto.getSamKrpSisternaPlan();
    	skrBoshqa+=planDto.getSamKrpBoshqaPlan();
    	SamKrpHammaPlan=skrKriti+skrPlatforma+skrPoluvagon+skrSisterna+skrBoshqa;
    	
    	model.addAttribute("SamKrpHammaPlan", SamKrpHammaPlan);
    	model.addAttribute("SamKrpKritiPlan", skrKriti);
    	model.addAttribute("SamKrpPlatformaPlan", skrPlatforma);
    	model.addAttribute("SamKrpPoluvagonPlan", skrPoluvagon);
    	model.addAttribute("SamKrpSisternaPlan", skrSisterna);
    	model.addAttribute("SamKrpBoshqaPlan", skrBoshqa);
    	
    	//Hovos krp plan
    	hkrKriti += planDto.getHavKrpKritiPlan();
    	hkrPlatforma+=planDto.getHavKrpPlatformaPlan();
    	hkrPoluvagon+=planDto.getHavKrpPoluvagonPlan();
    	hkrSisterna+=planDto.getHavKrpSisternaPlan();
    	hkrBoshqa+=planDto.getHavKrpBoshqaPlan();
    	HavKrpHammaPlan = hkrKriti + hkrPlatforma + hkrPoluvagon + hkrSisterna + hkrBoshqa;
    	
    	model.addAttribute("HavKrpHammaPlan",HavKrpHammaPlan);
    	model.addAttribute("HavKrpKritiPlan", hkrKriti);
    	model.addAttribute("HavKrpPlatformaPlan", hkrPlatforma);
    	model.addAttribute("HavKrpPoluvagonPlan", hkrPoluvagon);
    	model.addAttribute("HavKrpSisternaPlan", hkrSisterna);
    	model.addAttribute("HavKrpBoshqaPlan", hkrBoshqa);
    	
    	//andijon krp plan
    	akrKriti += planDto.getAndjKrpKritiPlan();
    	akrPlatforma+=planDto.getAndjKrpPlatformaPlan();
    	akrPoluvagon+=planDto.getAndjKrpPoluvagonPlan();
    	akrSisterna+=planDto.getAndjKrpSisternaPlan();
    	akrBoshqa+=planDto.getAndjKrpBoshqaPlan();
    	AndjKrpHammaPlan = akrKriti + akrPlatforma + akrPoluvagon + akrSisterna + akrBoshqa;
    	
    	model.addAttribute("AndjKrpHammaPlan",AndjKrpHammaPlan);
    	model.addAttribute("AndjKrpKritiPlan", akrKriti);
    	model.addAttribute("AndjKrpPlatformaPlan", akrPlatforma);
    	model.addAttribute("AndjKrpPoluvagonPlan", akrPoluvagon);
    	model.addAttribute("AndjKrpSisternaPlan", akrSisterna);
    	model.addAttribute("AndjKrpBoshqaPlan", akrBoshqa);
    	
    	//itogo krp
    	model.addAttribute("KrpHammaPlan", AndjKrpHammaPlan + HavKrpHammaPlan + SamKrpHammaPlan);
    	model.addAttribute("KrpKritiPlan", skrKriti + hkrKriti + akrKriti);
    	model.addAttribute("KrpPlatformaPlan", skrPlatforma + hkrPlatforma + akrPlatforma);
    	model.addAttribute("KrpPoluvagonPlan",akrPoluvagon + hkrPoluvagon + skrPoluvagon);
    	model.addAttribute("KrpSisternaPlan", skrSisterna + hkrSisterna + akrSisterna);
    	model.addAttribute("KrpBoshqaPlan", skrBoshqa + hkrBoshqa + akrBoshqa);
    	
//    	planDto=planDto0;
    	vagonTayyorService.makePlanNul();

    	//**//
    	// samarqand depo tamir hamma false vagonlar soni
    	int sdKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
    	int sdPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Depoli ta’mir(ДР)");
    	int sdPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
    	int sdSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Depoli ta’mir(ДР)");
    	int sdBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
    	int sdHammaFalse = sdKritiFalse + sdPlatformaFalse+ sdPoluvagonFalse+ sdSisternaFalse + sdBoshqaFalse;
    	
    	model.addAttribute("sdHammaFalse",sdHammaFalse);
    	model.addAttribute("sdKritiFalse",sdKritiFalse);
    	model.addAttribute("sdPlatformaFalse",sdPlatformaFalse);
    	model.addAttribute("sdPoluvagonFalse",sdPoluvagonFalse);
    	model.addAttribute("sdSisternaFalse",sdSisternaFalse);
    	model.addAttribute("sdBoshqaFalse",sdBoshqaFalse);
    	
    	// VCHD-3 depo tamir hamma false vagonlar soni
    	int hdKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
    	int hdPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Depoli ta’mir(ДР)");
    	int hdPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
    	int hdSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Depoli ta’mir(ДР)");
    	int hdBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
    	int hdHammaFalse = hdKritiFalse + hdPlatformaFalse+ hdPoluvagonFalse+ hdSisternaFalse + hdBoshqaFalse;
    	
    	model.addAttribute("hdHammaFalse",hdHammaFalse);
    	model.addAttribute("hdKritiFalse",hdKritiFalse);
    	model.addAttribute("hdPlatformaFalse",hdPlatformaFalse);
    	model.addAttribute("hdPoluvagonFalse",hdPoluvagonFalse);
    	model.addAttribute("hdSisternaFalse",hdSisternaFalse);
    	model.addAttribute("hdBoshqaFalse",hdBoshqaFalse);
    	
    	// VCHD-5 depo tamir hamma false vagonlar soni
    	int adKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Depoli ta’mir(ДР)");
    	int adPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Depoli ta’mir(ДР)");
    	int adPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Depoli ta’mir(ДР)");
    	int adSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Depoli ta’mir(ДР)");
    	int adBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Depoli ta’mir(ДР)");
    	int adHammaFalse = adKritiFalse + adPlatformaFalse+ adPoluvagonFalse+ adSisternaFalse + adBoshqaFalse;
    	
    	model.addAttribute("adHammaFalse",adHammaFalse);
    	model.addAttribute("adKritiFalse",adKritiFalse);
    	model.addAttribute("adPlatformaFalse",adPlatformaFalse);
    	model.addAttribute("adPoluvagonFalse",adPoluvagonFalse);
    	model.addAttribute("adSisternaFalse",adSisternaFalse);
    	model.addAttribute("adBoshqaFalse",adBoshqaFalse);
    	
    	// depoli tamir itogo uchun
    	int dHammaFalse =  adHammaFalse + hdHammaFalse+sdHammaFalse;
    	int dKritiFalse = sdKritiFalse + hdKritiFalse + adKritiFalse;
    	int dPlatforma = adPlatformaFalse + sdPlatformaFalse + hdPlatformaFalse;
    	int dPoluvagon  = adPoluvagonFalse + sdPoluvagonFalse + hdPoluvagonFalse;
    	int dSisterna = adSisternaFalse + hdSisternaFalse + sdSisternaFalse;
    	int dBoshqa = adBoshqaFalse + hdBoshqaFalse + sdBoshqaFalse;
    	
    	model.addAttribute("dHammaFalse",dHammaFalse);
    	model.addAttribute("dKritiFalse",dKritiFalse);
    	model.addAttribute("dPlatforma",dPlatforma);
    	model.addAttribute("dPoluvagon",dPoluvagon);
    	model.addAttribute("dSisterna",dSisterna);
    	model.addAttribute("dBoshqa",dBoshqa);
    	
    	
    	// samarqand KApital tamir hamma false vagonlar soni
    	int skKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
    	int skPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","Kapital ta’mir(КР)");
    	int skPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
    	int skSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","Kapital ta’mir(КР)");
    	int skBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
    	int skHammaFalse = skKritiFalse + skPlatformaFalse+ skPoluvagonFalse+ skSisternaFalse + skBoshqaFalse;
    	
    	model.addAttribute("skHammaFalse",skHammaFalse);
    	model.addAttribute("skKritiFalse",skKritiFalse);
    	model.addAttribute("skPlatformaFalse",skPlatformaFalse);
    	model.addAttribute("skPoluvagonFalse",skPoluvagonFalse);
    	model.addAttribute("skSisternaFalse",skSisternaFalse);
    	model.addAttribute("skBoshqaFalse",skBoshqaFalse);
    	
    	// VCHD-3 kapital tamir hamma false vagonlar soni
    	int hkKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
    	int hkPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","Kapital ta’mir(КР)");
    	int hkPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
    	int hkSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","Kapital ta’mir(КР)");
    	int hkBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
    	int hkHammaFalse = hkKritiFalse + hkPlatformaFalse+ hkPoluvagonFalse+ hkSisternaFalse + hkBoshqaFalse;
    	
    	model.addAttribute("hkHammaFalse",hkHammaFalse);
    	model.addAttribute("hkKritiFalse",hkKritiFalse);
    	model.addAttribute("hkPlatformaFalse",hkPlatformaFalse);
    	model.addAttribute("hkPoluvagonFalse",hkPoluvagonFalse);
    	model.addAttribute("hkSisternaFalse",hkSisternaFalse);
    	model.addAttribute("hkBoshqaFalse",hkBoshqaFalse);
    	
    	// VCHD-5 kapital tamir hamma false vagonlar soni
    	int akKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","Kapital ta’mir(КР)");
    	int akPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","Kapital ta’mir(КР)");
    	int akPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","Kapital ta’mir(КР)");
    	int akSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","Kapital ta’mir(КР)");
    	int akBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","Kapital ta’mir(КР)");
    	int akHammaFalse = akKritiFalse + akPlatformaFalse+ akPoluvagonFalse+ akSisternaFalse + akBoshqaFalse;
    	
    	model.addAttribute("akHammaFalse",akHammaFalse);
    	model.addAttribute("akKritiFalse",akKritiFalse);
    	model.addAttribute("akPlatformaFalse",akPlatformaFalse);
    	model.addAttribute("akPoluvagonFalse",akPoluvagonFalse);
    	model.addAttribute("akSisternaFalse",akSisternaFalse);
    	model.addAttribute("akBoshqaFalse",akBoshqaFalse);
    	
    	// Kapital tamir itogo uchun
    	int kHammaFalse =  akHammaFalse + hkHammaFalse+skHammaFalse;
    	int kKritiFalse = skKritiFalse + hkKritiFalse + akKritiFalse;
    	int kPlatforma = akPlatformaFalse + skPlatformaFalse + hkPlatformaFalse;
    	int kPoluvagon  = akPoluvagonFalse + skPoluvagonFalse + hkPoluvagonFalse;
    	int kSisterna = akSisternaFalse + hkSisternaFalse + skSisternaFalse;
    	int kBoshqa = akBoshqaFalse + hkBoshqaFalse + skBoshqaFalse;
    	
    	model.addAttribute("kHammaFalse",kHammaFalse);
    	model.addAttribute("kKritiFalse",kKritiFalse);
    	model.addAttribute("kPlatforma",kPlatforma);
    	model.addAttribute("kPoluvagon",kPoluvagon);
    	model.addAttribute("kSisterna",kSisterna);
    	model.addAttribute("kBoshqa",kBoshqa);
    	
    	//**
    	// samarqand KRP tamir hamma false vagonlar soni
    	int skrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yopiq vagon (крыт)","KRP(КРП)");
    	int skrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Platforma(пф)","KRP(КРП)");
    	int skrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Yarim ochiq vagon(пв)","KRP(КРП)");
    	int skrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Sisterna(цс)","KRP(КРП)");
    	int skrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-6","Boshqa turdagi(проч)","KRP(КРП)");
    	int skrHammaFalse = skrKritiFalse + skrPlatformaFalse+ skrPoluvagonFalse+ skrSisternaFalse + skrBoshqaFalse;
    	
    	model.addAttribute("skrHammaFalse",skrHammaFalse);
    	model.addAttribute("skrKritiFalse",skrKritiFalse);
    	model.addAttribute("skrPlatformaFalse",skrPlatformaFalse);
    	model.addAttribute("skrPoluvagonFalse",skrPoluvagonFalse);
    	model.addAttribute("skrSisternaFalse",skrSisternaFalse);
    	model.addAttribute("skrBoshqaFalse",skrBoshqaFalse);
    	
    	// VCHD-3 KRP tamir hamma false vagonlar soni
    	int hkrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yopiq vagon (крыт)","KRP(КРП)");
    	int hkrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Platforma(пф)","KRP(КРП)");
    	int hkrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Yarim ochiq vagon(пв)","KRP(КРП)");
    	int hkrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Sisterna(цс)","KRP(КРП)");
    	int hkrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-3","Boshqa turdagi(проч)","KRP(КРП)");
    	int hkrHammaFalse = hkrKritiFalse + hkrPlatformaFalse+ hkrPoluvagonFalse+ hkrSisternaFalse + hkrBoshqaFalse;
    	
    	model.addAttribute("hkrHammaFalse",hkrHammaFalse);
    	model.addAttribute("hkrKritiFalse",hkrKritiFalse);
    	model.addAttribute("hkrPlatformaFalse",hkrPlatformaFalse);
    	model.addAttribute("hkrPoluvagonFalse",hkrPoluvagonFalse);
    	model.addAttribute("hkrSisternaFalse",hkrSisternaFalse);
    	model.addAttribute("hkrBoshqaFalse",hkrBoshqaFalse);
    	
    	// VCHD-5 KRP tamir hamma false vagonlar soni
    	int akrKritiFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yopiq vagon (крыт)","KRP(КРП)");
    	int akrPlatformaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Platforma(пф)","KRP(КРП)");
    	int akrPoluvagonFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Yarim ochiq vagon(пв)","KRP(КРП)");
    	int akrSisternaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Sisterna(цс)","KRP(КРП)");
    	int akrBoshqaFalse=vagonTayyorService.countByDepoNomiVagonTuriAndTamirTuri("VCHD-5","Boshqa turdagi(проч)","KRP(КРП)");
    	int akrHammaFalse = akrKritiFalse + akrPlatformaFalse+ akrPoluvagonFalse+ akrSisternaFalse + akrBoshqaFalse;
    	
    	model.addAttribute("akrHammaFalse",akrHammaFalse);
    	model.addAttribute("akrKritiFalse",akrKritiFalse);
    	model.addAttribute("akrPlatformaFalse",akrPlatformaFalse);
    	model.addAttribute("akrPoluvagonFalse",akrPoluvagonFalse);
    	model.addAttribute("akrSisternaFalse",akrSisternaFalse);
    	model.addAttribute("akBoshqaFalse",akBoshqaFalse);
    	
    	// Krp itogo uchun
    	int krHammaFalse =  akrHammaFalse + hkrHammaFalse+skrHammaFalse;
    	int krKritiFalse = skrKritiFalse + hkrKritiFalse + akrKritiFalse;
    	int krPlatforma = akrPlatformaFalse + skrPlatformaFalse + hkrPlatformaFalse;
    	int krPoluvagon  = akrPoluvagonFalse + skrPoluvagonFalse + hkrPoluvagonFalse;
    	int krSisterna = akrSisternaFalse + hkrSisternaFalse + skrSisternaFalse;
    	int krBoshqa = akrBoshqaFalse + hkrBoshqaFalse + skrBoshqaFalse;
    	
    	model.addAttribute("krHammaFalse",krHammaFalse);
    	model.addAttribute("krKritiFalse",krKritiFalse);
    	model.addAttribute("krPlatforma",krPlatforma);
    	model.addAttribute("krPoluvagon",krPoluvagon);
    	model.addAttribute("krSisterna",krSisterna);
    	model.addAttribute("krBoshqa",krBoshqa);
    	
    	return "planTableForMonths";
    }
	
}
