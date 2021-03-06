package his.loadprofile.core;

import java.util.ArrayList;
import java.util.List;

public enum ActivityType {
	
	ACTIVITY_WAKUP,
	ACTIVITY_SLEEP,
	ACTIVITY_GOOUT,
	ACTIVITY_BACK_HOME,
	ACTIVITY_MORNING_COURSE{
    	@Override
    	public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_ELECTRIC_SHOWER);
            return applianceTypes;
        }
    },
	ACTIVITY_GETTING_READY{
    	@Override
    	public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_HAIR_STRAIGHTNER);
        	applianceTypes.add(ApplianceType.APPLIANCE_HAIR_DRYER);
        	return applianceTypes;
        }
    },
	ACTIVITY_CLEANING_HOME{
    	@Override
    	public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_VACCUM_CLEANER);
        	return applianceTypes;
        }
    },
	ACTIVITY_COOKING{
    	@Override
    	public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_OVEN);
        	applianceTypes.add(ApplianceType.APPLIANCE_MICROWAVE);
        	applianceTypes.add(ApplianceType.APPLIANCE_INDUCTION_PLATES);
            return applianceTypes;
        }
    },
	ACTIVITY_COFFEE_MAKING{
    	@Override
    	public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_COFFEE_MAKER);
          	applianceTypes.add(ApplianceType.APPLIANCE_KETTLE);
        	return applianceTypes;
        }
    },
	ACTIVITY_TOASTING_BREAD{
    	@Override
    	public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_TOASTER);
        	return applianceTypes;
        }
    },
	ACTIVITY_WASHING_DISHES{
    	@Override
    	public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_DISH_WASHER);
            return applianceTypes;
        }
    },
	ACTIVITY_WASHCLOTHES {
        @Override
        public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_WASHING_MACHINE);
        	applianceTypes.add(ApplianceType.APPLIANCE_CLOTHES_DRYER);
            return applianceTypes;
        }
    },
	ACTIVITY_IRONING {
        @Override
        public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_IRON);
            return applianceTypes;
        }
    },
	ACTIVITY_WATCHING_TV{
    	@Override
        public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_TV);
        	applianceTypes.add(ApplianceType.APPLIANCE_TV_RECEIVER);
            return applianceTypes;
        }
    },
	ACTIVITY_WATCHING_MOVIE{
    	@Override
        public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_CD_PLAYER);
        	applianceTypes.add(ApplianceType.APPLIANCE_DVD_PLAYER);
            return applianceTypes;
        }
    },

	ACTIVITY_COMPUTING{
    	@Override
        public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_LAPTOP_CHARGER);
        	applianceTypes.add(ApplianceType.APPLIANCE_COMPUTER);
        	applianceTypes.add(ApplianceType.APPLIANCE_HIFI);
            return applianceTypes;
        }
    },
	ACTIVITY_PRINTING{
    	@Override
        public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_PRINTER);
            return applianceTypes;
        }
    },
	ACTIVITY_TELEPHONING{
    	@Override
        public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_TELEPHONE);
        	applianceTypes.add(ApplianceType.APPLIANCE_MOBILE_CHARGER); 
            return applianceTypes;
        }
    },
	ACTIVITY_HEATING_VENTILATION{
    	@Override
        public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_ELEC_SPACE_HEATING);
        	applianceTypes.add(ApplianceType.APPLIANCE_STORAGE_HEATER);
            return applianceTypes;
        }
    },
	ACTIVITY_OTHERS{
    	@Override
        public List<ApplianceType> asApplianceType() {
        	List<ApplianceType> applianceTypes = new ArrayList<ApplianceType>();
        	applianceTypes.add(ApplianceType.APPLIANCE_OTHER);
            return applianceTypes;
        }
    };	
	
	//@TODO list the rest of possible activities 
	
	public List<ApplianceType> asApplianceType() {
		// TODO Auto-generated method stub
		return null;
	}

}
