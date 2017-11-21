package his.loadprofile.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import his.loadprofile.model.SimConfig;

public interface SimConfigReopsitory extends MongoRepository<SimConfig, Long>{
	
	SimConfig findFirstByName(String name);

	// Supports native JSON query string
	@Query("{domain:'?0'}")
	SimConfig findCustomByName(String name);

	@Query("{domain: { $regex: ?0 } })")
	List<SimConfig> findCustomByRegExName(String name);

}
