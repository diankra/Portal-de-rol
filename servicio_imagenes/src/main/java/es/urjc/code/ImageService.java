package es.urjc.code;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Service
@Configuration
public class ImageService implements WebMvcConfigurer {
	private static final Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");

	private Path createFilePath(long id, Path folder) {
		return folder.resolve("image-" + id + ".jpg");
	}

	public void saveImage(String folderName, long id, MultipartFile image) throws IOException {
		Path folder = FILES_FOLDER.resolve(folderName);
		if (!Files.exists(folder)) {
			Files.createDirectories(folder);
		}
		Path newFile = createFilePath(id, folder);
		image.transferTo(newFile);
	}

	public ResponseEntity<Object> createResponseFromImage(String folderName, long id) throws IOException {
		Path folder = FILES_FOLDER.resolve(folderName);
		Path checkFile = createFilePath(id, folder);
		if(Files.exists(checkFile)) {
 
			return new ResponseEntity<>(new File(checkFile.toString()), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	

}
