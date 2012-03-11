/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkygames.st.control;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.monkygames.st.game.IGame;
import com.monkygames.st.game.Score;
import com.monkygames.st.io.ScoreStore;
import com.monkygames.st.listener.InGameListener;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import java.util.List;

/**
 *
 * @author geekdenz
 */
public class MenuControl extends AbstractAppState implements ScreenController {

    // === class variables === //
    private Nifty nifty;
    private Screen screen;
    private SimpleApplication app;
    private TextRenderer loadingTextRenderer;
    private TextField nameTextField;
    private Score score;
    private boolean inGame = false;
    private ScoreStore scoreStore;
    private IGame game = null;
    private AudioNode lvlMusicNode;
    private AudioNode menuMusicNode;
    /**
     * Contains the progress bar for loading maps.
     */
    private Element mapLoadingProgressBarElement;

    public MenuControl(ScoreStore scoreStore, AudioNode menuMusicNode){
        this.scoreStore = scoreStore;
	this.menuMusicNode = menuMusicNode;
	menuMusicNode.play();
    }

    public void setLvlMusic(AudioNode lvlMusicNode){
    	this.lvlMusicNode = lvlMusicNode;
    }

    // === public methods === //
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        mapLoadingProgressBarElement = nifty.getScreen("loading").findElementByName("mapprogressbar");

    }

    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /** jME3 AppState methods */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        Element loadingElement = nifty.getScreen("loading").findElementByName("loadingText");
        loadingTextRenderer = loadingElement.getRenderer(TextRenderer.class);
        try {
            app.getInputManager().deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT); // removes ESC key
        } catch (IllegalArgumentException iae) {
            // don't do anything
        }
        if (app instanceof IGame) {
            this.game = (IGame) app;
        }
        Screen myScreen = nifty.getScreen("playerInput");
	nameTextField = myScreen.findNiftyControl("playerTextField", TextField.class);
    }

    @Override
    public void update(float tpf) {
    }

    public void play(){
	nifty.gotoScreen("loading");
	game.initMap();
	game.setCursorVisible(false);
    }

    public void unPause() {
	toggleLvlMusic();

	/*
        if (nifty.getCurrentScreen().getScreenId().contains("score")) {
            return;
        }
	*/
        if (score.getTime().getStartGameTime() == 0) {
            resetStartTime();
        }
        //bulletAppState.setSpeed(1.0f);
        app.getStateManager().getState(BulletAppState.class).setSpeed(1.0f);
        //nifty.removeScreen("start");
        nifty.gotoScreen("hud");
	score.getTime().setUnpaused();
        game.setPaused(false);
        if (!inGame) {
            inGame = true;
            initInGameListeners(); // when in the game we want ESC, P, and PAUSE to work to go to the menu
        }
	game.setCursorVisible(false);
    }

    public void pause() {
	toggleMenuMusic();
        if (nifty.getCurrentScreen().getScreenId().contains("pause")) {
            return;
        }
        app.getStateManager().getState(BulletAppState.class).setSpeed(0f);
	score.getTime().setPauseGameTime();
        nifty.gotoScreen("pause");
        game.setPaused(true);
	game.setCursorVisible(true);
    }

    /**
     * Handles exiting the current game.
     **/ 
    public void quitInProgressGame(){
	game.abortGame();
	backToMenu();
    }

    public void quit() {
	if(lvlMusicNode != null){
            lvlMusicNode.stop();
	}
        menuMusicNode.stop();
	//TODO move set end game to another class
	if(score != null){
	    score.getTime().setEndGameTime();
	}
        app.stop();
    }

    public void resetStartTime() {
	score.getTime().setStartGameTime();
    }

    public void setScore(Score score){
        this.score = score;
    }

    public void showScores() {
	toggleMenuMusic();
        nifty.gotoScreen("scores");
        displayScores();
    }
    
    public void backToMenu() {
	toggleMenuMusic();
        nifty.gotoScreen("start");
	game.setCursorVisible(true);
    }
    
    public void restartGame() {
	toggleMenuMusic();
        nifty.gotoScreen("start");
        if (game != null) {
            game.reinit();
        }
    }

    //public void displayRank(Score yourScore, ScoreStore store) {
    public void displayRank() {
	toggleMenuMusic();
        List<Score> scoreList = scoreStore.getList();
        Screen myScreen = nifty.getScreen("scoresRank");
        
        int i = 1;
        for (Score scoreItem : scoreList) {
            if (i > 10) {
                break;
            }
            Element scoreElement = myScreen.findElementByName("rankscore"+ i);
            TextRenderer renderer = scoreElement.getRenderer(TextRenderer.class);
            String scoreText = ""+ scoreItem.getTotal();
            renderer.setText(scoreText);

            Element scoreName = myScreen.findElementByName("rankscorename"+ i);
            renderer = scoreName.getRenderer(TextRenderer.class);
            renderer.setText(scoreItem.getPlayer().getName());
            ++i;
        }
        myScreen.findElementByName("scoreRank")
                .getRenderer(TextRenderer.class)
                .setText("Your Score: "+ score.getTotal());
        nifty.gotoScreen("scoresRank");
    }
    public void displayPlayerInput(){
	toggleMenuMusic();
        Screen myScreen = nifty.getScreen("playerInput");
        Element scoreElement = myScreen.findElementByName("scoreTitle");
    	TextRenderer renderer = scoreElement.getRenderer(TextRenderer.class);
    	renderer.setText("Your Score: "+score.getTotal());
	nifty.gotoScreen("playerInput");
	game.setCursorVisible(true);
    }
    /**
     * From the menu, a player enters their name.
     */
    public void enterPlayerName(){
	/*
	System.out.println("[MC:enterPlayerName ");
        Screen myScreen = nifty.getScreen("playerInput");
	//String name = screen.findControl("playerTextField", TextFieldControl.class).getText();
	System.out.println("[MC:enterPlayerName myScreen "+myScreen);
	String name = screen.findNiftyControl("playerTextField", TextField.class).getText();
	
	System.out.println("[MC:enterPlayerName name = "+name);
	game.saveScore(name);
	System.out.println("[MC:enterPlayerName save game");
	// reset text field
	screen.findNiftyControl("playerTextField", TextField.class).setText("");
	System.out.println("[MC:enterPlayerName display rank");
	displayRank();
     	*/
	String name = nameTextField.getText();
	game.saveScore(name);
	nameTextField.setText("");
	displayRank();
    }

    /**
     * Updates the progress bar with the specified progress.
     * @param mapProgress the percentage that the map is done loading
     * @param description what the system is loading
     */
    public void updateMapProgress(float mapProgress, String description){
	setMapLoadingProgress(mapProgress,description);
    }

    public void instructionPage1(){
        nifty.gotoScreen("instructions_p1");
    }
    public void instructionPage2(){
        nifty.gotoScreen("instructions_p2");
    }
    public void instructionPage3(){
        nifty.gotoScreen("instructions_p3");
    }
    
    // === private methods === //

    private void initInGameListeners() {
        InputManager inputManager = app.getInputManager();
        inputManager.addMapping("Pause", 
                new KeyTrigger(KeyInput.KEY_ESCAPE), 
                new KeyTrigger(KeyInput.KEY_P), 
                new KeyTrigger(KeyInput.KEY_PAUSE));
        InGameListener inGameListener = new InGameListener(game, 
                app.getStateManager().getState(BulletAppState.class), nifty, this);
        inputManager.addListener(inGameListener, "Pause");
    }
    

    private void displayScores() {
	toggleMenuMusic();
        List<Score> scoreList = scoreStore.getList();
        Screen myScreen = nifty.getScreen("scores");
        
        int i = 1;
        for (Score scoreItem : scoreList) {
            if (i > 10) {
                break;
            }

            Element scoreElement = myScreen.findElementByName("score"+ i);
            TextRenderer renderer = scoreElement.getRenderer(TextRenderer.class);
            String scoreText = ""+ scoreItem.getTotal();
            renderer.setText(scoreText);

            Element scoreName = myScreen.findElementByName("scorename"+ i);
            renderer = scoreName.getRenderer(TextRenderer.class);
            renderer.setText(scoreItem.getPlayer().getName());

            ++i;
        }
    }
    
    /**
     * Toggles the lvl music on.
     **/
    private void toggleLvlMusic(){
	menuMusicNode.stop();
	lvlMusicNode.play();
    }
    /**
     * Toggles the menu music on.
     **/
    private void toggleMenuMusic(){
	if(lvlMusicNode != null){
	    lvlMusicNode.stop();
	}
	menuMusicNode.play();
    }
    private void setMapLoadingProgress(final float progress, String loadingText){
	final int MIN_WIDTH = 32;
	int pixelWidth = (int) (MIN_WIDTH + (mapLoadingProgressBarElement.getParent().getWidth() - MIN_WIDTH) * progress);
	mapLoadingProgressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
	mapLoadingProgressBarElement.getParent().layoutElements();
	loadingTextRenderer.setText(loadingText);

    }
}
