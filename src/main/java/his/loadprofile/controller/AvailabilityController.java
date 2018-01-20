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

import his.loadprofile.http.HttpResponceStatus;
import his.loadprofile.http.JsonResponseBody;
import his.loadprofile.model.Activity;
import his.loadprofile.model.Availability;
import his.loadprofile.repo.AvailabilityRepository;

@Controller
public class AvailabilityController {
	
	@Autowired
	AvailabilityRepository availabilityRepository;
	
	@RequestMapping("/availability")
	public String index(Map<String, Object> model) 
	{
		List<Availability> availabilities = availabilityRepository.findAll();
		model.put("availabilities", availabilities);
		return "availability/index";
	}
	
	@RequestMapping(value = "/availability/details/{id}", method = RequestMethod.GET)
	public String details(
			@PathVariable("id") String id,
			Map<String, Object> model 
	) {
		Availability availability = availabilityRepository.findOne(id);
		model.put("availability", availability);
        return "availability/details";
	}
	
	@RequestMapping(value = "/availability/edit/{id}")
	public String edit(
			@PathVariable("id") String id,
			Map<String, Object> model
	) {
		Availability availability = availabilityRepository.findOne(id);
		model.put("availability", availability);
		model.put("title", "Edit the Appliance: " + availability.getName());
        return "availability/edit";
	}
	
	@RequestMapping(value = "/availability/delete/{id}")
	public String delete(
			@PathVariable("id") String id,
			final RedirectAttributes redirectAttributes
	) {
		availabilityRepository.delete(id);
		redirectAttributes.addFlashAttribute("msg", "Appliance is deleted!");
        return "redirect:/availability";
	}
	
	@RequestMapping(value = "/availability/add")
	public String add(Map<String, Object> model) {
		Availability availability = new Availability();
		model.put("availability", availability);	
		model.put("title", "Add new Availability");
        return "availability/edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "/availability/save", method= RequestMethod.POST)
	public JsonResponseBody save(
			@Valid @RequestBody Availability availability,
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
		
		System.out.println(availability);
		System.out.println(availability.getName());
		System.out.println(availability.getType());
		//System.out.println(availability.getDescription());
		
		for (Activity activity : availability.getActivities()) {
			System.out.println("" + activity.getName());
			System.out.println("" + activity.getType());
			System.out.println("" + activity.getStart());
			System.out.println("" + activity.getEnd());
		}
		
		availability.setCreationDate(new Date());
		availabilityRepository.save(availability);
		
		response.setStatus(HttpResponceStatus.SUCCESS);
		return response;
	}
	


}
