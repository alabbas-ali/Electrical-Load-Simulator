package his.loadprofile.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import his.loadprofile.model.Availability;

public interface AvailabilityRepository extends MongoRepository<Availability, String>, AvailabilityRepositoryCustom{
	
	Availability findFirstByName(String name);
	
	// Supports native JSON query string
	@Query("{name:'?0'}")
	Availability findCustomByName(String name);

	@Query("{name: { $regex: ?0 } })")
	List<Availability> findCustomByRegExName(String name);

}
