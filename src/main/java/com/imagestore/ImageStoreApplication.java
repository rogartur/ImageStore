package com.imagestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.imagestore.auth.JwtFilter;

@SpringBootApplication
public class ImageStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageStoreApplication.class, args);
	}
	
//    @Bean
//    public FilterRegistrationBean jwtFilter() {
//        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setFilter(new JwtFilter());
//        registrationBean.addUrlPatterns("/images/*");
//        return registrationBean;
//    }
}
