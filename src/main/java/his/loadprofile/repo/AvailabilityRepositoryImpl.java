package his.loadprofile.repo;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import his.loadprofile.core.AvailabilityType;
import his.loadprofile.model.Availability;


public class AvailabilityRepositoryImpl implements AvailabilityRepositoryCustom{
	
	@Autowired
    MongoTemplate mongoTemplate;
	
	@Override
	public Availability findOneRandomlyByType(AvailabilityType type) {
		
		SampleOperation matchStage = Aggregation.sample(1);

		Aggregation agg = newAggregation(
				matchStage,
				match(Criteria.where("type").is(type))
			);

		List<Availability> result = new ArrayList<Availability>();
		int t = 0;
		//Convert the aggregation result into a List
		while(result.size() == 0 && t < 20) {
			AggregationResults<Availability> groupResults
				= mongoTemplate.aggregate(agg, Availability.class, Availability.class);
			result = groupResults.getMappedResults();
			t ++;
		}
		if(result.size()>0) {
			return result.get(0);
		}
		return null;
	}

}
