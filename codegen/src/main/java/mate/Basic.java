package mate;

public class Basic {
	
	protected String description;
	protected String name;
	
	public Basic(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
