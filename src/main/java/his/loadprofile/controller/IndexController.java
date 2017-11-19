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

import his.loadprofile.component.SimulationRunner;
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

	@RequestMapping("/start-simulation")
	public String startSimulation(Map<String, Object> model) {
		SimConfig config = new SimConfig();
		
		// get the configuration from the form and 
		System.out.println(this + "START startWork");
		SimulationRunner simRunner = new SimulationRunner(config, template);
		taskExecutor.execute(simRunner);

		return "/";
	}

	@RequestMapping(value = "/status")
	@ResponseBody
	@SubscribeMapping("initial")
	List<SimulationRunner> fetchStatus() {
		return this.myJobList;
	}

}
