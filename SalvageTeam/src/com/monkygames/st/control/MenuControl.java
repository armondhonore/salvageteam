/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkygames.st.control;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.monkygames.st.game.IGame;
import com.monkygames.st.game.Score;
import com.monkygames.st.io.ScoreStore;
import com.monkygames.st.listener.InGameListener;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
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

    public MenuControl(Score score) {
        this(score, null);
    }
    
    public MenuControl(Score score, ScoreStore scoreStore) {
	this.score = score;
        this.scoreStore = scoreStore;
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
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
    }

    public void unPause() {
        if (score.getTime().getStartGameTime() == 0) {
            resetStartTime();
        }
        if (nifty.getCurrentScreen().getScreenId().contains("score")) {
            return;
        }
        //bulletAppState.setSpeed(1.0f);
        app.getStateManager().getState(BulletAppState.class).setSpeed(1.0f);
        //nifty.removeScreen("start");
        nifty.gotoScreen("hud");
	score.getTime().setUnpaused();
        if (!inGame) {
            inGame = true;
            initInGameListeners(); // when in the game we want ESC, P, and PAUSE to work to go to the menu
        }
    }

    public void pause() {
        if (nifty.getCurrentScreen().getScreenId().contains("score")) {
            return;
        }
        app.getStateManager().getState(BulletAppState.class).setSpeed(0f);
	score.getTime().setPauseGameTime();
        nifty.gotoScreen("start");
    }

    public void quit() {
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

    private void initInGameListeners() {
        InputManager inputManager = app.getInputManager();
        inputManager.addMapping("Pause", 
                new KeyTrigger(KeyInput.KEY_ESCAPE), 
                new KeyTrigger(KeyInput.KEY_P), 
                new KeyTrigger(KeyInput.KEY_PAUSE));
        InGameListener inGameListener = new InGameListener(app, 
                app.getStateManager().getState(BulletAppState.class), nifty, this);
        inputManager.addListener(inGameListener, "Pause");
    }
    
    public void showScores() {
        nifty.gotoScreen("scores");
        displayScores();
    }
    
    public void backToMenu() {
        nifty.gotoScreen("start");
    }
    
    public void restartGame() {
        nifty.gotoScreen("start");
        if (game != null) {
            game.reinit();
        }
    }

    private void displayScores() {
        List<Score> scoreList = scoreStore.getList();
        Screen myScreen = nifty.getScreen("scores");
        
        int i = 1;
        for (Score scoreItem : scoreList) {
            Element scoreElement = myScreen.findElementByName("score"+ i);
            TextRenderer renderer = scoreElement.getRenderer(TextRenderer.class);
            String scoreText = ""+ i +".) "+ scoreItem.getTotal();
            renderer.setText(scoreText);
            ++i;
        }
    }
    
    public void displayRank(Score yourScore) {
        List<Score> scoreList = scoreStore.getList();
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
}
