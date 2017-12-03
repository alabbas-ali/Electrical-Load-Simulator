package his.loadprofile.model;


public class OperationalMode {
	
    private String name;
    
    private String description;
    
    private float powerInputOn;
    
    private float powerInputOff;
    
    private int restartDelay;
    
    private double scaleFactor;
    
    private LoadCurve loadCurve;
    
    private int duration; 
    
    private int leftCycleTime = 0;
    
	private int leftRestartDelay = 0;

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

	public float getPowerInputOn() {
		return powerInputOn;
	}

	public void setPowerInputOn(float powerInputOn) {
		this.powerInputOn = powerInputOn;
	}

	public float getPowerInputOff() {
		return powerInputOff;
	}

	public void setPowerInputOff(float powerInputOff) {
		this.powerInputOff = powerInputOff;
	}

	public int getRestartDelay() {
		return restartDelay;
	}

	public void setRestartDelay(int restartDelay) {
		this.restartDelay = restartDelay;
	}

	public double getScaleFactor() {
		return scaleFactor;
	}

	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	public LoadCurve getLoadCurve() {
		return loadCurve;
	}

	public void setLoadCurve(LoadCurve loadCurve) {
		this.loadCurve = loadCurve;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getLeftCycleTime() {
		return leftCycleTime;
	}

	public void setLeftCycleTime(int leftCycleTime) {
		this.leftCycleTime = leftCycleTime;
	}

	public int getLeftRestartDelay() {
		return leftRestartDelay;
	}

	public void setLeftRestartDelay(int leftRestartDelay) {
		this.leftRestartDelay = leftRestartDelay;
	}
	
}
