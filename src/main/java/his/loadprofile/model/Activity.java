package his.loadprofile.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import his.loadprofile.core.ActivityType;
import his.loadprofile.http.CustomerDateAndTimeDeserialize;

public class Activity {
	
	@NotNull(message="Please enter a activity name")
	private String name;
	
	@JsonDeserialize(using=CustomerDateAndTimeDeserialize.class)
	@DateTimeFormat(pattern="h:mm a") 
	@NotNull(message="Please enter a start time")
	private Date start;
	
	@JsonDeserialize(using=CustomerDateAndTimeDeserialize.class)
	@DateTimeFormat(pattern="h:mm a") 
	@NotNull(message="Please enter a end time")
	private Date end;
	
	@NotNull(message="Please enter a Activity Type")
	private ActivityType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}
	
}
