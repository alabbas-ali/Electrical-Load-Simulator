package his.loadprofile.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import his.loadprofile.model.Availability;

public interface AvailabilityRepository extends MongoRepository<Availability, Long> {
	
	Availability findFirstByName(String name);

	// Supports native JSON query string
	@Query("{domain:'?0'}")
	Availability findCustomByName(String name);

	@Query("{domain: { $regex: ?0 } })")
	List<Availability> findCustomByRegExName(String name);
}
