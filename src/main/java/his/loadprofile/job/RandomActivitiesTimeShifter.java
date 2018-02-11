package his.loadprofile.job;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import his.loadprofile.core.ActivityType;
import his.loadprofile.model.Activity;
import his.loadprofile.model.Availability;

public class RandomActivitiesTimeShifter implements ActivitiesTimeShifter{
	
	static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
	
	private double mean;
	private double standDev;
	
	
	@Override
	public List<Availability> shift(List<Availability> availabilitiesList) {
		
		Random r = new Random();
		Calendar cal = new GregorianCalendar();
		long t;
		Date afterAddingTenMins;
		List<Activity> activityList;
		
		for (Availability availability : availabilitiesList) {
			
			activityList = availability.getActivities();
			
			for (int i=0;i< activityList.size();i++) {
				this.getActivityMeanValue(activityList.get(i).getType());
				double normalRandomNumber = r.nextGaussian() * this.standDev + this.mean;
				
				cal.setTime(activityList.get(i).getStart());
				t = cal.getTimeInMillis();
				afterAddingTenMins = new Date((long) (t + (normalRandomNumber * ONE_MINUTE_IN_MILLIS)));
				activityList.get(i).setStart(afterAddingTenMins);
				
				cal.setTime(activityList.get(i).getEnd());
				t = cal.getTimeInMillis();
				afterAddingTenMins = new Date((long) (t + (normalRandomNumber * ONE_MINUTE_IN_MILLIS)));
				activityList.get(i).setEnd(afterAddingTenMins);
			}
			
			availability.setActivities(activityList);
		}
		
			
		return availabilitiesList;
	}
	
	private void getActivityMeanValue(ActivityType type) {
		
		switch (type) {
		case ACTIVITY_WAKUP:
			this.mean = 7;
			this.standDev = 0.5;
			break;
		case ACTIVITY_SLEEP:
			this.mean = 23.30;
			this.standDev = 0.25;
			break;
		case ACTIVITY_GOOUT:
			this.mean = 8;
			this.standDev = 0.5;
			break;
		case ACTIVITY_BACK_HOME:
			this.mean = 18;
			this.standDev = 0.5;
			break;
		case ACTIVITY_MORNING_COURSE:
			this.mean = 7.15;
			this.standDev = 0.5;
			break;
		case ACTIVITY_GETTING_READY:
			this.mean = 7.45;
			this.standDev = 0.5;
			break;
		case ACTIVITY_CLEANING_HOME:
			this.mean = 11.30;
			this.standDev = 0.25;
			break;
		case ACTIVITY_COOKING:
			this.mean = 19.30;
			this.standDev = 0.5;
			break;
		case ACTIVITY_COFFEE_MAKING:
			this.mean = 7.30;
			this.standDev = 0.5;
			break;
		case ACTIVITY_TOASTING_BREAD:
			this.mean = 7.30;
			this.standDev = 0.75;
			break;
		case ACTIVITY_WASHING_DISHES:
			this.mean = 19.30;
			this.standDev = 1.5;
			break;
		case ACTIVITY_WASHCLOTHES:
			this.mean = 19.45;
			this.standDev = 1.5;
			break;
		case ACTIVITY_IRONING:
			this.mean = 20.30;
			this.standDev = 0.75;
			break;
		case ACTIVITY_WATCHING_TV:
			this.mean = 20;
			this.standDev = 0.75;
			break;
		case ACTIVITY_WATCHING_MOVIE:
			this.mean = 20;
			this.standDev = 0.75;
			break;
		case ACTIVITY_COMPUTING:
			this.mean = 19.30;
			this.standDev = 0.75;
			break;
		case ACTIVITY_PRINTING:
			this.mean = 20.30;
			this.standDev = 2.5;
			break;
		case ACTIVITY_TELEPHONING:
			this.mean = 21.30;
			this.standDev = 1.5;
			break;
		case ACTIVITY_HEATING_VENTILATION:
			this.mean = 21.45;
			this.standDev = 1.5;
			break;
		case ACTIVITY_OTHERS:
			this.mean = 20.45;
			this.standDev = 1.5;
			break;
			default:
				break;
		}
		
	}

}
