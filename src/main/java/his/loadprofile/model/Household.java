package his.loadprofile.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "household")
public class Household {
	
	
	@Id
    private long id;
	
	private List<Availability> availabilities;
	
	private List<Appliance> appliances;
	
	private Household type;

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

	public Household getType() {
		return type;
	}

	public void setType(Household type) {
		this.type = type;
	}
	
	

}
