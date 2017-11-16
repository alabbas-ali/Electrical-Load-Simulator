package his.loadprofile.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import his.loadprofile.model.Domain;
import his.loadprofile.repo.DomainRepository;

@Controller
public class WelcomeController {
	
	@Autowired
	private DomainRepository domainRepository;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) 
	{
		
//      Domain obj = new Domain();
//      obj.setDisplayAds(true);
//      obj.setDomain("abbas.test");
//      
//      Domain n = domainRepository.save(obj);
		
		Domain obj = domainRepository.findOne(7L);
        System.out.println(obj);

        Domain obj2 = domainRepository.findFirstByDomain("abbas.test");
        System.out.println(obj2);

        //int n = domainRepository.updateDomain("mkyong.com", true);
        //System.out.println("Number of records updated : " + n);
        
        model.put("message", "Load Profiler Simulator");
        model.put("domain", obj2);
        
        return "welcome";
	}

}

