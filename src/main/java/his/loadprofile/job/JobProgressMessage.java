package his.loadprofile.job;

public class JobProgressMessage {

	private String jobName;
    private String state;
    private int progress;

    public JobProgressMessage(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public String getState() {
        return state;
    }

    public int getProgress() {
        return progress;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
