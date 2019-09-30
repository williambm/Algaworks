package com.example.algamoneyapi.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {

	public static void main(String[] args) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
		
		//teste de combinação
		//CharSequence rawPassword = $2a$10$G1j5Rf8aEEiGc/AET9BA..xRR.qCpOUzBZoJd8ygbGy6tb3jsMT9G;		
		String encodedPassword = "admin";
		
		System.out.println(encoder.matches((CharSequence) encoder , encodedPassword ));
	}
}
