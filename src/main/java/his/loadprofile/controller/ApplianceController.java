package his.loadprofile.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		Appliance appliance = applianceRepository.findOne(id);
		model.put("appliance", appliance);
        return "appliance/details";
	}
	
	@RequestMapping(value = "/appliance/edit/{id}")
	public String edit(
			@PathVariable("id") String id,
			Map<String, Object> model
	) {
		Appliance appliance = applianceRepository.findOne(id);
		model.put("appliance", appliance);
		model.put("title", "Edit the Appliance: " + appliance.getName());
        return "appliance/edit";
	}
	
	@RequestMapping(value = "/appliance/delete/{id}")
	public String delete(
			@PathVariable("id") String id,
			final RedirectAttributes redirectAttributes
	) {
		applianceRepository.delete(id);
		redirectAttributes.addFlashAttribute("msg", "Appliance is deleted!");
        return "redirect:/appliance";
	}
	
	@RequestMapping(value = "/appliance/add")
	public String add(Map<String, Object> model) {
		Appliance appliance = new Appliance();
		model.put("appliance", appliance);	
		model.put("title", "Add new Appliance");
        return "appliance/edit";
	}
}
