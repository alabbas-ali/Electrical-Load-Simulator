package his.loadprofile.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import his.loadprofile.model.Household;
import his.loadprofile.model.SimConfig;
import his.loadprofile.repo.HouseholdRepository;
import his.loadprofile.repo.SimConfigReopsitory;

@Controller
public class SimulationController {
	
	@Autowired
	SimConfigReopsitory simConfigReopsitory;
	
	@Autowired
	private HouseholdRepository householdRepository;
	
	@RequestMapping("/simulations")
	public String index(Map<String, Object> model) 
	{
		List<SimConfig> simConfigs = simConfigReopsitory.findAll();
		model.put("simConfigs",simConfigs);
		return "simulation/index";
	}
	
	
	@RequestMapping(value = "/simulations/result/{id}", method = RequestMethod.GET)
	public String result(
			@PathVariable("id") String id,
			Map<String, Object> model 
	) {
		SimConfig simConfig = simConfigReopsitory.findOne(id);
		List<Household> houses = householdRepository.findCustomByRegExSimName(simConfig.getName());
		model.put("simConfig", simConfig);
		model.put("houses", houses);
        return "simulation/details";
	}

}
