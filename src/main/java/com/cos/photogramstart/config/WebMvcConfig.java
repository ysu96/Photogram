package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{ //web 설정 파일
	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			registry
			.addResourceHandler("/upload/**") //jsp페이지에서 /upload/** 이런 주소패턴이 나오면 발동
			.addResourceLocations("file:///"+uploadFolder) //얘가 발동 , uploadFolder는 내가 yml에 적은 경로명
			.setCachePeriod(60*10*6) //초 단위, 1시간동안 캐시관리
			.resourceChain(true) //이제 발동
 			.addResolver(new PathResourceResolver());
	}

}
