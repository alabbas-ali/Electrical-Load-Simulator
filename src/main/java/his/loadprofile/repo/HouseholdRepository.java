package his.loadprofile.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import his.loadprofile.core.HouseHoldType;
import his.loadprofile.model.Household;

public interface HouseholdRepository extends MongoRepository<Household, String>{
	
	Household findFirstBySimName(String simName);

	// Supports native JSON query string
	@Query("{simName:'?0', type: '?1'}")
	Household findCustomBySimName(String simName, HouseHoldType type);

	@Query("{simName: ?0, type: { $in : ['HOUSEHOLD_SINGLE','HOUSEHOLD_FAMILE'] }})")
	List<Household> findCustomByRegExSimName(String simName);
	
}
