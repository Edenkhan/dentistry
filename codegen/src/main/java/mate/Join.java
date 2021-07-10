package mate;

public class Join {
    private Entity entity;
    private String targetName;
    private String propertyName;
    private String[] joinPropertyNames;

    public Join(Entity entity, String targetName, String propertyName, String[] joinPropertyNames) {
        this.entity = entity;
        this.targetName = targetName;
        this.propertyName = propertyName;
        this.joinPropertyNames = joinPropertyNames;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getTargetName() {
        return targetName;
    }

    public String[] getJoinPropertyNames() {
        return joinPropertyNames;
    }

    public String getPropertyName() {
        return propertyName;
    }
}