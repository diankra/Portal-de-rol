package es.urjc.code;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.Path;
import com.itextpdf.text.pdf.parser.clipper.Paths;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.springframework.http.HttpStatus;

@RestController
public class PDFController {

	@PostMapping(value = "/generar_pdf")
	@ResponseStatus(HttpStatus.CREATED)
	public Document nuevoPDF(@RequestBody int id, @RequestBody String name, @RequestBody String info) throws FileNotFoundException, DocumentException {
		Document pdf = new Document();
		PdfWriter.getInstance(pdf, new FileOutputStream("Ficha_" + id + ".pdf"));		 
		pdf.open();
		Font titleFont = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.RED);
		Font textFont = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
		Chunk nombre = new Chunk(name, titleFont);		
		pdf.add(nombre);
		Chunk descripcion = new Chunk(info, textFont);
		pdf.add(descripcion);
		pdf.close();
						 
		return pdf;
	}

}
