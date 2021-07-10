package generator;

import mate.Entity;

public interface Generator {
	void generate(Entity entity);
	String getName();
}
