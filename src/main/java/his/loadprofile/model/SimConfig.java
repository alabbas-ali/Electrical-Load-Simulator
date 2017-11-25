package his.loadprofile.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sim_config")
public class SimConfig {

	@Id
	private String id;
	
	@NotBlank(message = "Name can't empty!")
	@Indexed(unique = true)
	private String name;
	
	private Date date;
	
	private int numberOfHouses;
	
	private int minNumberOfPeople;
	
	private int maxNumberOfPeople;
	
	@NotNull(message = "Singles Percentage can't empty!")
	private int singlesPercentage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSinglesPercentage() {
		return singlesPercentage;
	}

	public void setSinglesPercentage(int singlesPercentage) {
		this.singlesPercentage = singlesPercentage;
	}
	
	
}
