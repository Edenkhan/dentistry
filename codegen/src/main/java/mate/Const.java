package mate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class Const extends Basic {
    protected String alias;

    public final static String DEFAULT_STYLE = "muted";

    public Const(String name, String alias, String description, String style) {
        super(name, description);
        setAlias(alias);
        setStyle(style);
    }

    public Const(String name, String alias, String description) {
        this(name, alias, description, DEFAULT_STYLE);
    }

    public Const(String name, String description) {
        this(
                name,
                StringUtils.uncapitalize(WordUtils.capitalizeFully(name, new char[]{'_'}).replaceAll("_", "")),
                description,
                DEFAULT_STYLE
        );
    }

    private String style;

    public String getStyle() {
        return style;
    }

    public Const setStyle(String style) {
        this.style = style;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }


}
