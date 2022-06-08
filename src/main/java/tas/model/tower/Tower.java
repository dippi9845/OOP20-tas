package main.java.tas.model.tower;

import main.java.tas.model.Entity;
import main.java.tas.utils.Position;
import java.awt.Dimension;

/**
 * An Interface that model a generic Tower, that extends Entity and Runnable
 * 
 * Extends Entity: so can be drawable Extends Runnable: so every tower can run
 * concurrently
 */
public interface Tower extends Entity, Runnable {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Dimension getBodyDimension() {
		return new Dimension(100, 100);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Position getPosition() {
		return this.getPos();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default String getImageName() {
		return this.getTowerImageName();
	}

	/**
	 * Method used by thread, to run the tower in concurrency
	 */
	default void run() {
		try {
			this.compute();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method implements the behavior of the tower, at every wake up of the
	 * Thread
	 * 
	 * @throws InterruptedException, if some error occurs during Thread.sleep
	 */
	public void compute() throws InterruptedException;

	/**
	 * @return the damage of the tower
	 */
	public int getDamage();

	/**
	 * @return the radius of the tower
	 */
	public int getRadius();

	/**
	 * @return the cost of the tower
	 */
	public int getCost();

	/**
	 * @return the delay of the tower
	 */
	public int getDelay();

	/**
	 * @return the name of the image linked to the tower
	 */
	public String getTowerImageName();

	/**
	 * @return the position of the tower
	 */
	public Position getPos();
}
