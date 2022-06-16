package main.java.tas.controller;

import java.awt.event.ActionListener;

import main.java.tas.model.menu.MenuModel;
import main.java.tas.view.scene.GenericScene;

/**
 * Class that creates a controller for the settings menu. Class that implements
 * {@link SceneActionObserver}.
 */
public class SettingsController implements SceneActionObserver {

	private ButtonListener listener = new ButtonListener();
	private GenericScene scene;
	private MenuModel model;

	/**
	 * Constructor that creates the settings menu controller, and connects it to its
	 * scene.
	 * 
	 * @param sceneIn  the settings menu scene
	 * @param theModel the model
	 */
	public SettingsController(GenericScene sceneIn, MenuModel theModel) {
		scene = sceneIn;
		scene.setObserver(this);
		this.model = theModel;
	}

	/**
	 * @return the model
	 */
	public MenuModel getModel() {
		return this.model;
	}

	/** {@inheritDoc} */
	@Override
	public void nextTick() {
		if (this.listener.checkUpdate()) {
			this.model.setMainScene(1);
			listener.resetUpdate();
		}

	}

	/** {@inheritDoc} */
	@Override
	public ActionListener getActionListener() {
		return this.listener;
	}
}
