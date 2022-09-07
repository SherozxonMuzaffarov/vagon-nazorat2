package com.sms.serviceImp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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
import org.springframework.data.domain.Sort;
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
import com.sms.model.VagonTayyorUty;
import com.sms.repository.VagonTayyorUtyRepository;
import com.sms.service.VagonTayyorUtyService;

@Service
public class VagonTayyorUtyServiceImp implements VagonTayyorUtyService{

	@Autowired
	private VagonTayyorUtyRepository vagonTayyorUtyRepository;
	
	LocalDateTime today = LocalDateTime.now();
	LocalDateTime minusHours = today.plusHours(5);
	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String currentDate = minusHours.format(myFormatObj);
	String samDate ;
	String havDate ;
	String andjDate ;
	
public void createPdf(List<VagonTayyorUty> vagons, HttpServletResponse response) throws IOException {
		
		String home = System.getProperty("user.home");
		  File file = new File(home + "/Downloads" + "/O'TY rejasi boyicha ta'mir boyicha ma'lumot.pdf");
		  if (!file.getParentFile().exists())
		      file.getParentFile().mkdirs();
		  if (!file.exists())
		      file.createNewFile();
		List<VagonTayyorUty> allVagons = vagons;
		try {
			response.setHeader("Content-Disposition",
                    "attachment;fileName=\"" + "O'TY rejasi boyicha tamir boyicha malumot.pdf" +"\"");
			response.setContentType("application/pdf");
			
			PdfWriter writer = new PdfWriter(file.getAbsolutePath());
			PdfDocument pdfDoc = new PdfDocument(writer);
			Document doc = new Document(pdfDoc);

			String FONT_FILENAME = "./src/main/resources/arial.ttf";
			PdfFont font = PdfFontFactory.createFont(FONT_FILENAME, PdfEncodings.IDENTITY_H);
			doc.setFont(font);

			Paragraph paragraph = new Paragraph("Ta'mirdan chiqgan vagonlar(O'TY rejasi bo'yicha)");
			paragraph.setBackgroundColor(Color.DARK_GRAY);
			paragraph.setFontColor(Color.WHITE);// Setting background color to cell1
			paragraph.setBorder(Border.NO_BORDER);            // Setting border to cell1
			paragraph.setTextAlignment(TextAlignment.CENTER); // Setting text alignment to cell1
			paragraph.setFontSize(16);

			float[] columnWidth = {30f,200f,200f,200f,200f,200f,200f,200f,200f,200f};
			Table table = new Table(columnWidth);
			table.setTextAlignment(TextAlignment.CENTER);
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
			for(VagonTayyorUty vagon:allVagons) {
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
	public VagonTayyorUty saveVagon(VagonTayyorUty vagon) {	
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		Optional<VagonTayyorUty> exist=	vagonTayyorUtyRepository.findByNomer(vagon.getNomer());
		if(exist.isPresent())
			return new VagonTayyorUty();
		VagonTayyorUty savedVagon = new VagonTayyorUty();
		savedVagon.setNomer(vagon.getNomer());
		savedVagon.setDepoNomi(vagon.getDepoNomi());
		savedVagon.setRemontTuri(vagon.getRemontTuri());
		savedVagon.setVagonTuri(vagon.getVagonTuri());
		savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		savedVagon.setIzoh(vagon.getIzoh());
		savedVagon.setCountry(vagon.getCountry());
		savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		savedVagon.setCountry("O'TY(ГАЖК)");
		savedVagon.setActive(true);

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime minusHours = today.plusHours(5);
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String currentDate = minusHours.format(myFormatObj);

		savedVagon.setCreatedDate(currentDate);

		return vagonTayyorUtyRepository.save(savedVagon);	
	}
	@Override
	public VagonTayyorUty saveVagonSam(VagonTayyorUty vagon) {
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		Optional<VagonTayyorUty> exist=	vagonTayyorUtyRepository.findByNomer(vagon.getNomer());
		if(exist.isPresent() || !vagon.getDepoNomi().equalsIgnoreCase("VCHD-6"))
			return new VagonTayyorUty();
		VagonTayyorUty savedVagon = new VagonTayyorUty();
		savedVagon.setNomer(vagon.getNomer());
		savedVagon.setDepoNomi(vagon.getDepoNomi());
		savedVagon.setRemontTuri(vagon.getRemontTuri());
		savedVagon.setVagonTuri(vagon.getVagonTuri());
		savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		savedVagon.setIzoh(vagon.getIzoh());
		savedVagon.setCountry(vagon.getCountry());
		savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		savedVagon.setCountry("O'TY(ГАЖК)");
		savedVagon.setActive(true);

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime minusHours = today.plusHours(5);
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		samDate = minusHours.format(myFormatObj);

		savedVagon.setCreatedDate(samDate);

		return vagonTayyorUtyRepository.save(savedVagon);
	}
	
	@Override
	public VagonTayyorUty saveVagonHav(VagonTayyorUty vagon) {
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		Optional<VagonTayyorUty> exist=	vagonTayyorUtyRepository.findByNomer(vagon.getNomer());
		if(exist.isPresent() || !vagon.getDepoNomi().equalsIgnoreCase("VCHD-3"))
			return new VagonTayyorUty();
		VagonTayyorUty savedVagon = new VagonTayyorUty();
		savedVagon.setNomer(vagon.getNomer());
		savedVagon.setDepoNomi(vagon.getDepoNomi());
		savedVagon.setRemontTuri(vagon.getRemontTuri());
		savedVagon.setVagonTuri(vagon.getVagonTuri());
		savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		savedVagon.setIzoh(vagon.getIzoh());
		savedVagon.setCountry(vagon.getCountry());
		savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		savedVagon.setCountry("O'TY(ГАЖК)");
		savedVagon.setActive(true);

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime minusHours = today.plusHours(5);
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		havDate = minusHours.format(myFormatObj);

		savedVagon.setCreatedDate(havDate);
		System.out.println("Saqlandi *********" );

		return vagonTayyorUtyRepository.save(savedVagon);
	}

	@Override
	public VagonTayyorUty saveVagonAndj(VagonTayyorUty vagon) {
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		Optional<VagonTayyorUty> exist= vagonTayyorUtyRepository.findByNomer(vagon.getNomer());
		if(exist.isPresent() || !vagon.getDepoNomi().equalsIgnoreCase("VCHD-5"))
			return new VagonTayyorUty();
		VagonTayyorUty savedVagon = new VagonTayyorUty();
		savedVagon.setNomer(vagon.getNomer());
		savedVagon.setDepoNomi(vagon.getDepoNomi());
		savedVagon.setRemontTuri(vagon.getRemontTuri());
		savedVagon.setVagonTuri(vagon.getVagonTuri());
		savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		savedVagon.setIzoh(vagon.getIzoh());
		savedVagon.setCountry(vagon.getCountry());
		savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		savedVagon.setCountry("O'TY(ГАЖК)");
		savedVagon.setActive(true);
		
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime minusHours = today.plusHours(5);
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		andjDate = minusHours.format(myFormatObj);

		savedVagon.setCreatedDate(andjDate);

		return vagonTayyorUtyRepository.save(savedVagon);
}

	@Override
	public VagonTayyorUty updateVagon(VagonTayyorUty vagon, long id) {	
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		 Optional<VagonTayyorUty> exist = vagonTayyorUtyRepository.findById(id);
		 if(!exist.isPresent() )
			 return new VagonTayyorUty();
		 VagonTayyorUty savedVagon = exist.get();
		 savedVagon.setId(id);
		 savedVagon.setNomer(vagon.getNomer());
		 savedVagon.setVagonTuri(vagon.getVagonTuri());
		 savedVagon.setDepoNomi(vagon.getDepoNomi());
		 savedVagon.setRemontTuri(vagon.getRemontTuri());
		 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		 savedVagon.setIzoh(vagon.getIzoh());
		 savedVagon.setCountry(vagon.getCountry());
		 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		 savedVagon.setCountry("O'TY(ГАЖК)");

		 return vagonTayyorUtyRepository.save(savedVagon);
	}
	
	@Override
	public VagonTayyorUty updateVagonSam(VagonTayyorUty vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		 Optional<VagonTayyorUty> exist = vagonTayyorUtyRepository.findById(id);
		 if(exist.get().getDepoNomi().equalsIgnoreCase("VCHD-6") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-6")) {
			 VagonTayyorUty savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
   			 savedVagon.setCountry("O'TY(ГАЖК)");
			 LocalDateTime today = LocalDateTime.now();
			 LocalDateTime minusHours = today.plusHours(5);
			 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			 samDate = minusHours.format(myFormatObj);

			 return vagonTayyorUtyRepository.save(savedVagon);
		 }else
			return new VagonTayyorUty();

	}

	@Override
	public VagonTayyorUty updateVagonHav(VagonTayyorUty vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		 Optional<VagonTayyorUty> exist = vagonTayyorUtyRepository.findById(id);
		 if(exist.get().getDepoNomi().equalsIgnoreCase("VCHD-3") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-3") ) {
			 
			 VagonTayyorUty savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
			 savedVagon.setCountry("O'TY(ГАЖК)");

			 LocalDateTime today = LocalDateTime.now();
			 LocalDateTime minusHours = today.plusHours(5);
			 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			 havDate = minusHours.format(myFormatObj);

			 return vagonTayyorUtyRepository.save(savedVagon);
		 }else
			 return new VagonTayyorUty();
	}

	@Override
	public VagonTayyorUty updateVagonAndj(VagonTayyorUty vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		 Optional<VagonTayyorUty> exist = vagonTayyorUtyRepository.findById(id);
		 if( exist.get().getDepoNomi().equalsIgnoreCase("VCHD-5") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-5") ){	
			 VagonTayyorUty savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
			 savedVagon.setCountry("O'TY(ГАЖК)");

			 LocalDateTime today = LocalDateTime.now();
			 LocalDateTime minusHours = today.plusHours(5);
			 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			 andjDate = minusHours.format(myFormatObj);

			 return vagonTayyorUtyRepository.save(savedVagon);
		}else {
				 return new VagonTayyorUty();
		}
	}

	//*update JAmi oylarniki
	@Override
	public VagonTayyorUty updateVagonMonths(VagonTayyorUty vagon, long id) {	
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		 Optional<VagonTayyorUty> exist = vagonTayyorUtyRepository.findById(id);
		 if(!exist.isPresent() )
			 return new VagonTayyorUty();
		 VagonTayyorUty savedVagon = exist.get();
		 savedVagon.setId(id);
		 savedVagon.setNomer(vagon.getNomer());
		 savedVagon.setVagonTuri(vagon.getVagonTuri());
		 savedVagon.setDepoNomi(vagon.getDepoNomi());
		 savedVagon.setRemontTuri(vagon.getRemontTuri());
		 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
		 savedVagon.setIzoh(vagon.getIzoh());
		 savedVagon.setCountry(vagon.getCountry());
		 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
		 savedVagon.setCountry("O'TY(ГАЖК)");

		 return vagonTayyorUtyRepository.save(savedVagon);
	}
	
	@Override
	public VagonTayyorUty updateVagonSamMonths(VagonTayyorUty vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		 Optional<VagonTayyorUty> exist = vagonTayyorUtyRepository.findById(id);
		 if(exist.get().getDepoNomi().equalsIgnoreCase("VCHD-6") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-6")) {
			 VagonTayyorUty savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
   			 savedVagon.setCountry("O'TY(ГАЖК)");

			 return vagonTayyorUtyRepository.save(savedVagon);
		 }else
			return new VagonTayyorUty();

	}

	@Override
	public VagonTayyorUty updateVagonHavMonths(VagonTayyorUty vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		 Optional<VagonTayyorUty> exist = vagonTayyorUtyRepository.findById(id);
		 if(exist.get().getDepoNomi().equalsIgnoreCase("VCHD-3") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-3") ) {
			 
			 VagonTayyorUty savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
			 savedVagon.setCountry("O'TY(ГАЖК)");

			 return vagonTayyorUtyRepository.save(savedVagon);
		 }else
			 return new VagonTayyorUty();
	}

	@Override
	public VagonTayyorUty updateVagonAndjMonths(VagonTayyorUty vagon, long id) {
		if(vagon.getNomer() == null)
			return new VagonTayyorUty();
		 Optional<VagonTayyorUty> exist = vagonTayyorUtyRepository.findById(id);
		 if( exist.get().getDepoNomi().equalsIgnoreCase("VCHD-5") && vagon.getDepoNomi().equalsIgnoreCase("VCHD-5") ){	
			 VagonTayyorUty savedVagon = exist.get();
			 savedVagon.setId(id);
			 savedVagon.setNomer(vagon.getNomer());
			 savedVagon.setVagonTuri(vagon.getVagonTuri());
			 savedVagon.setDepoNomi(vagon.getDepoNomi());
			 savedVagon.setRemontTuri(vagon.getRemontTuri());
			 savedVagon.setIshlabChiqarilganYili(vagon.getIshlabChiqarilganYili());
			 savedVagon.setIzoh(vagon.getIzoh());
			 savedVagon.setCountry(vagon.getCountry());
			 savedVagon.setChiqganVaqti(vagon.getChiqganVaqti());
			 savedVagon.setCountry("O'TY(ГАЖК)");

			 return vagonTayyorUtyRepository.save(savedVagon);
		}else {
				 return new VagonTayyorUty();
		}
	}

	
	
	@Override
	public VagonTayyorUty getVagonById(long id) {
	Optional<VagonTayyorUty> exist=	vagonTayyorUtyRepository.findById(id);
	if(!exist.isPresent())
		return new VagonTayyorUty();
	return exist.get();
	}

	@Override
	public void deleteVagonById(long id) throws NotFoundException {
		Optional<VagonTayyorUty> exist=	vagonTayyorUtyRepository.findById(id);
		if(exist.isPresent()) 
			vagonTayyorUtyRepository.deleteById(id);

	}

	@Override
	public void deleteVagonSam(long id) throws NotFoundException {
		VagonTayyorUty exist=	vagonTayyorUtyRepository.findById(id).get();	
		if(exist.getDepoNomi().equals("VCHD-6") ) {
			vagonTayyorUtyRepository.deleteById(id);
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime minusHours = today.plusHours(5);
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			samDate = minusHours.format(myFormatObj);
		}
	}

	@Override
	public void deleteVagonHav(long id) throws NotFoundException{
		VagonTayyorUty exist=	vagonTayyorUtyRepository.findById(id).get();	
		if(exist.getDepoNomi().equals("VCHD-3") ) {
			vagonTayyorUtyRepository.deleteById(id);
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime minusHours = today.plusHours(5);
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			havDate = minusHours.format(myFormatObj);
		}
	}

	@Override
	public void deleteVagonAndj(long id) throws NotFoundException{
		VagonTayyorUty exist=	vagonTayyorUtyRepository.findById(id).get();	
		if(exist.getDepoNomi().equals("VCHD-5") ) {
			vagonTayyorUtyRepository.deleteById(id);
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime minusHours = today.plusHours(5);
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			andjDate = minusHours.format(myFormatObj);
		}
	}



	
	@Override
	public int countByDepoNomiVagonTuriAndTamirTuri(String depoNomi, String vagonTuri, String tamirTuri) {
		return vagonTayyorUtyRepository.countByDepoNomiVagonTuriAndTamirTuri(depoNomi, vagonTuri, tamirTuri);
	}

	@Override
	public List<VagonTayyorUty> findAllBoth() {	
		return vagonTayyorUtyRepository.findAll();
	}
	@Override
	public List<VagonTayyorUty> findAllActive() {
		List<VagonTayyorUty> all= vagonTayyorUtyRepository.findAll();
		List<VagonTayyorUty> allActive = new ArrayList<>();
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
		return vagonTayyorUtyRepository.countAllActiveByDepoNomiVagonTuriAndTamirTuri(depoNomi, vagnTuri, tamirTuri, active);
	}
	
	@Override
	public VagonTayyorUty searchByNomer(Integer nomer) {
		Optional<VagonTayyorUty> exist=vagonTayyorUtyRepository.findByNomer(nomer);
		if(exist.isPresent() && exist.get().isActive())
			return exist.get();
		else
			return null;
	}
	@Override
	public VagonTayyorUty findByNomer(Integer nomer) {	
		return vagonTayyorUtyRepository.findByNomer(nomer).get();
	}
	
	// filterniki	
	@Override
	public List<VagonTayyorUty> findAllByDepoNomiAndVagonTuri(String depoNomi, String vagonTuri) {
		return vagonTayyorUtyRepository.findAllByDepoNomiAndVagonTuri(depoNomi, vagonTuri, true);
	}

	@Override
	public List<VagonTayyorUty> findAllByDepoNomi(String depoNomi) {
		return vagonTayyorUtyRepository.findAllByDepoNomi(depoNomi, true);
	}

	@Override
	public List<VagonTayyorUty> findAllByVagonTuri(String vagonTuri) {
		return vagonTayyorUtyRepository.findAllByVagonTuri(vagonTuri, true);
	}

	//hamma oylarini filter uchn

	@Override
	public List<VagonTayyorUty> findByDepoNomiAndVagonTuri(String depoNomi, String vagonTuri) {
		return vagonTayyorUtyRepository.findByDepoNomiAndVagonTuri(depoNomi, vagonTuri);
	}

	@Override
	public List<VagonTayyorUty> findByDepoNomi(String depoNomi) {
		return vagonTayyorUtyRepository.findByDepoNomi(depoNomi);
	}

	@Override
	public List<VagonTayyorUty> findByVagonTuri(String vagonTuri) {
		return vagonTayyorUtyRepository.findByVagonTuri(vagonTuri);
	}
	
	PlanDto  planDto = new PlanDto();// planlarni qaytarib berish uchun orgaruvchiga oldim
	PlanDto  planDtoAllMonth = new PlanDto();
	@Override
	public void savePlan(PlanDto planDto) {
		List<VagonTayyorUty> all = vagonTayyorUtyRepository.findAll();
		for(int i=0; i<all.size(); i++) {
			all.get(i).setActive(false);
			vagonTayyorUtyRepository.save(all.get(i));
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
	public VagonTayyorUty findById(Long id) {
		return vagonTayyorUtyRepository.findById(id).get();
	}



}
