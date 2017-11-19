package his.loadprofile.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sim_config")
public class SimConfig {

	@Id
	private long id;

	@Indexed(unique = true)
	private String name;
	
	private int numberOfHouses;
	
	private int minNumberOfPeople;
	
	private int maxNumberOfPeople;
	
	private int sengelsPrecentage;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfHouses() {
		return numberOfHouses;
	}

	public void setNumberOfHouses(int numberOfHouses) {
		this.numberOfHouses = numberOfHouses;
	}

	public int getMinNumberOfPeople() {
		return minNumberOfPeople;
	}

	public void setMinNumberOfPeople(int minNumberOfPeople) {
		this.minNumberOfPeople = minNumberOfPeople;
	}

	public int getMaxNumberOfPeople() {
		return maxNumberOfPeople;
	}

	public void setMaxNumberOfPeople(int maxNumberOfPeople) {
		this.maxNumberOfPeople = maxNumberOfPeople;
	}

	public int getSengelsPrecentage() {
		return sengelsPrecentage;
	}

	public void setSengelsPrecentage(int sengelsPrecentage) {
		this.sengelsPrecentage = sengelsPrecentage;
	}
	
	

}
