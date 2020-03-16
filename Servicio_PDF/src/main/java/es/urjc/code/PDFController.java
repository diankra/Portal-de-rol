package es.urjc.code;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class PDFController {

	// public ConcurrentMap<Long, Document> mapaPDF = new ConcurrentHashMap<Long,
	// Document>();
	public ConcurrentMap<Long, FichaPDF> mapaPDF = new ConcurrentHashMap<Long, FichaPDF>();
	private AtomicLong lastId = new AtomicLong();

	@PostMapping(value = "/crear_pdf")
	@ResponseStatus(HttpStatus.CREATED)
	public FichaPDF crearPDF(@RequestBody FichaPDF fp) {
		/*
		 * Document pdf = new Document(); PdfWriter.getInstance(pdf, new
		 * FileOutputStream("Ficha_" + fp.getId() + ".pdf")); pdf.open(); Font titleFont
		 * = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.RED); Font textFont
		 * = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK); Chunk nombre
		 * = new Chunk(fp.getNombre(), titleFont); pdf.add(nombre); Chunk descripcion =
		 * new Chunk(fp.getDescripcion(), textFont); pdf.add(descripcion); pdf.close();
		 */
		// mapaPDF.put(fp.getId(), pdf);
		//fp.setId(lastId.incrementAndGet());
		mapaPDF.put(fp.getId(), fp);
		return fp;
	}

	@GetMapping("/crear_pdf/{id}")
	public ResponseEntity<byte[]> getPdf(@PathVariable(required = false) long id)
			throws DocumentException, IOException {
		if (mapaPDF.containsKey(id)) {
			FichaPDF fp = mapaPDF.get(id);
			Document pdf = new Document();
			PdfWriter.getInstance(pdf, new FileOutputStream("Ficha_" + fp.getId() + ".pdf"));
			pdf.open();
			Font titleFont = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.RED);
			Font textFont = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
			Chunk nombre = new Chunk(fp.getNombre(), titleFont);
			pdf.add(nombre);
			Chunk descripcion = new Chunk(fp.getDescripcion(), textFont);
			pdf.add(descripcion);
			pdf.close();
			byte[] arrayPDF = Files.readAllBytes(Paths.get("Ficha_" + fp.getId() + ".pdf"));
			// Document pdf = mapaPDF.get(id);
			return new ResponseEntity<>(arrayPDF, HttpStatus.OK);
		}
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
