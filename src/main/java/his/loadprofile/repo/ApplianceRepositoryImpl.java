package his.loadprofile.repo;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import his.loadprofile.core.ApplianceType;
import his.loadprofile.model.Appliance;

public class ApplianceRepositoryImpl implements ApplianceRepositoryCustom{
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	@Override
	public Appliance findOneRandomlyByType(ApplianceType type) {
		
		Aggregation agg = newAggregation(
				new CustomSampleOperation(1),
				match(Criteria.where("type").gte(type))
			);

		//Convert the aggregation result into a List
		AggregationResults<Appliance> groupResults
				= mongoTemplate.aggregate(agg, Appliance.class, Appliance.class);
		List<Appliance> result = groupResults.getMappedResults();
		
		if(result.size()>0) {
			return result.get(0);
		}
		return null;
	}

}
