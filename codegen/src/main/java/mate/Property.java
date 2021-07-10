package mate;


import java.util.ArrayList;
import java.util.List;

public class Property extends Basic {

	private String encryptionKey;
	private boolean uniqueIndex;
	private boolean index;
	private boolean notEqualSearchable;
	private boolean nullOrEqualTo;
	private String[] uniqueIndexWith;
	private String[] indexWith;
	private String databaseDataType;
	private List<Join> joins = new ArrayList<>();

	public Property(Entity entity, String name, String description) {
		super(name, description);
		setType(Project.getInstance().getEntityClassName(entity));
	}

	public Property(String type, String name, String description) {
		super(name, description);
		setType(type);
	}

	public Property(Class<?> type, String name, String description) {
		super(name, description);
		setType(type.getName());
	}

	protected String type;
	protected boolean update = false;
	protected boolean insert = true;
	protected boolean getter = true;
	protected boolean setter = true;
	protected boolean highPrecision = false;
	protected boolean encrypt = false;
	protected boolean nullSearchale = false;
	protected boolean addOperation = false;
	protected boolean subtractOperation = false;
	protected boolean sortable = false;
	protected boolean large = false;
	protected boolean searchable = false;				// 创建 Query 类、Mapper XML用
	protected boolean likeSearchable = false;			// 创建 Query 类、Mapper XML用
	protected Entity joinEntity;
	protected String[] joinNames;
	private boolean groupable;

	public boolean isGroupable() {
		return groupable;
	}

	public Property setHighPrecision(boolean highPrecision) {
		this.highPrecision = highPrecision;
		return this;
	}

	public boolean isHighPrecision() {
		return highPrecision;
	}
	
	public Property setNullSearchale(boolean nullSearchale) {
		this.nullSearchale = nullSearchale;
		return this;
	}
	
	public boolean isNullSearchale() {
		return nullSearchale;
	}

	public Entity getJoinEntity() {
		return joinEntity;
	}

	public String[] getJoinNames() {
		return joinNames;
	}

	public Property setAddOperation(boolean addOperation) {
		this.addOperation = addOperation;
		return this;
	}

	public Property setSubtractOperation(boolean subtractOperation) {
		this.subtractOperation = subtractOperation;
		return this;
	}

	public Property setGetter(boolean getter) {
		this.getter = getter;
		return this;
	}

	public Property setSetter(boolean setter) {
		this.setter = setter;
		return this;
	}

	public boolean isAddOperation() {
		return addOperation;
	}

	public boolean isSubtractOperation() {
		return subtractOperation;
	}

	public boolean isGetter() {
		return getter;
	}

	public boolean isSetter() {
		return setter;
	}

	/**
	 * 可被查询
	 * @param searchable
	 * @return
	 */
	public Property setSearchable(boolean searchable) {
		this.searchable = searchable;
		return this;
	}

	/**
	 *可被模糊查询
	 * @param likeSearchable
	 * @return
	 */
	public Property setLikeSearchable(boolean likeSearchable) {
		this.likeSearchable = likeSearchable;
		return this;
	}

	public boolean isLikeSearchable() {
		return likeSearchable;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public String getType() {
		return type;
	}

	public Property setType(String type) {
		this.type = type;
		return this;
	}

	public boolean isUpdate() {
		return update;
	}

	/**
	 * 可更新
	 * @param update
	 * @return
	 */
	public Property setUpdate(boolean update) {
		this.update = update;
		return this;
	}

	public boolean isInsert() {
		return insert;
	}

	public Property setInsert(boolean insert) {
		this.insert = insert;
		return this;
	}

	public boolean isSortable() {
		return sortable;
	}

	public Property setSortable(boolean sortable) {
		this.sortable = sortable;
		return this;
	}

	public boolean isLarge() {
		return large;
	}

	public Property setLarge(boolean large) {
		this.large = large;
		return this;
	}

	/**
	 * 设置连接
	 * @param entity
	 * @param names
	 * @return
	 */
	public Property setJoin(Entity entity, String... names) {
		this.joinEntity = entity;
		this.joinNames = names;
		return this;
	}

	/**
	 * 设置分组
	 * @param b
	 * @return
	 */
	public Property setGroupable(boolean b) {
		this.groupable = b;
		return this;
	}

	public boolean isEncrypt() {
		return encrypt;
	}

	public Property setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
		return this;
	}

	public Property setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
		return this;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public Property setUniqueIndex(boolean uniqueIndex, String... uniqueIndexWith) {
		this.uniqueIndex = uniqueIndex;
		this.uniqueIndexWith = uniqueIndexWith;
		return this;
	}

	public boolean isUniqueIndex() {
		return uniqueIndex;
	}

	public Property setIndex(boolean index, String... indexWith) {
		this.index = index;
		this.indexWith = indexWith;
		return this;
	}

	public boolean isIndex() {
		return index;
	}

	public Property setNotEqualSearchable(boolean notEqualSearchable) {
		this.notEqualSearchable = notEqualSearchable;
		return this;
	}

	public boolean isNotEqualSearchable() {
		return notEqualSearchable;
	}

	public Property setNullOrEqualTo(boolean nullOrEqualTo) {
		this.nullOrEqualTo = nullOrEqualTo;
		return this;
	}

	public boolean isNullOrEqualTo() {
		return nullOrEqualTo;
	}

	public String[] getUniqueIndexWith() {
		return uniqueIndexWith;
	}

	public String[] getIndexWith() {
		return indexWith;
	}

    public Property setDatabaseDataType(String databaseDataType) {
        this.databaseDataType = databaseDataType;
        return this;
    }

    public String getDatabaseDataType() {
        return databaseDataType;
    }

	public List<Join> getJoins() {
		return joins;
	}

	public Property addJoin(Entity entity, String targetName, String propertyName, String[] joinPropertyNames) {
		joins.add(new Join(entity, targetName, propertyName, joinPropertyNames));
		return this;
	}
}
