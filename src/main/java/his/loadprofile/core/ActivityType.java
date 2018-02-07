package his.loadprofile.core;

import java.util.ArrayList;
import java.util.List;

public enum ActivityType {
	
	ACTIVITY_WAKUP,
	ACTIVITY_SLEEP,
	ACTIVITY_GOOUT,
	ACTIVITY_BACK,
	ACTIVITY_WACHCLOTHES {
        @Override
        public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_WASHING_MACHINE);
        	applianceTypes.add(ApplianceType.APPLIANCE_WASHER_DRYER);
            return applianceTypes;
        }
    },
	ACTIVITY_PREACKFAST{
    	@Override
    	public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_KETTLE);
        	applianceTypes.add(ApplianceType.APPLIANCE_STOVE);
            return applianceTypes;
        }
    },
	ACTIVITY_COOKING{
        @Override
        public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_OVEN);
            return applianceTypes;
        }
    };

	
	
	//@TODO list the rest of possible activities 
	
	public List<ApplianceType> asApplianceType() {
		// TODO Auto-generated method stub
		return null;
	}

}
