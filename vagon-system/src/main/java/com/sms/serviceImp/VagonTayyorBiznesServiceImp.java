package com.sms.serviceImp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.property.TextAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.sms.dto.PlanDto;
import com.sms.model.VagonModel;
import com.sms.model.VagonTayyor;
import com.sms.repository.VagonTayyorBiznesRepository;
import com.sms.service.VagonTayyorBiznesService;

@Service
public class VagonTayyorBiznesServiceImp implements VagonTayyorBiznesService{

	@Autowired
	private VagonTayyorBiznesRepository vagonTayyorRepository;
	
	LocalDateTime today = LocalDateTime.now();
	LocalDateTime minusHours = today.plusHours(5);
	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String currentDate = minusHours.format(myFormatObj);
	String samDate ;
	String havDate ;
	String andjDate ;
	
	public void createPdf(List<VagonTayyor> vagons, HttpServletResponse response) throws IOException {

		String home = System.getProperty("user.home");
		  File file = new File(home + "/Downloads" + "/Biznes reja boyicha tamir malumot.pdf");
		  if (!file.getParentFile().exists())
		      file.getParentFile().mkdirs();
		  if (!file.exists())
		      file.createNewFile();

		List<VagonTayyor> allVagons = vagons;
		try {
			response.setHeader("Content-Disposition",
                    "attachment;fileName=\"" + "Biznes reja boyicha tamir malumot.pdf" +"\"");
			response.setContentType("application/pdf");
			
			PdfWriter writer = new PdfWriter(file.getAbsolutePath());
			PdfDocument pdfDoc = new PdfDocument(writer);
			Document doc = new Document(pdfDoc);

			String FONT_FILENAME = "./src/main/resources/arial.ttf";
			PdfFont font = PdfFontFactory.createFont(FONT_FILENAME, PdfEncodings.IDENTITY_H);
			doc.setFont(font);

			Paragraph paragraph = new Paragraph("Ta'mirdan chiqgan vagonlar(Biznes reja bo'yicha)");
			paragraph.setBackgroundColor(Color.DARK_GRAY);
			paragraph.setFontColor(Color.WHITE);// Setting background color to cell1
			paragraph.setBorder(Border.NO_BORDER);            // Setting border to cell1
			paragraph.setTextAlignment(TextAlignment.CENTER); // Setting text alignment to cell1
			paragraph.setFontSize(16);

			float[] columnWidth = {30f,200f,200f, 200f,200f,200f,200f,200f,200f,200f};
			Table table = new Table(columnWidth);
			table.setTextAlignment(TextAlignment.CENTER); // Setting text alignment to cell1
			table.addCell(new Cell().add(" № "));
			table.addCell(new Cell().add("Nomeri"));
			table.addCell(new Cell().add("Vagon turi"));
			table.addCell(new Cell().add("VCHD"));
			table.addCell(new Cell().add("Ta'mir turi"));
			table.addCell(new Cell().add("Ishlab chiqarilgan yili"));
			table.addCell(new Cell().add("Ta'mirdan chiqgan vaqti"));
			table.addCell(new Cell().add("Saqlangan vaqti"));
			table.addCell(new Cell().add("Egasi"));
			table.addCell(new Cell().add("Izoh"));
			int i=0;
			for(VagonTayyor vagon:allVagons) {
				i++;
				table.addCell(new Cell().add(String.valueOf(i)));
				table.addCell(new Cell().add(String.valueOf(vagon.getNomer())));
				table.addCell(new Cell().add(vagon.getVagonTuri()));
				table.addCell(new Cell().add(vagon.getDepoNomi()));
				table.addCell(new Cell().add(vagon.getRemontTuri()));
				table.addCell(new Cell().add(String.valueOf(vagon.getIshlabChiqarilganYili())));
				table.addCell(new Cell().add(String.valueOf(vagon.getChiqganVaqti())));
				table.addCell(new Cell().add(String.valueOf(vagon.getCreatedDate())));
				table.addCell(new Cell().add(vagon.getCountry()));
				table.addCell(new Cell().add(vagon.getIzoh()));
			}

			doc.add(paragraph);
			doc.add(table);
			doc.close();
			FileInputStream in = new FileInputStream(file.getAbsoluteFile());
			FileCopyUtils.copy(in, response.getOutputStream());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getSamDate() {
		return samDate;
	}

	public String getHavDate() {
		return havDate;
	}

	public String getAndjDate() {
		return andjDate;
	}


	
	
	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	// bosh admin qoshadi
	@Override
	public VagonTayyor saveVagon(VagonTayyor vagon) {	
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		Optional<VagonTayyor> exist=vagonTayyorRepository.findByNomer(vagon.getNomer());
		if(exist.isPresent())
			return new VagonTayyor();
		VagonTayyor savedVagon = new VagonTayyor();
		savedVagon.setNomer(vagon.getNomer());
		savedVagon.setDepoNomi(vagon.getDepoNomi());
		savedVagon.setRemontTuri(vagon.getRemontTuri());
		savedVagon.setVagonTuri(vagon.getVagonTuri());
		savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		savedVagon.setIzoh(vagon.getIzoh());
		savedVagon.setCountry(vagon.getCountry());
		savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		savedVagon.setActive(true);

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime minusHours = today.plusHours(5);
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String currentDate = minusHours.format(myFormatObj);

		savedVagon.setCreatedDate(currentDate);
	

		return vagonTayyorRepository.save(savedVagon);	
	}
	@Override
	public VagonTayyor saveVagonSam(VagonTayyor vagon) {
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		Optional<VagonTayyor> exist=	vagonTayyorRepository.findByNomer(vagon.getNomer());
		if(exist.isPresent() || !vagon.getDepoNomi().equalsIgnoreCase("VCHD-6"))
			return new VagonTayyor();
		VagonTayyor savedVagon = new VagonTayyor();
		savedVagon.setNomer(vagon.getNomer());
		savedVagon.setDepoNomi(vagon.getDepoNomi());
		savedVagon.setRemontTuri(vagon.getRemontTuri());
		savedVagon.setVagonTuri(vagon.getVagonTuri());
		savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		savedVagon.setIzoh(vagon.getIzoh());
		savedVagon.setCountry(vagon.getCountry());
		savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		savedVagon.setActive(true);
		
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime minusHours = today.plusHours(5);
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		samDate = minusHours.format(myFormatObj);

		savedVagon.setCreatedDate(samDate);
	
		return vagonTayyorRepository.save(savedVagon);	
	}
	
	@Override
	public VagonTayyor saveVagonHav(VagonTayyor vagon) {
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		Optional<VagonTayyor> exist=vagonTayyorRepository.findByNomer(vagon.getNomer());
		if(exist.isPresent() || !vagon.getDepoNomi().equalsIgnoreCase("VCHD-3"))
			return new VagonTayyor();
		VagonTayyor savedVagon = new VagonTayyor();
		savedVagon.setNomer(vagon.getNomer());
		savedVagon.setDepoNomi(vagon.getDepoNomi());
		savedVagon.setRemontTuri(vagon.getRemontTuri());
		savedVagon.setVagonTuri(vagon.getVagonTuri());
		savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		savedVagon.setIzoh(vagon.getIzoh());
		savedVagon.setCountry(vagon.getCountry());
		savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		savedVagon.setActive(true);

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime minusHours = today.plusHours(5);
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		havDate = minusHours.format(myFormatObj);

		savedVagon.setCreatedDate(havDate);

		return vagonTayyorRepository.save(savedVagon);	
	}

	@Override
	public VagonTayyor saveVagonAndj(VagonTayyor vagon) {
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		Optional<VagonTayyor> exist= vagonTayyorRepository.findByNomer(vagon.getNomer());
		if(exist.isPresent() || !vagon.getDepoNomi().equalsIgnoreCase("VCHD-5"))
			return new VagonTayyor();
		VagonTayyor savedVagon = new VagonTayyor();
		savedVagon.setNomer(vagon.getNomer());
		savedVagon.setDepoNomi(vagon.getDepoNomi());
		savedVagon.setRemontTuri(vagon.getRemontTuri());
		savedVagon.setVagonTuri(vagon.getVagonTuri());
		savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		savedVagon.setIzoh(vagon.getIzoh());
		savedVagon.setCountry(vagon.getCountry());
		savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		savedVagon.setActive(true);

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime minusHours = today.plusHours(5);
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		andjDate = minusHours.format(myFormatObj);

		savedVagon.setCreatedDate(andjDate);

		return vagonTayyorRepository.save(savedVagon);
}

	@Override
	public VagonTayyor updateVagon(VagonTayyor vagon, long id) {	
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		 Optional<VagonTayyor> exist = vagonTayyorRepository.findById(id);
		 if(!exist.isPresent())
			 return new VagonTayyor();
		 VagonTayyor savedVagon = exist.get();
		 savedVagon.setId(id);
		 savedVagon.setNomer(vagon.getNomer());
		 savedVagon.setVagonTuri(vagon.getVagonTuri());
		 savedVagon.setDepoNomi(vagon.getDepoNomi());
		 savedVagon.setRemontTuri(vagon.getRemontTuri());
		 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		 savedVagon.setIzoh(vagon.getIzoh());
		 savedVagon.setCountry(vagon.getCountry());
		 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		
		 
		 return vagonTayyorRepository.save(savedVagon);
	}
	
	@Override
	public VagonTayyor updateVagonSam(VagonTayyor vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		 Optional<VagonTayyor> exist = vagonTayyorRepository.findById(id);
		 if(exist.get().getDepoNomi().equalsIgnoreCase("VCHD-6") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-6")) {
			 VagonTayyor savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
			 LocalDateTime today = LocalDateTime.now();
			 LocalDateTime minusHours = today.plusHours(5);
			 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			 samDate = minusHours.format(myFormatObj);
			 return vagonTayyorRepository.save(savedVagon);
		 }else
			return new VagonTayyor();

	}

	@Override
	public VagonTayyor updateVagonHav(VagonTayyor vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		 Optional<VagonTayyor> exist = vagonTayyorRepository.findById(id);
		 if(exist.get().getDepoNomi().equalsIgnoreCase("VCHD-3") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-3")) {
			 
			 VagonTayyor savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
			 LocalDateTime today = LocalDateTime.now();
			 LocalDateTime minusHours = today.plusHours(5);
			 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			 havDate = minusHours.format(myFormatObj);
			 return vagonTayyorRepository.save(savedVagon);
		 }else
			 return new VagonTayyor();
	}

	@Override
	public VagonTayyor updateVagonAndj(VagonTayyor vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		 Optional<VagonTayyor> exist = vagonTayyorRepository.findById(id);
		 if( exist.get().getDepoNomi().equalsIgnoreCase("VCHD-5") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-5")){	
			 VagonTayyor savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
			 LocalDateTime today = LocalDateTime.now();
			 LocalDateTime minusHours = today.plusHours(5);
			 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			 andjDate = minusHours.format(myFormatObj);
			 return vagonTayyorRepository.save(savedVagon);
		}else {
				 return new VagonTayyor();
		}
	}
	
	//hamma oy uchun
	@Override
	public VagonTayyor updateVagonMonths(VagonTayyor vagon, long id) {	
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		 Optional<VagonTayyor> exist = vagonTayyorRepository.findById(id);
		 if(!exist.isPresent())
			 return new VagonTayyor();
		 VagonTayyor savedVagon = exist.get();
		 savedVagon.setId(id);
		 savedVagon.setNomer(vagon.getNomer());
		 savedVagon.setVagonTuri(vagon.getVagonTuri());
		 savedVagon.setDepoNomi(vagon.getDepoNomi());
		 savedVagon.setRemontTuri(vagon.getRemontTuri());
		 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		 savedVagon.setIzoh(vagon.getIzoh());
		 savedVagon.setCountry(vagon.getCountry());
		 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		
		 
		 return vagonTayyorRepository.save(savedVagon);
	}
	
	@Override
	public VagonTayyor updateVagonSamMonths(VagonTayyor vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		 Optional<VagonTayyor> exist = vagonTayyorRepository.findById(id);
		 if(exist.get().getDepoNomi().equalsIgnoreCase("VCHD-6") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-6")) {
			 VagonTayyor savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());

			 return vagonTayyorRepository.save(savedVagon);
		 }else
			return new VagonTayyor();

	}

	@Override
	public VagonTayyor updateVagonHavMonths(VagonTayyor vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		 Optional<VagonTayyor> exist = vagonTayyorRepository.findById(id);
		 if(exist.get().getDepoNomi().equalsIgnoreCase("VCHD-3") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-3")) {
			 
			 VagonTayyor savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());

			 return vagonTayyorRepository.save(savedVagon);
		 }else
			 return new VagonTayyor();
	}

	@Override
	public VagonTayyor updateVagonAndjMonths(VagonTayyor vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyor();
		 Optional<VagonTayyor> exist = vagonTayyorRepository.findById(id);
		 if( exist.get().getDepoNomi().equalsIgnoreCase("VCHD-5") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-5")){	
			 VagonTayyor savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());

			 return vagonTayyorRepository.save(savedVagon);
		}else {
				 return new VagonTayyor();
		}
	}


	@Override
	public VagonTayyor getVagonById(long id) {
	Optional<VagonTayyor> exist=	vagonTayyorRepository.findById(id);
	if(!exist.isPresent())
		return new VagonTayyor();
	return exist.get();
	}

	@Override
	public void deleteVagonById(long id) throws NotFoundException {
		Optional<VagonTayyor> exist=	vagonTayyorRepository.findById(id);
		if(exist.isPresent()) 
			vagonTayyorRepository.deleteById(id);

	}

	@Override
	public void deleteVagonSam(long id) throws NotFoundException {
		VagonTayyor exist=	vagonTayyorRepository.findById(id).get();	
		if(exist.getDepoNomi().equals("VCHD-6") ) {
			vagonTayyorRepository.deleteById(id);
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime minusHours = today.plusHours(5);
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			samDate = minusHours.format(myFormatObj);
		}
	}

	@Override
	public void deleteVagonHav(long id) throws NotFoundException{
		VagonTayyor exist=	vagonTayyorRepository.findById(id).get();	
		if(exist.getDepoNomi().equals("VCHD-3") ) {
			vagonTayyorRepository.deleteById(id);
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime minusHours = today.plusHours(5);
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			havDate = minusHours.format(myFormatObj);
		}
	}

	@Override
	public void deleteVagonAndj(long id) throws NotFoundException{
		VagonTayyor exist=	vagonTayyorRepository.findById(id).get();	
		if(exist.getDepoNomi().equals("VCHD-5") ) {
			vagonTayyorRepository.deleteById(id);
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime minusHours = today.plusHours(5);
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			andjDate = minusHours.format(myFormatObj);
		}
	}
	
	@Override
	public int countByDepoNomiVagonTuriAndTamirTuri(String depoNomi, String vagonTuri, String tamirTuri) {
		return vagonTayyorRepository.countByDepoNomiVagonTuriAndTamirTuri(depoNomi, vagonTuri, tamirTuri);
	}

	@Override
	public List<VagonTayyor> findAllBoth() {	
		return vagonTayyorRepository.findAll();
	}
	@Override
	public List<VagonTayyor> findAllActive() {
		List<VagonTayyor> all= vagonTayyorRepository.findAll();
		List<VagonTayyor> allActive = new ArrayList<>();
		for(int i=0; i<all.size();i++) {
			if(!all.get(i).isActive())
				continue;
			allActive.add(all.get(i));
		}
		return allActive;
	}
	
	@Override
	public int countAllActiveByDepoNomiVagonTuriAndTamirTuri(String depoNomi, String vagnTuri, 
			String tamirTuri, boolean active) {
		return vagonTayyorRepository.countAllActiveByDepoNomiVagonTuriAndTamirTuri(depoNomi, vagnTuri, tamirTuri, active);
	}
	
	@Override
	public VagonTayyor searchByNomer(Integer nomer) {
		Optional<VagonTayyor> exist=vagonTayyorRepository.findByNomer(nomer);
		if(exist.isPresent() && exist.get().isActive())
			return exist.get();
		else
			return null;
	}
	@Override
	public VagonTayyor findByNomer(Integer nomer) {	
		return vagonTayyorRepository.findByNomer(nomer).get();
	}
	
	// filterniki	
	@Override
	public List<VagonTayyor> findAllByDepoNomiVagonTuriAndCountry(String depoNomi, String vagonTuri, String country) {
		return vagonTayyorRepository.findAllByDepoNomiVagonTuriAndCountry(depoNomi, vagonTuri, country, true);
	}


	@Override
	public List<VagonTayyor> findAllByDepoNomiAndVagonTuri(String depoNomi, String vagonTuri) {
		return vagonTayyorRepository.findAllByDepoNomiAndVagonTuri(depoNomi, vagonTuri, true);
	}

	@Override
	public List<VagonTayyor> findAllByDepoNomiAndCountry(String depoNomi, String country) {
		return vagonTayyorRepository.findAllByDepoNomiAndCountry(depoNomi, country, true);
	}

	@Override
	public List<VagonTayyor> findAllByDepoNomi(String depoNomi) {
		return vagonTayyorRepository.findAllByDepoNomi(depoNomi, true);
	}

	@Override
	public List<VagonTayyor> findAllByVagonTuriAndCountry(String vagonTuri, String country) {
		return vagonTayyorRepository.findAllByVagonTuriAndCountry(vagonTuri, country, true);
	}

	@Override
	public List<VagonTayyor> findAllBycountry(String country) {
		return vagonTayyorRepository.findAllBycountry(country, true);
	}

	@Override
	public List<VagonTayyor> findAllByVagonTuri(String vagonTuri) {
		return vagonTayyorRepository.findAllByVagonTuri(vagonTuri, true);
	}

	//
	@Override
	public List<VagonTayyor> findByDepoNomiVagonTuriAndCountry(String depoNomi, String vagonTuri, String country) {
		return vagonTayyorRepository.findByDepoNomiVagonTuriAndCountry(depoNomi, vagonTuri, country);
	}


	@Override
	public List<VagonTayyor> findByDepoNomiAndVagonTuri(String depoNomi, String vagonTuri) {
		return vagonTayyorRepository.findByDepoNomiAndVagonTuri(depoNomi, vagonTuri);
	}

	@Override
	public List<VagonTayyor> findByDepoNomiAndCountry(String depoNomi, String country) {
		return vagonTayyorRepository.findByDepoNomiAndCountry(depoNomi, country);
	}

	@Override
	public List<VagonTayyor> findByDepoNomi(String depoNomi) {
		return vagonTayyorRepository.findByDepoNomi(depoNomi);
	}

	@Override
	public List<VagonTayyor> findByVagonTuriAndCountry(String vagonTuri, String country) {
		return vagonTayyorRepository.findByVagonTuriAndCountry(vagonTuri, country);
	}

	@Override
	public List<VagonTayyor> findBycountry(String country) {
		return vagonTayyorRepository.findBycountry(country);
	}

	@Override
	public List<VagonTayyor> findByVagonTuri(String vagonTuri) {
		return vagonTayyorRepository.findByVagonTuri(vagonTuri);
	}
	
	PlanDto  planDto = new PlanDto();// planlarni qaytarib berish uchun orgaruvchiga oldim
	PlanDto  planDtoAllMonth = new PlanDto();
	@Override
	public void savePlan(PlanDto planDto) {
		List<VagonTayyor> all = vagonTayyorRepository.findAll();
		for(int i=0; i<all.size(); i++) {
			all.get(i).setActive(false);
			vagonTayyorRepository.save(all.get(i));
		}
		this.planDto= planDto;
		this.planDtoAllMonth= planDto;
		
	}

	public PlanDto getPlanDto() {
		return this.planDto;
	}
	
	public PlanDto getPlanDtoAllMonth() {
		return planDtoAllMonth;
	}
	
	
	@Override
	public void makePlanNul() {
		PlanDto dto0 = new PlanDto();
		this.planDtoAllMonth=dto0;
		
	}

	@Override
	public VagonTayyor findById(Long id) {
		return vagonTayyorRepository.findById(id).get(); 
	}


}
