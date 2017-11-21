package his.loadprofile.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import his.loadprofile.job.SimulationRunner;
import his.loadprofile.model.AjaxResponseBody;
import his.loadprofile.model.SimConfig;

@Controller
public class IndexController {

	@Autowired
	ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private SimpMessagingTemplate template;

	private List<SimulationRunner> myJobList = new ArrayList<SimulationRunner>();

	@RequestMapping("/")
	public String index(Map<String, Object> model) {

		return "index";
	}
	
	@ResponseBody
	@RequestMapping("/start-simulation")
	public AjaxResponseBody startSimulation(Map<String, Object> model) {
		
		AjaxResponseBody result = new AjaxResponseBody();
		
		//@Todo check and validate the Configuration from request
		SimConfig config = new SimConfig();
		
		config.setName("Test_Name_Simulation");
		config.setNumberOfHouses(3);
		System.out.println(this + "START startWork");
		SimulationRunner simRunner = new SimulationRunner(config, template);
		myJobList.add(simRunner);
		taskExecutor.execute(simRunner);
		
		result.setCode("200");
		result.setMsg("done");
		return result;
	}

	@RequestMapping(value = "/sim-status")
	@ResponseBody
	@SubscribeMapping("initial")
	List<SimulationRunner> fetchStatus() {
		return this.myJobList;
	}

}
