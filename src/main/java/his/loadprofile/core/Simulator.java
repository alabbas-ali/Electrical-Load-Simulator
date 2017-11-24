package his.loadprofile.core;

import his.loadprofile.job.JobRunner;
import his.loadprofile.model.Household;
import his.loadprofile.model.LoadCurve;

public class Simulator {

	// this parameters are important to check the simulation progress
	private static final int NUMBER_OF_SECOUNDS = 86400;
	private int totalNumberOfSimulation;
	private JobRunner jobRunner;

	public Simulator(int totalNumberOfSimulation, JobRunner jobRunner) {
		this.totalNumberOfSimulation = totalNumberOfSimulation;
		this.jobRunner = jobRunner;
	}

	public LoadCurve simulate(Household house, int currentHouseNumber) {

		// @Todo calculations go here , the calculation should conseder all the
		// configuration and the house

		try {
			for (int i = 1; i <= NUMBER_OF_SECOUNDS; i++) {
				Thread.sleep(1);

				// System.out.println(" [ Progress in Secand ] " + i + " [ is ] "
				// + (int) ((currentHouseNumber * i * 100) / (NUMBER_OF_SECOUNDS *
				// totalNumberOfSimulation)));
				jobRunner.progress
						.set((int) (((currentHouseNumber * i * 100) / (NUMBER_OF_SECOUNDS * totalNumberOfSimulation))
								+ ((100 / totalNumberOfSimulation) * (currentHouseNumber-1))));
				jobRunner.sendProgress();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new LoadCurve();
	}

}
