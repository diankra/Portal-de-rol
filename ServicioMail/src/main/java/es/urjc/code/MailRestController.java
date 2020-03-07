package es.urjc.code;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.sun.mail.smtp.SMTPTransport;

@RestController
public class MailRestController {
	Properties prop = System.getProperties();
	String user, pass;

	@PostConstruct
	public void init() {
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.starttls.enable", "true");
	}

	@PostMapping("/enviar_correo")
	@ResponseStatus(HttpStatus.CREATED)
	public Correo enviarCorreo(@RequestBody Correo c) {
		user = c.getUsername();
		pass = c.getPassword();
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);
			}
		};
		Session session = Session.getInstance(prop, auth);
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(c.getFrom()));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(c.getTo(), false));
			msg.setSubject(c.getSubject());
			msg.setDataHandler(new DataHandler(new HTMLDataSource(c.getBody())));
			msg.setSentDate(new Date());

			SMTPTransport smtp = (SMTPTransport) session.getTransport("smtp");
			smtp.connect("smtp.gmail.com", user, pass);
			smtp.sendMessage(msg, msg.getAllRecipients());

			System.out.println("Response: " + smtp.getLastServerResponse());
			smtp.close();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return c;
	}

}
