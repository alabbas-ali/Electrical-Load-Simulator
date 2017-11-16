package his.loadprofile.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@SpringBootApplication
@ComponentScan(basePackages = "his.loadprofile.controller")
@EnableMongoRepositories(basePackages = "his.loadprofile.repo")
@EntityScan("his.loadprofile.model")
public class WebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public ServletContextTemplateResolver defaultTemplateResolver() {
		ServletContextTemplateResolver yourTemplateResolver = new ServletContextTemplateResolver();
		yourTemplateResolver.setPrefix("/WEB-INF/templates/");
		yourTemplateResolver.setSuffix(".html");
		yourTemplateResolver.setTemplateMode("HTML5");
		yourTemplateResolver.setCharacterEncoding("UTF-8");

		return yourTemplateResolver;
	}
	
//	@Bean
//	public SpringTemplateEngine springTemplateEngine(TemplateResolver templateResolver) {
//		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
//		springTemplateEngine.addTemplateResolver(templateResolver);
//
//		return springTemplateEngine;
//	}
//	
//	@Bean
//	public ThymeleafViewResolver thymeleafViewResolver(SpringTemplateEngine templateEngine) {
//		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
//		thymeleafViewResolver.setTemplateEngine(templateEngine);
//		thymeleafViewResolver.setOrder(1);
//		thymeleafViewResolver.setViewNames(new String [] {"thymeleaf/*"});
//		return thymeleafViewResolver;
//	}
}
