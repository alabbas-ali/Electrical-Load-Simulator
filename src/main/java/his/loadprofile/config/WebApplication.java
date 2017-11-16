package his.loadprofile.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "his.loadprofile.controller")
@EnableMongoRepositories(basePackages = "his.loadprofile.repo")
@EntityScan("his.loadprofile.model")
public class WebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
	{
		return application.sources(WebApplication.class);
	}

	public static void main(String[] args) throws Exception 
	{
		SpringApplication.run(WebApplication.class, args);
	}

}
