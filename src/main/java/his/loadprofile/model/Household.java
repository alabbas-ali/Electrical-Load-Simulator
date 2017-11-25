package his.loadprofile.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import his.loadprofile.core.HouseHoldType;

@Document(collection = "household")
public class Household {
	
	
	@Id
    private String id;
	
	private String simName;
	
	@Field("availabilities")
	private List<Availability> availabilities;
	
	@Field("appliances")
	private List<Appliance> appliances;
	
	private HouseHoldType type;
	
	private Date creationDate;
	
	private LoadCurve resultLoadCurve;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
	
}
