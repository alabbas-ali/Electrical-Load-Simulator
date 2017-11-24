package his.loadprofile.model;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import his.loadprofile.core.AvailabilityType;

@Document(collection = "availability")
public class Availability {
	
	@Id
    private String id;
	
	@Indexed(unique = true)
	private String name;
	
	private AvailabilityType type;
	
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
	
	
	
}
