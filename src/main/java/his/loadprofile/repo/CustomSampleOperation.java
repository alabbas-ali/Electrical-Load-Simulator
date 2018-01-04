package his.loadprofile.repo;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CustomSampleOperation implements AggregationOperation {
	private int size;
	
    public CustomSampleOperation(int size){
        this.size = size;   
    }

    @Override
    public DBObject toDBObject(final AggregationOperationContext context){
        return new BasicDBObject("$sample", new BasicDBObject("size", size));
    }
}