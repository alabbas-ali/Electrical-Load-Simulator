package his.loadprofile.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import his.loadprofile.core.ApplianceType;
import his.loadprofile.model.Appliance;

public interface ApplianceRepository extends MongoRepository<Appliance, String> {
	
	@Query("{type:  ?0, $sample: { size: 1 }})")
	Appliance findCustomByType(ApplianceType type);
}
