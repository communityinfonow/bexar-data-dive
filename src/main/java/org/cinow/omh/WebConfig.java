package org.cinow.omh;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The web configuration.
 * 
 * @author brian
 */
@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

        /**
         * Adds the view controllers. All views will be forwarded to index.html as Vue handles all routing.
         */
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/")
                                .setViewName("forward:/index.html");
                registry.addViewController("/{x:[\\w\\-]+}")
                                .setViewName("forward:/index.html");
                registry.addViewController("/{x:^(?!api$).*$}/*/{y:[\\w\\-]+}")
                                .setViewName("forward:/index.html");
        }

        /**
         * Adds the resource handlers for static content.
         */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        }

        /*@Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
                        //.addMapping("/oauth2/authorization/google")
                        //.allowedOrigins("http://localhost:3000")
                        //.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
        }*/

        /*@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}*/
}
