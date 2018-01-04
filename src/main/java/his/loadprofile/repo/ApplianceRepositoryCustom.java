package his.loadprofile.repo;

import his.loadprofile.core.ApplianceType;
import his.loadprofile.model.Appliance;

public interface ApplianceRepositoryCustom {
	
	//Query("{type:  ?0, $sample: { size: 1 }})")
	
	Appliance findOneRandomlyByType(ApplianceType type);
}
