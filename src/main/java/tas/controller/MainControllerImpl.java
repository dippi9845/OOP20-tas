package main.java.tas.controller;

import main.java.tas.view.MainView;
import main.java.tas.view.SceneActionObserver;
import main.java.tas.view.scene.GameSceneImpl;
import main.java.tas.view.scene.GenericScene;
import main.java.tas.view.scene.LevelSelectSceneImpl;
import main.java.tas.view.scene.MainMenuSceneImpl;
import main.java.tas.view.scene.SandboxModeScene;
import main.java.tas.view.scene.ActionScene;
import main.java.tas.view.scene.SettingsSceneImpl;
import main.java.tas.model.GameModelImpl;
import main.java.tas.model.GameSpecs;
import main.java.tas.model.MenuModel;
import main.java.tas.model.MenuModelImpl;
import main.java.tas.utils.LevelHandler;

/**
 * Class that implements {@link MainController}.
 */
public class MainControllerImpl implements MainController {

	private SceneController sceneController;
	private GenericScene scene;
	private GameSpecs gameSpecs = new GameSpecs();

	private int playerHealth = 100;
	private int playerMoney = 1000;

	private MainView mainView;
	private MenuModel menuModel;
	private int currentMenuMode = 1;

	/**
	 * Constructor that creates the main controller of the game
	 */
	public MainControllerImpl() {
		this.menuModel = new MenuModelImpl();
		this.mainView = new MainView();
		this.sceneController = createMenu(this.mainView);
		this.mainView.show();
	}

	/** {@inheritDoc} */
	@Override
	public SceneController createMenu(final MainView view) {
		this.scene = new MainMenuSceneImpl(view.getPanel());
		SceneController controller = new MainMenuController(this.scene, this.menuModel);
		((ActionScene)this.scene).setActionObserver((SceneActionObserver)controller);
		return controller;
	}
	
	/**
	 * Connects the level select model, with it's own view.
	 * 
	 * @param view the main window
	 * @return the scene that was created
	 */
	public SceneController createLevelSelect(final MainView view) {
		this.scene = new LevelSelectSceneImpl(view.getPanel(), this.menuModel);
		SceneController controller = new LevelSelectController(this.scene, this.menuModel);
		((ActionScene)this.scene).setActionObserver((SceneActionObserver)controller);
		LevelHandler.deleteUserLevels();
		return controller;
		
	}
	
	/**
	 * Connects the sandbox mode model, with it's own view.
	 * 
	 * @param view the main window
	 * @return the scene that was created
	 */
	public SceneController createSandBoxMode(final MainView view) {
		this.scene = new SandboxModeScene(view.getPanel(), this.menuModel);
		SceneController controller = new SandboxModeController(this.scene, this.menuModel);
		this.scene.setActionObserver(controller);
		this.scene.setMouseObserver(controller);
		return controller;
	}
	
	/**
	 * Connects the settings model, with it's own view.
	 * 
	 * @param view the main window
	 * @return the scene that was created
	 */
	public SceneController createSettings(final MainView view) {
		this.scene = new SettingsSceneImpl(view.getPanel(), this.menuModel);
		SceneController controller = new SettingsController(this.scene, this.menuModel);
		this.scene.setActionObserver(controller);
		return controller;
	}

	/** {@inheritDoc} */
	@Override
	public SceneController createGame(final MainView view) {
		this.scene = new GameSceneImpl(view.getPanel());
		SceneController controller = new GameController(((GameSceneImpl) this.scene),
		        new GameModelImpl(this.playerHealth, this.playerMoney),
		        LevelHandler.readLevel("level" + Integer.toString(this.menuModel.getCurrentLevel())));
		this.scene.setActionObserver(controller);
		this.scene.setMouseObserver(controller);
		return controller;
	}

	/** {@inheritDoc} */
	@Override
	public SceneController getController() {
		return this.sceneController;
	}
	
	/**
	 * Checks if the current window is correct and if not it closes the current window
	 * and opens the correct one
	 */
	private void updateCurrentMode() {
		// I check if the currentMenuMode has changed and if it has I update it and open
		// the new window
		if (this.currentMenuMode != this.menuModel.getMainScene()) {
			this.currentMenuMode = this.menuModel.getMainScene();
			if (this.currentMenuMode == 1) {
				this.mainView.dispose();
				this.mainView = new MainView();
				this.mainView.show();
				this.sceneController = createMenu(this.mainView);
			}
			if (this.currentMenuMode == 2) {
				this.mainView.dispose();
				this.mainView = new MainView();
				this.mainView.show();
				this.sceneController = createGame(this.mainView);
			}
			if (this.currentMenuMode == 3) {
				this.mainView.dispose();
				this.mainView = new MainView();
				this.mainView.show();
				this.sceneController = createLevelSelect(this.mainView);
			}
			if (this.currentMenuMode == 5) {
				this.mainView.dispose();
				this.mainView = new MainView();
				this.mainView.show();
				this.sceneController = createSettings(this.mainView);
			}
			if (this.currentMenuMode == 6) {
				this.mainView.dispose();
				this.mainView = new MainView();
				this.mainView.show();
				this.sceneController = createSandBoxMode(this.mainView);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mainLoop() {
		double next_game_tick = System.currentTimeMillis();
		double last_frame_time = System.currentTimeMillis();
		int loops;
		while (this.currentMenuMode != 4) {
			loops = 0;

			while (System.currentTimeMillis() > next_game_tick && loops < this.gameSpecs.getMaxFrameSkip()) {
				this.sceneController.nextTick();
				updateCurrentMode();
				this.mainView.update();

				next_game_tick += this.gameSpecs.getSkipTicks();
				loops++;
			}

			if (System.currentTimeMillis() - last_frame_time > 1000) {
				last_frame_time = System.currentTimeMillis();
			}

		}
		
		this.mainView.destroyView();

	}

	/**
	 * The main method that starts the game.
	 * 
	 * @param args not used
	 */
	public static void main(final String[] args) {
		new MainControllerImpl().mainLoop();
	}
}
