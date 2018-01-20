package his.loadprofile.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import his.loadprofile.core.SimulatorInterface;
import his.loadprofile.http.HttpResponceStatus;
import his.loadprofile.http.JsonResponseBody;
import his.loadprofile.job.HouesCreator;
import his.loadprofile.job.SimulationRunner;
import his.loadprofile.model.SimConfig;
import his.loadprofile.repo.HouseholdRepository;
import his.loadprofile.repo.SimConfigReopsitory;

@RestController
public class SimulationRestController {
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private HouseholdRepository householdRepository;
	
	@Autowired
	private SimConfigReopsitory simConfigReopsitory;
	
	@Autowired
	private HouesCreator houesCreator;
	
	@Autowired
	private SimulatorInterface simulator;
	
	private List<SimulationRunner> myJobList = new ArrayList<SimulationRunner>();
	
	@ResponseBody
	@RequestMapping(value="/start-simulation", method=RequestMethod.POST)
	public JsonResponseBody startSimulation(
			@Valid @RequestBody SimConfig simConfig,
			Errors validationResult
	) {
		
		JsonResponseBody response = new JsonResponseBody();
		
		if(validationResult.hasErrors())
		{
			response.setStatus(HttpResponceStatus.FAIL);
			response.setResult(validationResult.getAllErrors());
			return response;
		}
		
		simConfig.setDate(new Date());
		houesCreator.setSimConfig(simConfig);
		SimulationRunner simRunner = new SimulationRunner(
			simConfig,
			template,
			simulator,
			houesCreator,
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
	List<SimulationRunner> fetchStatus() 
	{
		return this.myJobList;
	}

}
