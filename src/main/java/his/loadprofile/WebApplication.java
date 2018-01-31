package his.loadprofile;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import his.loadprofile.core.Simulator;
import his.loadprofile.core.SimulatorInterface;
import his.loadprofile.job.ActivitiesTimeShifter;
import his.loadprofile.job.HouesCreator;
import his.loadprofile.job.RandomActivitiesTimeShifter;
import his.loadprofile.job.RandomHouesCreator;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration 
@ComponentScan
@EnableMongoRepositories(basePackages = "his.loadprofile.repo")
@EntityScan("his.loadprofile.model")
@EnableAsync
public class WebApplication extends SpringBootServletInitializer {
	
	
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
	public HouesCreator houesCreator() {
		RandomHouesCreator rhc = new RandomHouesCreator();
		return rhc;
	}
	
	
	@Bean
	public SimulatorInterface simulator() {
		Simulator s = new Simulator();
		return s;
	}
	
	@Bean
	public ActivitiesTimeShifter activitiesTimeShifter() {
		RandomActivitiesTimeShifter s = new RandomActivitiesTimeShifter();
		return s;
	}
}
