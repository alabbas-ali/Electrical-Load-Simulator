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
				this.mean = 15;
				this.standDev = 0.5;
				break;
			case ACTIVITY_PREACKFAST:
				this.mean = 15;
				this.standDev = 0.5;
				break;
			case ACTIVITY_BACK:
				this.mean = 15;
				this.standDev = 0.5;
				break;
			case ACTIVITY_GOOUT:
				this.mean = 15;
				this.standDev = 0.5;
				break;
			case ACTIVITY_SLEEP:
				this.mean = 15;
				this.standDev = 0.5;
				break;
			case ACTIVITY_WACHCLOTHES:
				this.mean = 15;
				this.standDev = 0.5;
				break;
			default:
				break;
		}
		
	}

}
