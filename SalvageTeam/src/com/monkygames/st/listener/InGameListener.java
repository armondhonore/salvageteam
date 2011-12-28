/*
 * Class to give in game action listeners
 */
package com.monkygames.st.listener;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.controls.ActionListener;
import com.monkygames.st.control.MenuControl;
import com.monkygames.st.game.IGame;
import de.lessvoid.nifty.Nifty;

/**
 *
 * @author geekdenz
 */
public class InGameListener implements ActionListener {
    private BulletAppState bulletAppState;
    private IGame game;
    private Nifty nifty;
    private MenuControl mc;
    
    public InGameListener(IGame game, BulletAppState bulletAppState, Nifty nifty, MenuControl mc) {
        this.game = game;
        this.bulletAppState = bulletAppState;
        this.nifty = nifty;
        this.mc = mc;
    }
    public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals("Pause") && !keyPressed) {
            if (game.getPaused()) {
                mc.unPause();
            } else {
                mc.pause();
            }
        }
    }
}
