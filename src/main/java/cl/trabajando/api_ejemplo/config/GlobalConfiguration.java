package cl.trabajando.api_ejemplo.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cl.trabajando.api_ejemplo.interceptors.MDCInterceptor;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = { "cl.trabajando.api_ejemplo.controllers", "cl.trabajando.api_ejemplo.services",
	"cl.trabajando.api_ejemplo.controllers_advices", "cl.trabajando.api_ejemplo.repo" })
public class GlobalConfiguration implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor addMDCInterceptor() {
	return new MDCInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(addMDCInterceptor()).addPathPatterns("/**");
    }

    /**
     * obtiene la configuración de ambiente
     */
    @Autowired
    private Environment env;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
	registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
	registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /*
     * Si desea tener otra conexion a base de datos debe repetir esta configuracion
     * en un nuevo beans dataSource() transactionManager() sqlSessionFactory()
     * mapperFactoryNomina() -- este ultimo es uno por archivo xml si desea crear
     * nuevos archivos de mapeo debe crear un nuevo bean de mapperFactory
     */


    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
	ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
	bundle.setDefaultEncoding("UTF-8");
	Properties fileEncodings = new Properties();
	fileEncodings.setProperty("application-messages", "UTF-8");
	bundle.setFileEncodings(fileEncodings);
	bundle.setFallbackToSystemLocale(true);
	bundle.setBasename("classpath:application-messages");
	return bundle;
    }
}
