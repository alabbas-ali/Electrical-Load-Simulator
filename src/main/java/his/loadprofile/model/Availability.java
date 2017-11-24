package his.loadprofile.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import his.loadprofile.core.AvailabilityType;

@Document(collection = "availability")
public class Availability {
	
	@Id
    private long id;
	
	private AvailabilityType type;
	
	private String name;
	
	private LocalDateTime creationDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
	
	
}
