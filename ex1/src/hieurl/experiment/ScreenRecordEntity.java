package hieurl.experiment;

public class ScreenRecordEntity {
	private String ScreenName;
	private int Counter;
	
	public ScreenRecordEntity() {
		this.ScreenName = "";
		this.Counter = 0;
	}
	
	public ScreenRecordEntity(String name) {
		this.ScreenName = name;
		this.Counter = 0;
	}
	
	public ScreenRecordEntity(String name, int count) {
		this.ScreenName = name;
		this.Counter = count;
	}
	
	public String getScreenName() {
		return this.ScreenName;
	}
	
	public void setScreenName(String name) {
		this.ScreenName = name;
	}
	
	public int getCount() {
		return this.Counter;
	}
	
	public void setCount(int count) {
		this.Counter = count;
	}
}
