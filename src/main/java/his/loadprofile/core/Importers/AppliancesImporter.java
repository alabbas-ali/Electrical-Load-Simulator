package his.loadprofile.core.Importers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import his.loadprofile.core.ApplianceType;
import his.loadprofile.model.Appliance;
import his.loadprofile.model.OperationalMode;

public class AppliancesImporter extends CSVReader implements AppliancesImporterInterface{

	private static final String APPLIANCES_FILE_LOCATION = "input/appliances.csv";
	private static final int NAME_COL = 0;
	private static final int POWER_ON_COL = 6;
	private static final int POWER_OFF_COL = 7;
	private static final int CYCLE_TIME_COL = 5;
	private static final int RESTART_DELAY_COL = 8;
	private static final int SCALE_FACTOR_COL = 20;

	public List<Appliance> importData() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(APPLIANCES_FILE_LOCATION).getFile());
		List<Appliance> appliances = new ArrayList<Appliance>();
		Appliance tmp;
		List<OperationalMode> oplist;
		OperationalMode op;

		if (this.read(file)) {
			for (int row = 0; row < this.getLineNymber(); row++) {

				tmp = new Appliance();

				tmp.setName(getString(row, NAME_COL));
				tmp.setDescription("This is Appliance " + tmp.getName() + " from import operation.");
				tmp.setCreationDate(new Date());
				tmp.setType(this.getType(getString(row, NAME_COL)));
				
				oplist = new ArrayList<OperationalMode>();
				op = new OperationalMode();
				op.setName("Op mode 1");
				op.setDescription("This is an imported operational mode for Appliance " + tmp.getName());
				op.setPowerInputOn((int) getDouble(row, POWER_ON_COL));
				op.setPowerInputOff((int) getDouble(row, POWER_OFF_COL));
				op.setDuration((int) getDouble(row, CYCLE_TIME_COL));
				op.setRestartDelay((int) getDouble(row, RESTART_DELAY_COL));
				op.setScaleFactor(getDouble(row, SCALE_FACTOR_COL));
				oplist.add(op);
				tmp.setOperationalModes(oplist);
				appliances.add(tmp);
			}
		}

		return appliances;
	}

	private ApplianceType getType(String name) {

		switch (name) {
		case "Chest freezer":
		case "Upright freezer":
		case "Fridge freezer":
			return ApplianceType.APPLIANCE_FRIDGE_FREEZER;
		case "Refrigerator":
			return ApplianceType.APPLIANCE_FREADGE;
		case "Answer machine":
			return ApplianceType.APPLIANCE_ANSWER_MACHEN;
		case "Cassette / CD Player":
			return ApplianceType.APPLIANCE_CD_PLAYER;
		case "Clock":
			return ApplianceType.APPLIANCE_CLOCK;
		case "Cordless telephone":
			return ApplianceType.APPLIANCE_TELEPHONE;
		case "Hi-Fi":
			return ApplianceType.APPLIANCE_HIFI;
		case "Iron":
			return ApplianceType.APPLIANCE_IRON;
		case "Vacuum":
			return ApplianceType.APPLIANCE_VACUUM;
		case "Fax":
			return ApplianceType.APPLIANCE_FAX;
		case "Personal computer":
			return ApplianceType.APPLIANCE_COMPUTER;
		case "Printer":
			return ApplianceType.APPLIANCE_PRINTER;
		case "TV 1":
		case "TV 2":
		case "TV 3":
			return ApplianceType.APPLIANCE_TV;
		case "VCR / DVD":
			return ApplianceType.APPLIANCE_DVD_PLAYER;
		case "TV Receiver box":
			return ApplianceType.APPLIANCE_TV_RECEIVER;
		case "Hob":
			return ApplianceType.APPLIANCE_HOB;
		case "Oven":
			return ApplianceType.APPLIANCE_OVEN;
		case "Microwave":
			return ApplianceType.APPLIANCE_MICROWAVE;
		case "Kettle":
			return ApplianceType.APPLIANCE_KETTLE;
		case "Small cooking (group)":
			return ApplianceType.APPLIANCE_OTHER;
		case "Dish washer":
			return ApplianceType.APPLIANCE_DISH_WASHER;
		case "Tumble dryer":
			return ApplianceType.APPLIANCE_TUMBLE_DRYER;
		case "Washing machine":
			return ApplianceType.APPLIANCE_WASHING_MACHINE;
		case "Washer dryer":
			return ApplianceType.APPLIANCE_WASHER_DRYER;
		case "DESWH":
			return ApplianceType.APPLIANCE_DESWH;
		case "E-INST":
			return ApplianceType.APPLIANCE_E_INST;
		case "Electric shower":
			return ApplianceType.APPLIANCE_ELECTRIC_SHOWER;
		case "Storage heaters":
			return ApplianceType.APPLIANCE_STORAGE_HEATER;
		case "Other electric space heating":
			return ApplianceType.APPLIANCE_OTHER_HEATER;
		default:
			break;
		}
		return null;
	}

}
