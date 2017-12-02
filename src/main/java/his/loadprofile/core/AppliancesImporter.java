package his.loadprofile.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import his.loadprofile.core.csv.CSVReader;
import his.loadprofile.model.Appliance;

public class AppliancesImporter extends CSVReader{
	
	private static final String APPLIANCES_FILE_LOCATION = "input/appliances.csv";
	private static final int NAME_COL = 0;
	private static final int POWER_ON_COL = 6;
	private static final int POWER_OFF_COL = 7;
	private static final int CYCLE_TIME_COL = 5;
	private static final int RESTART_DELAY_COL = 8;
	private static final int SCALE_FACTOR_COL = 20;
	

	public List<Appliance> importData()
	{
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(APPLIANCES_FILE_LOCATION).getFile());
		List<Appliance> appliances = new ArrayList<Appliance>(); 
		Appliance tmp;
		Random random = new Random();
		
		if(this.read(file)) {
			for (int row = 0; row < this.getLineNymber() ; row++) {
				
				tmp = new Appliance();
				
				System.out.println("Device name = " + getString(row, NAME_COL));
				System.out.println("POWER_ON = " + (int) getDouble(row, POWER_ON_COL));
				System.out.println("POWER_OFF = " + (int) getDouble(row, POWER_OFF_COL));
				System.out.println("cycleTime = " + (int) getDouble(row, CYCLE_TIME_COL));
				System.out.println("RESTART_DELAY = " + (int) getDouble(row, RESTART_DELAY_COL));
				System.out.println("SCALE_FACTOR = " + getDouble(row, SCALE_FACTOR_COL));
				System.out.println("RESTART_DELAY = " + (int) getDouble(row, RESTART_DELAY_COL));
				System.out.println("POWER_ON_DEVIATION POWER_ON_DEVIATION = " + random.nextDouble());
	 
				appliances.add(tmp);
			}
		}
		
		return appliances;
	}
	

}
