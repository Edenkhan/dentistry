package main;

import java.util.List;

import mate.Entity;
import mate.ThreeJoin;

public interface Entities extends Iterable<Entity>{

	public Entities addEntity(Entity entity);

	public void addThreeJoin(ThreeJoin threeJoin);

	public List<ThreeJoin> getThreeJoins();

	public String getModuleName();
}
