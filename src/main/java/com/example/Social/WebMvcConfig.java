package com.example.Social;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new interceptor());
	
	    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
	    interceptor.setParamName("mylocale");
	    registry.addInterceptor(interceptor);
	
	}
	


	@Bean
    public LocaleResolver localeResolver(){
	CookieLocaleResolver resolver = new CookieLocaleResolver();
	resolver.setDefaultLocale(new Locale("en"));
	resolver.setCookieName("myLocaleCookie");
	resolver.setCookieMaxAge(4800);
	return resolver;
    }
	
    @Bean  
    public ResourceBundleMessageSource messageSource() {  
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();  
        source.setBasename("new");
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);  
        return source;  
    } 

              
  
}
