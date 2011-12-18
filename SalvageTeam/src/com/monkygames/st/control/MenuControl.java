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
    private long startTime = 0;
    private long pauseTime = 0;
    private TextRenderer timeElementRenderer;

    public MenuControl() {
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
    }

    @Override
    public void update(float tpf) {
        updateTime();
    }

    public void unPause() {
        //bulletAppState.setSpeed(1.0f);
        app.getStateManager().getState(BulletAppState.class).setSpeed(1.0f);
        if (startTime == 0) {
            resetStartTime();
        }
        nifty.removeScreen("start");
        nifty.gotoScreen("hud");
        long currentTime = System.currentTimeMillis();
        long timeToSubtract;
        if (pauseTime > 0) {
            timeToSubtract = currentTime - pauseTime;
        } else {
            timeToSubtract = 0;
        }
        this.startTime -= timeToSubtract;
    }

    public void pause() {
        app.getStateManager().getState(BulletAppState.class).setSpeed(0f);
        pauseTime = System.currentTimeMillis();
    }

    public void quit() {
        app.stop();
    }

    public void resetStartTime() {
        startTime = System.currentTimeMillis();
    }

    private void updateTime() {
        long time = System.currentTimeMillis() - startTime;
        String timeStr = "" + time;
        int ind = timeStr.length() - 3;
        if (ind >= 0) {
            timeStr = timeStr.substring(0, ind) + "." + timeStr.substring(ind);
        }
        timeElementRenderer.setText("Time: " + timeStr);
    }
    
}