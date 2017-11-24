package his.loadprofile.model;


public class OperationalMode {
	
	
    private String name;
    
    private String description;
    
    private float powerInput;
    
    private LoadCurve loadCurve;
    
    private Integer duration;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPowerInput() {
		return powerInput;
	}

	public void setPowerInput(float powerInput) {
		this.powerInput = powerInput;
	}

	public LoadCurve getLoadCurve() {
		return loadCurve;
	}

	public void setLoadCurve(LoadCurve loadCurve) {
		this.loadCurve = loadCurve;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
    
    

}
