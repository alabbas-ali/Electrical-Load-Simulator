package his.loadprofile.repo;

import his.loadprofile.core.AvailabilityType;
import his.loadprofile.model.Availability;

public interface AvailabilityRepositoryCustom {
	//Query("{type:'?0' , $sample: { size: 1 } })")
	
	Availability findOneRandomlyByType(AvailabilityType type);
}
