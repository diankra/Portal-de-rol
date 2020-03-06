package es.urjc.code;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Service
@Configuration
public class imageService implements WebMvcConfigurer {
	private static final Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")
				.addResourceLocations("file:" + FILES_FOLDER.toAbsolutePath().toString() + "/");
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	private Path createFilePath(long id, Path folder) {
		return folder.resolve("image-" + id + ".jpg");
	}

	public String saveImage(String folderName, long id, MultipartFile image) throws IOException {
		Path folder = FILES_FOLDER.resolve(folderName);
		if (!Files.exists(folder)) {
			Files.createDirectories(folder);
		}
		Path newFile = createFilePath(id, folder);
		image.transferTo(newFile);
		return "/images/"+folderName+"/image-"+id+".jpg";
	}
	
	public String saveImage(String folderName, long id, File image) throws IOException {
		Path folder = FILES_FOLDER.resolve(folderName);
		if (!Files.exists(folder)) {
			Files.createDirectories(folder);
		}
		Path newFile = createFilePath(id, folder);
		File imagen = new File(newFile.toString());
		
		return "/images/"+folderName+"/image-"+id+".jpg";
	}

}
