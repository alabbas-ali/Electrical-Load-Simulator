package his.loadprofile.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sim_config")
public class SimConfig {

	@Id
	private long id;

	@Indexed(unique = true)
	private String name;
	
	private LocalDateTime date;
	
	private int numberOfHouses;
	
	private int minNumberOfPeople;
	
	private int maxNumberOfPeople;
	
	private int singlesPercentage;

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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getSinglesPercentage() {
		return singlesPercentage;
	}

	public void setSinglesPercentage(int singlesPercentage) {
		this.singlesPercentage = singlesPercentage;
	}
	
	
}
