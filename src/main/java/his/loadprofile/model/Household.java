package his.loadprofile.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import his.loadprofile.core.HouseHoldType;

@Document(collection = "household")
public class Household {
	
	
	@Id
    private long id;
	
	private String simName;
	
	private List<Availability> availabilities;
	
	private List<Appliance> appliances;
	
	private HouseHoldType type;
	
	private LocalDateTime creationDate;
	
	private LoadCurve resultLoadCurve;

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

	public String getSimName() {
		return simName;
	}

	public void setSimName(String simName) {
		this.simName = simName;
	}

	public LoadCurve getResultLoadCurve() {
		return resultLoadCurve;
	}

	public void setResultLoadCurve(LoadCurve resultLoadCurve) {
		this.resultLoadCurve = resultLoadCurve;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
	
	
}
