package es.urjc.code;

import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

//import com.mysql.cj.conf.PropertySet;
//import com.mysql.cj.exceptions.ExceptionInterceptor;
import org.springframework.security.authentication.AuthenticationProvider;
//import com.mysql.cj.protocol.Protocol;
//import com.mysql.cj.protocol.ServerSession;
import org.springframework.security.core.Authentication;

@Component
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private UsuarioRepository userRepository;

	@Autowired
	private UserComponent userComponent;

	@Override
	public Authentication authenticate(Authentication auth) {
		List<SimpleGrantedAuthority> roles = new ArrayList<>();
		Usuario user = userRepository.findUsuarioByNombre(auth.getName());
		if (user == null) {
			throw new BadCredentialsException("User not found");
		}
		String password = (String) auth.getCredentials();
		if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
			throw new BadCredentialsException("Wrong password");

		} else {
			for (String role : user.getRoles()) {
				roles.add(new SimpleGrantedAuthority(role));
			}
			userComponent.setLoggedUser(user);
		}
		return new UsernamePasswordAuthenticationToken(user.getNombre(), password, roles);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}
