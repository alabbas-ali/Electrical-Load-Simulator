package his.loadprofile.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import his.loadprofile.model.Appliance;
import his.loadprofile.repo.ApplianceRepository;

@Controller
public class ApplianceController {
	
	@Autowired
	private ApplianceRepository applianceRepository;
	
	@RequestMapping("/appliance")
	public String index(Map<String, Object> model) 
	{
		List<Appliance> appliances = applianceRepository.findAll();
		model.put("appliances", appliances);
		return "appliance/index";
	}
	
	@RequestMapping(value = "/appliance/details/{id}", method = RequestMethod.GET)
	public String details(
			@PathVariable("id") String id,
			Map<String, Object> model 
	) {
		// get the Appliance by id ang pass it in model
		// System.out.println(id);
		Appliance appliance = applianceRepository.findOne(id);
		model.put("appliance", appliance);
        return "appliance/details";
	}
	
	@RequestMapping("/appliance/edit/{id}")
	public String edit(Map<String, Object> model) 
	{
		// get the data and save or update Appliance
		
        return "appliance/edit";
	}

}
