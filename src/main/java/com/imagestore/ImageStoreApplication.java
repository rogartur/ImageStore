package com.imagestore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.imagestore.auth.JwtFilter;

@SpringBootApplication
public class ImageStoreApplication {
	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	public static void main(String[] args) {
		SpringApplication.run(ImageStoreApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter(jwtSecret));
		registrationBean.addUrlPatterns("/images/*","/user/update", "/user/get/*");
		return registrationBean;
	}
}
