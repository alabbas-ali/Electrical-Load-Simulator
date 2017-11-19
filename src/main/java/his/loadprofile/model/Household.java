package his.loadprofile.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import his.loadprofile.core.HouseHoldType;

@Document(collection = "household")
public class Household {
	
	
	@Id
    private long id;
	
	private List<Availability> availabilities;
	
	private List<Appliance> appliances;
	
	private HouseHoldType type;
	
	private SimResult simulationResult;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Availability> getAvailabilities() {
		return availabilities;
	}

	public void setAvailabilities(List<Availability> availabilities) {
		this.availabilities = availabilities;
	}

	public List<Appliance> getAppliances() {
		return appliances;
	}

	public void setAppliances(List<Appliance> appliances) {
		this.appliances = appliances;
	}

	public HouseHoldType getType() {
		return type;
	}

	public void setType(HouseHoldType type) {
		this.type = type;
	}

	public SimResult getSimulationResult() {
		return simulationResult;
	}

	public void setSimulationResult(SimResult simulationResult) {
		this.simulationResult = simulationResult;
	}
	
	
	
}
