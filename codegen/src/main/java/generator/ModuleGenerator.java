package generator;

import main.Entities;

public interface ModuleGenerator {
	public void generate(Entities entities);

	public String getName();
}
