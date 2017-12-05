package his.loadprofile.repo;


import org.springframework.data.mongodb.repository.MongoRepository;

import his.loadprofile.model.Appliance;

public interface ApplianceRepository extends MongoRepository<Appliance, String> {


}
