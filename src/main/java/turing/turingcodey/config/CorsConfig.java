package turing.turingcodey.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://172.17.1.29:63342","http://localhost:63342","http://172.17.1.29:80","http://localhost:80","http://localhost","http://172.17.1.29","http://172.19.1.174","http://172.19.1.174:8080"
                ,"http://172.19.1.174:3000","htp://172.17.1.29:8601","http://localhost:8601","*")
               // .allowedOrigins("localhost:63342")
               // .allowedOrigins("172.17.1.29:80")
                //.allowedOrigins( "localhost:80")
                .allowedMethods("GET", "POST","PUT")
                .allowCredentials(true).maxAge(3600);
    }
}


