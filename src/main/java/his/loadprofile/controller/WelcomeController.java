package his.loadprofile.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import his.loadprofile.model.Domain;
import his.loadprofile.repo.DomainRepository;

@Controller
public class WelcomeController {

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";
	
	@Autowired
	private DomainRepository domainRepository;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		
		Domain obj = domainRepository.findOne(7L);
        System.out.println(obj);

        Domain obj2 = domainRepository.findFirstByDomain("mkyong.com");
        System.out.println(obj2);

        int n = domainRepository.updateDomain("mkyong.com", true);
        System.out.println("Number of records updated : " + n);
        
		return "welcome";
	}

}

