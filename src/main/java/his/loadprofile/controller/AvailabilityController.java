package his.loadprofile.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import his.loadprofile.model.Availability;

@Controller
public class AvailabilityController {
	
	@RequestMapping("/availability")
	public String index(Map<String, Object> model) 
	{
		List<Availability> availabilities = new ArrayList<Availability>();
		model.put("availabilities", availabilities);
		return "availability/index";
	}
	
	@RequestMapping("/availability/details/{id}")
	public String details(Map<String, Object> model) 
	{
		        
        return "availability/details";
	}
	
	@RequestMapping("/availability/edit/{id}")
	public String edit(Map<String, Object> model) 
	{
		        
        return "availability/edit";
	}
	


}
