package his.loadprofile.job;

import his.loadprofile.core.HouseHoldType;
import his.loadprofile.core.SimulatorInterface;
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
	private SimulatorInterface simulator;
	private HouesCreator houesCreator;
	private HouseholdRepository householdRepository;
	private SimConfigReopsitory simConfigReopsitory;
	private ActivitiesTimeShifter activitiesTimeShifter;

	public SimulationRunner(
			SimConfig config, 
			SimpMessagingTemplate template,
			SimulatorInterface simulator,
			HouesCreator houesCreator,
			HouseholdRepository householdRepository,
			SimConfigReopsitory simConfigReopsitory,
			ActivitiesTimeShifter activitiesTimeShifter
	) {
		super(template);
		this.jobName = config.getName();
		this.config = config;
		this.simulator = simulator;
		this.simulator.setJobRunner(this);
		this.houesCreator = houesCreator;
		this.householdRepository = householdRepository;
		this.simConfigReopsitory = simConfigReopsitory;
		this.activitiesTimeShifter = activitiesTimeShifter;
		this.sendProgress();
	}

	@Override
	public void run() {
		// send WS RUNNING status for front end
		this.state = "RUNNING";
		this.sendProgress();

		Household household;
		List<Measurement>  totalMeasurements = new ArrayList<Measurement>();
		LoadCurve loadCurve;
		int i;
		
		for (i = 1; i <= config.getNumberOfHouses(); i++) {
			
			// create Random HousHold
			household = houesCreator.getHousehold();
			
			// time shift for Availabilities activities
			household.setAvailabilities(
					activitiesTimeShifter.shift(
							household.getAvailabilities()
							)
					);
			
			// simulate the Load Curve for the HousHold
			loadCurve = new LoadCurve();
			loadCurve.setMeasurements(
					simulator.simulate(
							household, 
							config.getTimeStep()
					)
				);
			loadCurve.setCreationDate(new Date());
			loadCurve.setName("Sim_" + config.getName() + "_" + i);
			loadCurve.setDescription("This is auto created curve for simulation " + 
					config.getName() + "and household number " + i
				);
			
			// set the HousHold Load Curve
			household.setResultLoadCurve(loadCurve);
			
			// calculate the total load Curve for all HousHolds
			List<Measurement>  measurements = household.getResultLoadCurve().getMeasurements();
			for (int k = 0; k < measurements.size(); k++) {
				float value = measurements.get(k).getValue();
				int lightingValue = measurements.get(k).getLightingValue();
				int activeOccupancy = measurements.get(k).getActiveOccupancy();
				int occupancy = measurements.get(k).getOccupancy();
				if(totalMeasurements.size() > k) {
					value += totalMeasurements.get(k).getValue();
					lightingValue += totalMeasurements.get(k).getLightingValue();
					activeOccupancy += totalMeasurements.get(k).getActiveOccupancy();
					occupancy += totalMeasurements.get(k).getOccupancy();
					totalMeasurements.get(k).setValue(value);
					totalMeasurements.get(k).setLightingValue(lightingValue);
					totalMeasurements.get(k).setActiveOccupancy(activeOccupancy);
					totalMeasurements.get(k).setOccupancy(occupancy);
				} else {
					Measurement m = new Measurement();
					m.setTime(measurements.get(k).getTime());
					m.setValue(value);
					m.setLightingValue(lightingValue);
					m.setActiveOccupancy(activeOccupancy);
					m.setOccupancy(occupancy);
					totalMeasurements.add(m);
				}
			}
			
			// save the HousHold in DB
			householdRepository.save(household);
			
			// send WS status for front end
			this.state = "STATE";
			this.progress.set((i*100)/this.config.getNumberOfHouses());
			this.sendProgress();
		}
		
		// Create a total HousHold
		Household householdtotal = new Household();
		householdtotal.setType(HouseHoldType.HOUSEHOLD_TOTAL);
		householdtotal.setCreationDate(new Date());
		householdtotal.setSimName(config.getName());
		
		// Create the Total Load Curve 
		LoadCurve resultLoadCurve = new LoadCurve();
		resultLoadCurve.setMeasurements(totalMeasurements);
		resultLoadCurve.setCreationDate(new Date());
		i++;
		resultLoadCurve.setName("Sim_" + config.getName() + "_" + i);
		resultLoadCurve.setDescription("total load Curve for simulation " + config.getName());
		
		// set the total HousHold Load Curve
		householdtotal.setResultLoadCurve(resultLoadCurve);
		
		// Save the total HousHold
		householdRepository.save(householdtotal);
		
		// save the Simulation configuration 
		simConfigReopsitory.save(this.config);

		// send WS DONE status for front end
		this.state = "DONE";
		this.sendProgress();
	}

}
