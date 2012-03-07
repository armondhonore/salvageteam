/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monkygames.st.control;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.monkygames.st.game.IGame;
import com.monkygames.st.game.Score;
import com.monkygames.st.utils.EffectTimer;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;

/**
 * Controls the HUD components.
 */
public class HudControl extends AbstractAppState implements ScreenController {

    // === class variables === //
    private Nifty nifty;
    private Screen screen;
    private SimpleApplication app;
    private TextRenderer timeElementRenderer;
    private TextRenderer scoreElementRenderer;
    private Score score;
    private boolean inGame = false;
    private IGame game = null;
    private boolean isInitialized = false;
    /**
     * Used for updating the progress bar for thrust.
     */
    private EffectTimer thrustTimer;
    /**
     * Contains the progress bar.
     */
    private Element thrustProgressBarElement;

    public HudControl(Score score){
	this.score = score;
    }

    public void setThrustTimer(EffectTimer thrustTimer){
	this.thrustTimer = thrustTimer;
    }

    // === public methods === //
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        thrustProgressBarElement = nifty.getScreen("hud").findElementByName("progressbar");
    }
    public void setNifty(Nifty nifty){
    	this.nifty = nifty;
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
        if (app instanceof IGame) {
            this.game = (IGame) app;
        }
	isInitialized = true;
    }

    @Override
    public void update(float tpf) {
	if(isInitialized){
            updateTime();
	    updateScore();
	    updateThrustProgressBar();
	}
    }

    public void setScore(Score score){
        this.score = score;
    }

    // === private methods === //
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
    
    private void setThrustProgress(final float progress, String loadingText) {
	final int MIN_WIDTH = 32;
	int pixelWidth = (int) (MIN_WIDTH + (thrustProgressBarElement.getParent().getWidth() - MIN_WIDTH) * progress);
	thrustProgressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
	thrustProgressBarElement.getParent().layoutElements();
    }
}