package main.java.tas.tower;

import main.java.tas.model.enemies.Enemy;
import main.java.tas.utils.Position;

public class BasicTower extends AbstractBasicTower {
	private Enemy target = null; // TODO cambiare con gli opzionali
	
	protected BasicTower(final Position pos, final int damage, final int radius, final int delay, final int cost) {
		super(pos, damage, radius, delay, cost);
	}

	@Override
	protected void attack() {
		this.target.dealDamage(this.getDamage());
	}

	@Override
	protected void setTarget(final Enemy e) {
		this.target = e;
	}

	@Override
	public void compute() {
		if (Towers.isTargetInRange(this.target, this)) {
			this.attack();
			// TODO sleep
		}
		else {
			Towers.findFirstEnemyInRange(this).ifPresent(this::setTarget);
		}

	}

}