package his.loadprofile.job;

import his.loadprofile.core.Simulator;
import his.loadprofile.model.Household;
import his.loadprofile.model.SimConfig;
import his.loadprofile.repo.HouseholdRepository;
import his.loadprofile.repo.SimConfigReopsitory;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class SimulationRunner extends JobRunner {

	private SimConfig config;
	Simulator simulator;
	SimRandomChooser randomchouser;
	HouseholdRepository householdRepository;
	SimConfigReopsitory simConfigReopsitory;

	public SimulationRunner(
			SimConfig config, 
			SimpMessagingTemplate template,
			HouseholdRepository householdRepository,
			SimConfigReopsitory simConfigReopsitory
	) {
		super(template);
		this.jobName = config.getName();
		this.config = config;
		this.simulator = new Simulator(config.getNumberOfHouses(), this);
		this.randomchouser = new SimRandomChooser(config);
		this.householdRepository = householdRepository;
		this.simConfigReopsitory = simConfigReopsitory;
		sendProgress();
	}

	@Override
	public void run() {
		System.out.println(jobName + " is running");
		state = "RUNNING";
		sendProgress();

		Household household;
		
		for (int i = 1; i <= config.getNumberOfHouses(); i++) {
			
			household = randomchouser.getRandomHousehold();
			household.setResultLoadCurve(simulator.simulate(household, i));
			
			householdRepository.save(household);
		}
		
		simConfigReopsitory.save(this.config);

		state = "DONE";
		sendProgress();
		System.out.println(jobName + " is finished");
	}

}
