package his.loadprofile.job;

import his.loadprofile.model.Household;
import his.loadprofile.model.SimConfig;

public interface HouesCreator {
	
	public void setSimConfig(SimConfig config);
	
	public Household getHousehold();
}
