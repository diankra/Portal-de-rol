package es.urjc.code;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImagenesController {

	@Autowired
	private ImageService imgService;
	
	@PostMapping("/{id}/imagen")
	public ResponseEntity<Object> saveImage(@PathVariable long id/*, @RequestParam MultipartFile imagenFile*/) throws IOException{
		//imgService.saveImage("mensajes", id, imagenFile);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}/imagen")
	public ResponseEntity<Object> getImagenAnuncio(@PathVariable long id)
	 throws IOException {
	 
	 return this.imgService.createResponseFromImage("mensajes", id);

	}

}
