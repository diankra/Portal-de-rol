package es.urjc.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
	
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
		http.authorizeRequests().antMatchers("/cierra_sesion/aceptar").permitAll();
		
		// Private pages (all other pages)
		 http.authorizeRequests().anyRequest().authenticated();
		 http.authorizeRequests().antMatchers("/foro/{hilo}/escribir_mensaje").hasAnyRole("USER");
		 http.authorizeRequests().antMatchers("/foro/{hilo}/{mensaje}").hasAnyRole("ADMIN");
		 
		http.formLogin().loginPage("/inicia_sesion");
		http.formLogin().usernameParameter("username");
		http.formLogin().passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/crear_usuario/aceptar");
		//http.formLogin().failureUrl("/loginerror");
		 
		// Logout
		http.logout().logoutUrl("/cierra_sesion");
		http.logout().logoutSuccessUrl("/cierra_sesion/aceptar");
	
	}
	
	//CSRFHandler
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CSRFHandlerInterceptor());
	}
	
	@Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	 // User
	 auth.inMemoryAuthentication().withUser("user").password("{noop}pass").roles("USER");
	 auth.inMemoryAuthentication().withUser("admin").password("{noop}adminpass").roles("USER, ADMIN");
	 
	 }

	
	//Esto es para que se vea el CSS que si no se lo carga
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/css/**");
	    web.ignoring().antMatchers("/assets/**");
	}


}

class CSRFHandlerInterceptor extends HandlerInterceptorAdapter {
	 @Override
	 public void postHandle(final HttpServletRequest request,
	 final HttpServletResponse response, final Object handler,
	 
	 final ModelAndView modelAndView) throws Exception {
	 CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
	 modelAndView.addObject("token", token.getToken());
	 }
}


