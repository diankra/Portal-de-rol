package es.urjc.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImagenesController {

	@Autowired
	private ImageService imgService;
	
	@PostMapping("/{id}/imagen")
	@ResponseStatus(HttpStatus.CREATED)
	public File saveImage(@PathVariable long id, @RequestParam("user-file") File imagenFile) throws IOException{
		System.out.println(imagenFile.getPath());
		imgService.saveImage("mensajes", id, imagenFile);
		return imagenFile;
	}
	
	@GetMapping("/{id}/im")
	public ResponseEntity<Object> getImagenAnuncio(@PathVariable long id)
	 throws IOException {
	 
	 return this.imgService.createResponseFromImage("mensajes", id);

	}

	

}
