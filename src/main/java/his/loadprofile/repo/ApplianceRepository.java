package his.loadprofile.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import his.loadprofile.model.Appliance;

public interface ApplianceRepository extends MongoRepository<Appliance, Long> {

	Appliance findFirstByName(String name);

	// Supports native JSON query string
	@Query("{domain:'?0'}")
	Appliance findCustomByName(String name);

	@Query("{domain: { $regex: ?0 } })")
	List<Appliance> findCustomByRegExDomain(String name);

}
