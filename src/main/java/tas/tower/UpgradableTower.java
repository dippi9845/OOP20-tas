package main.java.tas.tower;

public interface UpgradableTower extends Tower {
	
	public int getLevel();

	public int costUpgrade();

	public boolean upgradable(final int money);

	public void upgradeDamage();
	
}
