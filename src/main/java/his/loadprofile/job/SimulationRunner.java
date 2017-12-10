package his.loadprofile.job;

import his.loadprofile.core.HouseHoldType;
import his.loadprofile.core.Simulator;
import his.loadprofile.model.Household;
import his.loadprofile.model.LoadCurve;
import his.loadprofile.model.Measurement;
import his.loadprofile.model.SimConfig;
import his.loadprofile.repo.HouseholdRepository;
import his.loadprofile.repo.SimConfigReopsitory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class SimulationRunner extends JobRunner {

	private SimConfig config;
	Simulator simulator;
	RandomHouesCreator randomchouser;
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
		this.simulator = new Simulator(this);
		this.randomchouser = new RandomHouesCreator(config);
		this.householdRepository = householdRepository;
		this.simConfigReopsitory = simConfigReopsitory;
		this.sendProgress();
	}

	@Override
	public void run() {
		
		this.state = "RUNNING";
		this.sendProgress();

		Household household;
		
		Household householdtotal = new Household();
		householdtotal.setType(HouseHoldType.HOUSEHOLD_TOTAL);
		householdtotal.setCreationDate(new Date());
		householdtotal.setSimName(config.getName());
		List<Measurement>  totalMeasurements = new ArrayList<Measurement>();
		int i;
		for (i = 1; i <= config.getNumberOfHouses(); i++) {
			
			household = randomchouser.getRandomHousehold();
			household.setResultLoadCurve(simulator.simulate(household, i));
			List<Measurement>  measurements = household.getResultLoadCurve().getMeasurements();
			
			for (int k = 0; k < measurements.size(); k++) {
				Float value = measurements.get(k).getValue();
				if(totalMeasurements.size() > k) {
					value += totalMeasurements.get(k).getValue();
					totalMeasurements.get(k).setValue(value);
				} else {
					Measurement m = new Measurement();
					m.setTime(measurements.get(k).getTime());
					m.setValue(value);
					totalMeasurements.add(m);
				}
			}
			
			householdRepository.save(household);
			
			this.state = "STATE";
			this.progress.set((i*100)/this.config.getNumberOfHouses());
			this.sendProgress();
		}
		
		LoadCurve resultLoadCurve = new LoadCurve();
		resultLoadCurve.setMeasurements(totalMeasurements);
		resultLoadCurve.setCreationDate(new Date());
		i++;
		resultLoadCurve.setName("Sim_" + config.getName() + "_" + i);
		resultLoadCurve.setDescription("total load Curve for simulation " + config.getName());
		householdtotal.setResultLoadCurve(resultLoadCurve);
		householdRepository.save(householdtotal);
		simConfigReopsitory.save(this.config);

		this.state = "DONE";
		this.sendProgress();
	}

}
