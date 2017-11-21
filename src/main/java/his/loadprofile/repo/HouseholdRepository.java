package his.loadprofile.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import his.loadprofile.model.Household;

public interface HouseholdRepository extends MongoRepository<Household, Long>{
	
	Household findFirstBySimName(String simName);

	// Supports native JSON query string
	@Query("{domain:'?0'}")
	Household findCustomBySimName(String simName);

	@Query("{domain: { $regex: ?0 } })")
	List<Household> findCustomByRegExSimName(String simName);
}
