package com.example.algamoneyapi.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.algamoneyapi.config.property.AlgamoneyApiProperty;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	
	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty;

	//private String originPermitida = "http://localhost:8000"; //TODO: Configurar para diferentes ambientes
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		//Especifica a origem permitida de CORS
		response.setHeader("Access-Control-Allow-Origin", algamoneyApiProperty.getOriginPermitida());
		//Passa que é necessário ter as credenciais só assim o cookie é enviado
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		//Verifica se a requisição Http é de Option, se sim monta o HTTP
		if("OPTIONS".equals(request.getMethod()) && algamoneyApiProperty.getOriginPermitida().equals(request.getHeader("Origin"))) {
			//Verbos Permitidos
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			
			//Tipos de controle de autorização
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			
			//Tempo em Cache, depois de 1 hora faz de novo a req
			response.setHeader("Access-Control-Max-Age", "3600");

			response.setStatus(HttpServletResponse.SC_OK);			
		} else {
			chain.doFilter(req, resp);
			
		}
		
		
	}

	
}
