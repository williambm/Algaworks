package com.example.algamoneyapi.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	//metodo executa primeiro e retorna True
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	//Se o metodo acima retornar true executa o método abaixo
	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		//Para funcionar tenho de usar o request e o response da transação
		HttpServletRequest req = ((ServletServerHttpRequest)request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
		
		//Pega o Refresh Token e passa para a String
		String refreshToken = body.getRefreshToken().getValue();
		
		//Cria um Cookie
		adicionarRefreshTokenNoCookie(refreshToken, req, resp);
		
		//Variavel Token que na verdade recebe o Body do Http de forma convertida 
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken)body;
		
		//Após criar um cookie e passar na resposta deve-se tirar o refresh Token do Body
		removeRefreshTokenDoBody(token);
		
		return body;
	}
	

	//Método responsável por criar o cookie Http
	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		Cookie refreshTokenCookie = new Cookie("refreshToken",refreshToken);
		refreshTokenCookie.setHttpOnly(true);//Visivel apenas no HTTP escondido de scripts
		refreshTokenCookie.setSecure(false);//TODO: Mudar para True em Producao
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");// req passa a URI da aplicação e acrescenta oauth/token
		refreshTokenCookie.setMaxAge(3600*24);//Expira em um dia
		resp.addCookie(refreshTokenCookie);//passa o cookie na resposta !
		
	}
	
	//Método responsável por remover depois da criação do Cookie o token do Body do HTTP, a classe de parâmetro torna acessivel o token
	private void removeRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);//Remove apenas o refresh Token do Http Body
		
	}

}
