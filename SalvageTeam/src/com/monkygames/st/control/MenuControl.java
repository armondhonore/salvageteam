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
import com.monkygames.st.utils.EffectTimer;
import de.lessvoid.nifty.Nifty;
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

    private Nifty nifty;
    private Screen screen;
    private SimpleApplication app;
    private TextRenderer timeElementRenderer;
    private TextRenderer scoreElementRenderer;
    private Score score;
    private boolean inGame = false;
    private ScoreStore scoreStore;
    private IGame game = null;
    private AudioNode lvlMusicNode;
    private AudioNode menuMusicNode;
    /**
     * Used for updating the progress bar for thrust.
     */
    private EffectTimer thrustTimer;
    /**
     * Contains the progress bar.
     */
    private Element thrustProgressBarElement;

    public MenuControl(Score score) {
        this(score, null);
    }
    
    public MenuControl(Score score, ScoreStore scoreStore) {
	this(score,scoreStore,null,null,null);
    }
    public MenuControl(Score score, ScoreStore scoreStore, AudioNode lvlMusicNode, AudioNode menuMusicNode, EffectTimer thrustTimer) {
	this.score = score;
        this.scoreStore = scoreStore;
	this.lvlMusicNode = lvlMusicNode;
	this.menuMusicNode = menuMusicNode;
	this.thrustTimer = thrustTimer;
	menuMusicNode.play();
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        thrustProgressBarElement = nifty.getScreen("hud").findElementByName("progressbar");

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
        Element timeElement = nifty.getScreen("hud").findElementByName("timeLabel");
        timeElementRenderer = timeElement.getRenderer(TextRenderer.class);
        Element scoreElement = nifty.getScreen("hud").findElementByName("scoreLabel");
        scoreElementRenderer = scoreElement.getRenderer(TextRenderer.class);
        try {
            app.getInputManager().deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT); // removes ESC key
        } catch (IllegalArgumentException iae) {
            // don't do anything
        }
        if (app instanceof IGame) {
            this.game = (IGame) app;
        }
    }

    @Override
    public void update(float tpf) {
        updateTime();
	updateScore();
	updateThrustProgressBar();
    }

    public void unPause() {
	toggleLvlMusic();

        if (nifty.getCurrentScreen().getScreenId().contains("score")) {
            return;
        }
        if (score.getTime().getStartGameTime() == 0) {
            resetStartTime();
        }
        //System.out.println("TIME = "+ score.getTime().getStartGameTime());
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
    }

    public void pause() {
	toggleMenuMusic();
        if (nifty.getCurrentScreen().getScreenId().contains("score")) {
            return;
        }
        app.getStateManager().getState(BulletAppState.class).setSpeed(0f);
	score.getTime().setPauseGameTime();
        nifty.gotoScreen("start");
        game.setPaused(true);
    }

    public void quit() {
        lvlMusicNode.stop();
        menuMusicNode.stop();
	//TODO move set end game to another class
	score.getTime().setEndGameTime();
        app.stop();
    }

    public void resetStartTime() {
	score.getTime().setStartGameTime();
    }

    public void setScore(Score score){
        this.score = score;
    }
    
    /**
     * Updates the time in the HUD.
     **/
    private void updateTime() {
        timeElementRenderer.setText("Time: " + score.getTime().updateTime());
    }
    /**
     * Updates the score in the HUD.
     **/
    private void updateScore(){
        scoreElementRenderer.setText("Score: " + score.getTotal());
    }
    /**
     * Updates the thrust progress bar in the HUD.
     */
    private void updateThrustProgressBar(){
    	setThrustProgress(thrustTimer.timeLeft(), "");
    }

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
    
    public void showScores() {
	toggleMenuMusic();
        nifty.gotoScreen("scores");
        displayScores();
    }
    
    public void backToMenu() {
	toggleMenuMusic();
        nifty.gotoScreen("start");
    }
    
    public void restartGame() {
	toggleMenuMusic();
        nifty.gotoScreen("start");
        if (game != null) {
            game.reinit();
        }
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
            String scoreText = ""+ i +".) "+ scoreItem.getTotal();
            renderer.setText(scoreText);
            ++i;
        }
    }
    
    public void displayRank(Score yourScore, ScoreStore store) {
	toggleMenuMusic();
        List<Score> scoreList = store.getList();
        Screen myScreen = nifty.getScreen("scoresRank");
        
        int i = 1;
        for (Score scoreItem : scoreList) {
            if (i > 10) {
                break;
            }
            Element scoreElement = myScreen.findElementByName("rankscore"+ i);
            TextRenderer renderer = scoreElement.getRenderer(TextRenderer.class);
            String scoreText = ""+ i +".) "+ scoreItem.getTotal();
            renderer.setText(scoreText);
            ++i;
        }
        myScreen.findElementByName("scoreRank")
                .getRenderer(TextRenderer.class)
                .setText("Your Score: "+ yourScore.getTotal());
        nifty.gotoScreen("scoresRank");
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
	lvlMusicNode.stop();
	menuMusicNode.play();
    }
    private void setThrustProgress(final float progress, String loadingText) {
	final int MIN_WIDTH = 32;
	int pixelWidth = (int) (MIN_WIDTH + (thrustProgressBarElement.getParent().getWidth() - MIN_WIDTH) * progress);
	thrustProgressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
	thrustProgressBarElement.getParent().layoutElements();
    }
}
