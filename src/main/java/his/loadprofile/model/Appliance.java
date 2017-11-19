package his.loadprofile.model;

import java.util.Date;

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
    
    private String text;
    
    private Date date;

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ApplianceType getType() {
		return type;
	}

	public void setType(ApplianceType type) {
		this.type = type;
	}

    
}
