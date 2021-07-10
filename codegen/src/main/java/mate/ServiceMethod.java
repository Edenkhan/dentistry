package mate;

import java.util.HashMap;
import java.util.Map;

public class ServiceMethod {
	private String name;
	private String returnType;
	private Map<String, String> args = new HashMap<String, String>();
	private String description;
	
	public ServiceMethod setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public String getDescription() {
		return description;
	}
	
	public ServiceMethod(String returnType, String name) {
		super();
		this.name = name;
		this.returnType = returnType;
	}
	
	
	public ServiceMethod addArg(String type, String name){
		args.put(type, name);
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public Map<String, String> getArgs() {
		return args;
	}

	public void setArgs(Map<String, String> args) {
		this.args = args;
	}
}
