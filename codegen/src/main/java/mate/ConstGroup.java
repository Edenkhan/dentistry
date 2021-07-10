package mate;


public class ConstGroup{

	private boolean index;

	public ConstGroup(int method, String name, String description, Const... consts) {
		this.method = method;
		this.name = name;
		this.description = description;
		this.consts = consts;
	}

	public static final int METHOD_MOD = 0;
	public static final int METHOD_TYPE = 1;
	public static final int METHOD_STATE = METHOD_TYPE;

	protected int method;
	private String name;
	protected String description;
	protected Const[] consts;
	protected boolean include = true;
	protected boolean exclude = true;
	protected boolean update = false;
	protected boolean insert = true;
	protected boolean getter = true;
	protected boolean setter = true;
	protected boolean validSetter = false;

	public ConstGroup setValidSetter(boolean validSetter) {
		this.validSetter = validSetter;
		return this;
	}

	public boolean isValidSetter() {
		return validSetter;
	}

	public ConstGroup setUpdate(boolean update) {
		this.update = update;
		return this;
	}
	
	public boolean isUpdate() {
		return update || method == METHOD_MOD;
	}
	
	public ConstGroup setInsert(boolean insert) {
		this.insert = insert;
		return this;
	}
	
	public boolean isInsert() {
		return insert;
	}
	
	public ConstGroup setGetter(boolean getter) {
		this.getter = getter;
		return this;
	}
	
	public boolean isGetter() {
		return getter;
	}
	
	public ConstGroup setSetter(boolean setter) {
		this.setter = setter;
		return this;
	}
	
	public boolean isSetter() {
		return setter;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public ConstGroup setExclude(boolean exclude) {
		this.exclude = exclude;
		return this;
	}
	
	public ConstGroup setInclude(boolean include) {
		this.include = include;
		return this;
	}
	
	public boolean isInclude() {
		return include;
	}
	
	public boolean isExclude() {
		return exclude;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Const[] getConsts() {
		return consts;
	}
	
	public void setConsts(Const[] consts) {
		this.consts = consts;
	}

	public static int getMethodMod() {
		return METHOD_MOD;
	}

	public static int getMethodType() {
		return METHOD_TYPE;
	}

	public ConstGroup setIndex(boolean index) {
		this.index = index;
		return this;
	}

	public boolean isIndex() {
		return index;
	}
}
