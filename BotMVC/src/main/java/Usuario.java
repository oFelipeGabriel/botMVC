
public class Usuario {
	private Long id;
	private boolean temTime = false;
	private String time;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isTemTime() {
		return temTime;
	}
	public void setTemTime(boolean temTime) {
		this.temTime = temTime;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
