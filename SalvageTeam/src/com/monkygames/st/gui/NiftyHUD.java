/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.gui;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.net.URL;

/**
 * Used for displaying the in-game HUD.
 * @version 1.0
 */
public class NiftyHUD implements ScreenController{


// ============= Class variables ============== //
/**
 * Handles the GUI.
 **/
Nifty nifty;

// ============= Constructors ============== //
public NiftyHUD(AssetManager assetManager, InputManager inputManager, AudioRenderer audioRenderer, ViewPort guiViewPort){
    NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
    nifty = niftyDisplay.getNifty();

    nifty.fromXml("Interface/NiftyHUD.xml", "start", this);

    // attach the nifty display to the gui view port as a processor
    guiViewPort.addProcessor(niftyDisplay);
}
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    public void onStartScreen() {
	System.out.println("On Screen Start");
    }

    public void onEndScreen() {
	System.out.println("On Screen End");
    }
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
