package ua.pivik.VueCookingBook.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @autor Alexander Pivovarov
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryWebServerFactoryCustomizer(){
        return container -> {
          container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
        };
    }

}
