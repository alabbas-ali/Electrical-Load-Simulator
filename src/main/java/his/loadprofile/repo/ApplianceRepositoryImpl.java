package his.loadprofile.repo;

import com.mongodb.WriteResult;

import his.loadprofile.model.Appliance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

//Impl postfix of the name on it compared to the core repository interface
public class ApplianceRepositoryImpl implements ApplianceRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    public int update(String name) {

        Query query = new Query(Criteria.where("domain").is(name));
        Update update = new Update();
        //update.set("displayAds", displayAds);

        WriteResult result = mongoTemplate.updateFirst(query, update, Appliance.class);

        if(result!=null)
            return result.getN();
        else
            return 0;

    }

}
