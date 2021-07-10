package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.Entities;
import mate.Entity;
import mate.ThreeJoin;

public abstract class AbstractEntities implements Entities {

	protected List<Entity> entites = new ArrayList<Entity>();
	protected List<ThreeJoin> threeJoins = new ArrayList<ThreeJoin>();

	@Override
	public Iterator<Entity> iterator() {
		return entites.iterator();
	}

	@Override
	public Entities addEntity(Entity entity) {
		entites.add(entity);
		return this;
	}


	@Override
	public void addThreeJoin(ThreeJoin threeJoin) {
		threeJoins.add(threeJoin);
	}

	@Override
	public List<ThreeJoin> getThreeJoins() {
		return threeJoins;
	}
}
