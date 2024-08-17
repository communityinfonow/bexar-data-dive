package org.cinow.omh;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The web configuration.
 * 
 * @author brian
 */
@Configuration
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
                registry.addViewController("/admin")
                                .setViewName("forward:/admin.html");
                registry.addViewController("/admin/{x:[\\w\\-]+}")
                                .setViewName("forward:/admin.html");
                registry.addViewController("/admin/{x:^(?!api$).*$}/*/{y:[\\w\\-]+}")
                                .setViewName("forward:/admin.html");
        }

        /**
         * Adds the resource handlers for static content.
         */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        }

        /**
         * Adds the CORS mappings.
         */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "POST");
        }
}
