package com.imagestore.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtFilter extends GenericFilterBean {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private String jwtSecret;

	public JwtFilter(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		
		log.debug("Filtering request for Authorization header");

		final HttpServletRequest request = (HttpServletRequest) req;

		String authValue = "";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			String cookieValue = cookie.getValue();
			if (cookieValue.contains("currentUser")) {
				authValue = java.net.URLDecoder.decode(cookieValue, "UTF-8");
				try {
					JSONObject jsonObj = new JSONObject(authValue);
					authValue = jsonObj.getJSONObject("currentUser").get("authdata").toString();
				} catch (JSONException e) {
					throw new ServletException("Error while parsing auth header ", e);
				}
			}
		}
		if (authValue == null || authValue.isEmpty()) {
			throw new ServletException("Authorization header is missing ");
		}

		try {
			final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authValue).getBody();
			request.setAttribute("claims", claims);
		} catch (final SignatureException e) {
			throw new ServletException("Invalid token ");
		}

		chain.doFilter(req, res);
	}

}
