package his.loadprofile.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import his.loadprofile.job.JsonResponseBody;
import his.loadprofile.job.SimulationRunner;
import his.loadprofile.model.SimConfig;
import his.loadprofile.repo.HouseholdRepository;
import his.loadprofile.repo.SimConfigReopsitory;

@Controller
public class IndexController {

	@Autowired
	ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private HouseholdRepository householdRepository;
	
	@Autowired
	SimConfigReopsitory simConfigReopsitory;

	private List<SimulationRunner> myJobList = new ArrayList<SimulationRunner>();

	@RequestMapping("/")
	public String index(Map<String, Object> model) {
		return "index";
	}
	
	@ResponseBody
	@RequestMapping(value="/start-simulation", method=RequestMethod.POST)
	public JsonResponseBody startSimulation(
			@ModelAttribute(value="simConfig") SimConfig simConfig,
			BindingResult validationResult
	) {
		
		JsonResponseBody response = new JsonResponseBody();
		
		System.out.println(this + "START startWork");
		System.out.println(this + simConfig.getName());
		System.out.println(this + " " + simConfig.getNumberOfHouses());
		
		ValidationUtils.rejectIfEmpty(validationResult, "name", "Name can not be empty.");
		ValidationUtils.rejectIfEmpty(validationResult, "numberOfHouses", "Number Of Houses can not be empty.");
		ValidationUtils.rejectIfEmpty(validationResult, "singlesPercentage", "Singles Percentage not be empty.");
		
		if(validationResult.hasErrors())
		{
			System.out.println(this + "Is not valid");
			System.out.println(validationResult.getAllErrors() + "errors");
			response.setStatus(HttpResponceStatus.FAIL);
			response.setResult(validationResult.getAllErrors());
			return response;
		}
		
		SimulationRunner simRunner = new SimulationRunner(
				simConfig, 
				template,
				householdRepository,
				simConfigReopsitory
		);
		
		myJobList.add(simRunner);
		taskExecutor.execute(simRunner);
		
		response.setStatus(HttpResponceStatus.SUCCESS);
		return response;
	}

	@RequestMapping(value = "/sim-status")
	@ResponseBody
	@SubscribeMapping("initial")
	List<SimulationRunner> fetchStatus() {
		return this.myJobList;
	}

}
