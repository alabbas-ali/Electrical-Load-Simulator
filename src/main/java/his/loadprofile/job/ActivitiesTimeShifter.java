package his.loadprofile.job;

import java.util.List;
import his.loadprofile.model.Availability;

public interface ActivitiesTimeShifter {
	
	public List<Availability> shift(List<Availability> availabilitiesList);

}
