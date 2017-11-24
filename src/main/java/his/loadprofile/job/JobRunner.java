package his.loadprofile.job;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public abstract class JobRunner implements Runnable {

	private SimpMessagingTemplate template;
	public AtomicInteger progress = new AtomicInteger();
	public String state = "NEW";
	public String jobName;

	public JobRunner(SimpMessagingTemplate template) {
		this.template = template;
	}

	public void sendProgress() {
		JobProgressMessage temp = new JobProgressMessage(jobName);
		temp.setProgress(progress.get());
		temp.setState(state);
		template.convertAndSend("/simulation/sim-status", temp);
	}

	public int getProgress() {
		return progress.get();
	}

	public String getState() {
		return state;
	}

	public String getJobName() {
		return jobName;
	}
}
