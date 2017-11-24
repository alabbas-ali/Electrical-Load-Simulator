package his.loadprofile.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import his.loadprofile.core.ApplianceType;

@Document(collection = "appliance")
public class Appliance {
	
	@Id
    private long id;

    @Indexed(unique = true)
    private String name;
    
    private ApplianceType type;
    
    private String description;
    
    private Date creationDate;
    
    protected List<OperationalMode> operationalModes;

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

	public ApplianceType getType() {
		return type;
	}

	public void setType(ApplianceType type) {
		this.type = type;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<OperationalMode> getOperationalModes() {
		return operationalModes;
	}

	public void setOperationalModes(List<OperationalMode> operationalModes) {
		this.operationalModes = operationalModes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	    
}
