package his.loadprofile.model;


import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import his.loadprofile.core.AvailabilityType;

@Document(collection = "availability")
public class Availability {
	
	@Id
    private String id;
	
	@NotNull(message = "Name can't empty!")
	@Indexed(unique = true)
	private String name;
	
 
	@NotNull(message = "Availability Type should be defined!")
	private AvailabilityType type;
	
	private List<Activity> activities;

	private Date creationDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AvailabilityType getType() {
		return type;
	}

	public void setType(AvailabilityType type) {
		this.type = type;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
}
