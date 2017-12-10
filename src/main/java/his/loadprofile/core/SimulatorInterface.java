package his.loadprofile.core;

import java.util.List;

import his.loadprofile.job.JobRunner;
import his.loadprofile.model.Household;
import his.loadprofile.model.Measurement;

public interface SimulatorInterface {
	
	public void setJobRunner(JobRunner jobRunner);
	
	public List<Measurement> simulate(Household house, int timeStep);

}
