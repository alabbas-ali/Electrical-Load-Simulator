package his.loadprofile.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import his.loadprofile.model.Appliance;
import his.loadprofile.model.Availability;
import his.loadprofile.repo.ApplianceRepository;

@Controller
public class ConfigurationController {
	
	
	@Autowired
	private ApplianceRepository applianceRepository;
	
	@RequestMapping("/configuration")
	public String index(Map<String, Object> model) 
	{
		List<Availability> availabilities = new ArrayList<Availability>();
		
		List<Appliance> appliances = applianceRepository.findAll();
		
		model.put("appliances", appliances);
		model.put("availabilities", availabilities);
		return "config";
	}

}
