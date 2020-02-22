package es.urjc.code;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/partidas_publicas").permitAll();
		http.authorizeRequests().antMatchers("/partidas_publicas/{partida}").permitAll();
		http.authorizeRequests().antMatchers("/foro").permitAll();
		//Para que los no autorizados no puedan crear hilos
		http.authorizeRequests().antMatchers("/foro/crear_hilo").authenticated();
		
		http.authorizeRequests().antMatchers("/foro/{hilo}").permitAll();
		
		http.authorizeRequests().antMatchers("/crear_usuario").permitAll();
		http.authorizeRequests().antMatchers("/inicia_sesion").permitAll();
		
		// Private pages (all other pages)
		 http.authorizeRequests().anyRequest().authenticated();
		 
		
	
	}
	
	//Esto es para que se vea el CSS que si no se lo carga
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/css/**");
	    web.ignoring().antMatchers("/assets/**");
	}


}


