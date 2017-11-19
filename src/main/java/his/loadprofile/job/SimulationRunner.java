package his.loadprofile.job;

import java.util.ArrayList;
import java.util.List;

import his.loadprofile.core.Simulator;
import his.loadprofile.model.Household;
import his.loadprofile.model.SimConfig;

import org.springframework.messaging.simp.SimpMessagingTemplate;


public class SimulationRunner extends JobRunner{
	
	private SimConfig config;
	Simulator simulator;
	SimRandomChooser randomchouser;
	
	public SimulationRunner(SimConfig config, SimpMessagingTemplate template){
		super(template);
		this.jobName = config.getName();
		this.config = config;
		this.simulator = new Simulator(config);
		this.randomchouser = new SimRandomChooser(config);
		sendProgress();
	}
	
	@Override
	public void run() {
		System.out.println(jobName + " is running");
		state = "RUNNING";
		sendProgress();
		
		List<Household> households = new ArrayList<Household>();
		Household household;
		for (int i = 0; i < config.getNumberOfHouses(); i++) {
			household = randomchouser.getRandomHousehold();
			household.setSimulationResult(simulator.simulate(household));
			households.add(household);
			progress.set((int) ((i / config.getNumberOfHouses()) * 100));
	        sendProgress();
		}
		
		state = "DONE";
        sendProgress();
		System.out.println(jobName + " is finished");
	}
	
}
