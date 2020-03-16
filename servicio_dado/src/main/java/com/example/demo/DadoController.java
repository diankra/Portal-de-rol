package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


@RestController
public class DadoController {

	private Map<Long, Tirada> tiradas = new ConcurrentHashMap<>();
	private AtomicLong lastId = new AtomicLong();
	
	@PostMapping("/tirada/{idp}")
	@ResponseStatus(HttpStatus.CREATED)
	public long nuevaTirada(@PathVariable(required = false) long idp, @RequestBody String usuario) {
		long id = lastId.incrementAndGet();
		Tirada t = new Tirada(id, usuario, idp);
		tiradas.put(id, t);
	return new Integer(t.getResultado());
	}
	
	@GetMapping("/getTiradas/{par}")
	@ResponseStatus(HttpStatus.OK)
	public List<Tirada> getTiradas(@PathVariable(required = false) long par){
		List<Tirada> res = new ArrayList<Tirada>();
		for(Tirada t:tiradas.values())
		{
			if(t.getPartida() == par) {
				res.add(t);
			}
		}
		return res;
	}
	
}
