package his.loadprofile.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplianceController {
	
	@RequestMapping("/appliance/details/{id}")
	public String details(Map<String, Object> model) 
	{
		        
        return "appliance/details";
	}
	
	@RequestMapping("/appliance/edit/{id}")
	public String edit(Map<String, Object> model) 
	{
		        
        return "appliance/edit";
	}

}
