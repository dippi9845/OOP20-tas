package main.java.tas.view;

import java.awt.BorderLayout;


import javax.swing.JPanel;

import main.java.tas.controller.SceneController;


public class MainMenuSceneImpl implements GameScene {
	
	private JPanel rootPanel;
	private MenuView menuView;
	
	/**
     * Constructor that set up the menu scene
     * @param root is the {@link JPanel} that will contain the scene
     */
	public MainMenuSceneImpl(JPanel rootPanelIn) {
		this.rootPanel = rootPanelIn;
		this.rootPanel.setLayout(new BorderLayout());
        
        this.menuView = new MenuView();
        
        this.rootPanel.add(this.menuView.getPanel(), BorderLayout.CENTER);
	}
	@Override
	public void setObserver(SceneController listener) {
		this.menuView.setObserver(listener);
	}

	public MenuView getMenuView() {
		
		return this.menuView;
	}
	


}
