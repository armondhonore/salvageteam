/*
 * Class to give in game action listeners
 */
package com.monkygames.st.listener;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.controls.ActionListener;
import com.monkygames.st.control.MenuControl;
import de.lessvoid.nifty.Nifty;

/**
 *
 * @author geekdenz
 */
public class InGameListener implements ActionListener {
    private BulletAppState bulletAppState;
    private SimpleApplication app;
    private Nifty nifty;
    private MenuControl mc;
    
    public InGameListener(SimpleApplication app, BulletAppState bulletAppState, Nifty nifty, MenuControl mc) {
        this.app = app;
        this.bulletAppState = bulletAppState;
        this.nifty = nifty;
        this.mc = mc;
    }
    public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals("Pause") && !keyPressed) {
            if (bulletAppState.getSpeed() == 0f) {
                mc.unPause();
            } else {
                mc.pause();
            }
        }
    }
}
