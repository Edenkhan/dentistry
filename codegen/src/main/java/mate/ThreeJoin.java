package mate;

public class ThreeJoin {
	private Entity middle;
	private Join leftJoin;
	private Join rightJoin;


	public ThreeJoin(Join leftJoin, Entity middle, Join rightJoin) {
		super();
		this.leftJoin = leftJoin;
		this.middle = middle;
		this.rightJoin = rightJoin;
	}

	public ThreeJoin() {

	}

	public Entity getMiddle() {
		return middle;
	}

	public void setMiddle(Entity middle) {
		this.middle = middle;
	}

	public Join getLeftJoin() {
		return leftJoin;
	}

	public void setLeftJoin(String middleColumnName, Entity entity, String columnName){
		this.leftJoin = new Join(middleColumnName, entity, columnName);
	}

	public void setRightJoin(String middleColumnName, Entity entity, String columnName){
		this.rightJoin = new Join(middleColumnName, entity, columnName);
	}

	public void setLeftJoin(Join leftJoin) {
		this.leftJoin = leftJoin;
	}

	public Join getRightJoin() {
		return rightJoin;
	}

	public void setRightJoin(Join rightJoin) {
		this.rightJoin = rightJoin;
	}

	public static class Join {
		private String middleColumnName;
		private Entity entity;
		private String columnName;

		public Join(String middleColumnName, Entity entity, String columnName) {
			super();
			this.middleColumnName = middleColumnName;
			this.entity = entity;
			this.columnName = columnName;
		}

		public String getMiddleColumnName() {
			return middleColumnName;
		}

		public void setMiddleColumnName(String middleColumnName) {
			this.middleColumnName = middleColumnName;
		}

		public Entity getEntity() {
			return entity;
		}

		public void setEntity(Entity entity) {
			this.entity = entity;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
	}
}
