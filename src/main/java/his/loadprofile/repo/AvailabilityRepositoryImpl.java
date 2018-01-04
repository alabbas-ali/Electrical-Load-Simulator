package his.loadprofile.repo;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import his.loadprofile.core.AvailabilityType;
import his.loadprofile.model.Availability;


public class AvailabilityRepositoryImpl implements AvailabilityRepositoryCustom{
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	@Override
	public Availability findOneRandomlyByType(AvailabilityType type) {
		
		Aggregation agg = newAggregation(
				new CustomSampleOperation(1),
				match(Criteria.where("type").gte(type))
				);

		//Convert the aggregation result into a List
		AggregationResults<Availability> groupResults
				= mongoTemplate.aggregate(agg, Availability.class, Availability.class);
		List<Availability> result = groupResults.getMappedResults();

		if(result.size()>0) {
			return result.get(0);
		}
		return null;
	}

}
