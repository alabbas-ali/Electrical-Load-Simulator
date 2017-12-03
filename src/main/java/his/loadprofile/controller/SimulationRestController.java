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

import his.loadprofile.core.AppliancesImporter;
import his.loadprofile.job.JsonResponseBody;
import his.loadprofile.job.SimulationRunner;
import his.loadprofile.model.Appliance;
import his.loadprofile.model.SimConfig;
import his.loadprofile.repo.ApplianceRepository;
import his.loadprofile.repo.HouseholdRepository;
import his.loadprofile.repo.SimConfigReopsitory;

@RestController
public class SimulationRestController {
	
	@Autowired
	ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private ApplianceRepository  applianceRepository;
	
	@Autowired
	private HouseholdRepository householdRepository;
	
	@Autowired
	SimConfigReopsitory simConfigReopsitory;
	
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
	
	
	@ResponseBody
	@RequestMapping(value="/import-appliances", method=RequestMethod.GET)
	public JsonResponseBody importAppliances() 
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
	
	
	@RequestMapping(value = "/sim-status")
	@ResponseBody
	@SubscribeMapping("initial")
	List<SimulationRunner> fetchStatus() 
	{
		return this.myJobList;
	}

}
