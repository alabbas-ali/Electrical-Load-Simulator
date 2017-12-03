package his.loadprofile;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@SpringBootApplication(exclude = { org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.class })
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableMongoRepositories(basePackages = "his.loadprofile.repo")
@EntityScan("his.loadprofile.model")
@EnableAsync
public class WebApplication extends SpringBootServletInitializer {
	
	@Autowired
    private ThymeleafProperties properties;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25);
		return executor;
	}

	@Bean
	public ServletContextTemplateResolver defaultTemplateResolver(ServletContext servletContext) {
		ServletContextTemplateResolver yourTemplateResolver = new ServletContextTemplateResolver(servletContext);
		yourTemplateResolver.setCharacterEncoding("UTF-8");
		yourTemplateResolver.setPrefix(this.properties.getPrefix());
		yourTemplateResolver.setSuffix(this.properties.getSuffix());
		yourTemplateResolver.setTemplateMode(this.properties.getMode());
		yourTemplateResolver.setCacheable(this.properties.isCache());
		
		return yourTemplateResolver;
	}
}
