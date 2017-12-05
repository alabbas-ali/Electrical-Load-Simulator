package his.loadprofile.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import his.loadprofile.model.SimConfig;

public interface SimConfigReopsitory extends MongoRepository<SimConfig, String>{
	
	@Query("{name: { $regex: ?0 } })")
	List<SimConfig> findCustomByRegExName(String name);

}
