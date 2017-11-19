package his.loadprofile.component;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import his.loadprofile.model.JobProgressMessage;

public abstract class JupRunner implements Runnable {

	private SimpMessagingTemplate template;
	public AtomicInteger progress = new AtomicInteger();
	public String state = "NEW";
	public String jobName;

	public JupRunner(SimpMessagingTemplate template) {
		this.template = template;
	}

	public void sendProgress() {
		JobProgressMessage temp = new JobProgressMessage(jobName);
		temp.setProgress(progress.get());
		temp.setState(state);
		template.convertAndSend("/topic/status", temp);
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
