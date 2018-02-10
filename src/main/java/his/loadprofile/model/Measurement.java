package his.loadprofile.model;


public class Measurement {
	
    private int time;
    
    private float value;
    
    private int lightingValue;
    
    private int activeOccupancy;
    
    private int occupancy;

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public int getLightingValue() {
		return lightingValue;
	}

	public void setLightingValue(int lightingValue) {
		this.lightingValue = lightingValue;
	}

	public int getActiveOccupancy() {
		return activeOccupancy;
	}

	public void setActiveOccupancy(int activeOccupancy) {
		this.activeOccupancy = activeOccupancy;
	}

	public int getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}
	
	
    
}
