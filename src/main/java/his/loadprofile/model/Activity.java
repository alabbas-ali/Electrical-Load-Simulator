package his.loadprofile.model;

import java.util.Date;

import his.loadprofile.core.ActivityType;

public class Activity {
	
	private String name;
	
	private Date start;
	
	private Date end;
	
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
