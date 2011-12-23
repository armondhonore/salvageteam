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
import com.monkygames.st.game.Score;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

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

    public MenuControl(Score score) {
	this.score = score;
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
    }

    @Override
    public void update(float tpf) {
        updateTime();
	updateScore();
    }

    public void unPause() {
        //bulletAppState.setSpeed(1.0f);
        app.getStateManager().getState(BulletAppState.class).setSpeed(1.0f);
        if (score.getTime().getStartGameTime() == 0) {
            resetStartTime();
        }
        nifty.removeScreen("start");
        nifty.gotoScreen("hud");
	score.getTime().setUnpaused();
    }

    public void pause() {
        app.getStateManager().getState(BulletAppState.class).setSpeed(0f);
	score.getTime().setPauseGameTime();
    }

    public void quit() {
	//TODO move set end game to another class
	score.getTime().setEndGameTime();
        app.stop();
    }

    public void resetStartTime() {
	score.getTime().setStartGameTime();
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
    
}
