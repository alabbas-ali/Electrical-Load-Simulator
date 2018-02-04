package his.loadprofile.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import his.loadprofile.core.Importers.AppliancesImporter;
import his.loadprofile.http.HttpResponceStatus;
import his.loadprofile.http.JsonResponseBody;
import his.loadprofile.model.Appliance;
import his.loadprofile.model.OperationalMode;
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
	
	@ResponseBody
	@RequestMapping(value = "/appliance/save", method= RequestMethod.POST)
	public JsonResponseBody save(
			@Valid @RequestBody Appliance appliance,
			Errors validationResult,
			final RedirectAttributes redirectAttributes
	) {
		
		JsonResponseBody response = new JsonResponseBody();
		
		if(validationResult.hasErrors())
		{
			response.setStatus(HttpResponceStatus.FAIL);
			response.setResult(validationResult.getAllErrors());
			return response;
		}
		
		System.out.println(appliance);
		System.out.println(appliance.getName());
		System.out.println(appliance.getType());
		System.out.println(appliance.getDescription());
		
		for (OperationalMode mode : appliance.getOperationalModes()) {
			System.out.println("" + mode.getName());
			System.out.println("" + mode.getDescription());
			System.out.println("" + mode.getDuration());
			System.out.println("" + mode.getLeftCycleTime());
		}
		
		appliance.setCreationDate(new Date());
		applianceRepository.save(appliance);
		
		response.setStatus(HttpResponceStatus.SUCCESS);
		return response;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/appliance/import", method=RequestMethod.GET)
	public JsonResponseBody importA() 
	{
		JsonResponseBody response = new JsonResponseBody();
		AppliancesImporter importer = new AppliancesImporter();
		List<Appliance> appliances = importer.importData();
		
		if(appliances.size() > 0) {
			for (Appliance appliance : appliances) {
				applianceRepository.save(appliance);
			}
			response.setStatus(HttpResponceStatus.SUCCESS);
			response.setResult("number of inserted appliances" + appliances.size());
		}else {
			response.setStatus(HttpResponceStatus.FAIL);
			response.setResult("No appliances to import");
		}
		
		return response;	
	}
}
