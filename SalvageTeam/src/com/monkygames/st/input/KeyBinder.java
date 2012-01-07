/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.input;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.monkygames.st.actions.ShipActionListener;
import com.monkygames.st.objects.Ship;

/**
 * Binds keys to actions for player input.
 * @version 1.0
 */
public class KeyBinder{

// ============= Class variables ============== //
    /**
     * Handles the physical keypresses.
     **/
    private InputManager inputManager;
    /**
     * Handles digital actions.
     **/
    private ShipActionListener shipActionListener;
    /**
     * Actions for button presses.
     **/
    private String[] actionKeys = new String[]{
						/*"Pause",*/
						"Left",
						"Right",
						"Thrust",
						"Turbo",
						"Attack",
						"Defend",
						"Hook"
					      };
// ============= Constructors ============== //
    /**
     * Creates a new key binder.
     * @param inputManager handles the physical keypresses.
     * @param ship the ship to apply any input from the user.
     **/
    public KeyBinder (InputManager inputManager, Ship ship){
	this.inputManager = inputManager;
	shipActionListener = new ShipActionListener (ship);

	bindKeys();
	inputManager.addListener(shipActionListener, actionKeys);
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Binds the keys to the actions.
     **/
    private void bindKeys () {
	 // You can map one or several inputs to one named action
    //inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
    inputManager.addMapping("Attack",  new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addMapping("Defend",  new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Hook",  new KeyTrigger(KeyInput.KEY_F));
    inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_LEFT));
    inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_RIGHT));
    inputManager.addMapping("Thrust",  new KeyTrigger(KeyInput.KEY_UP));
    inputManager.addMapping("Turbo",  new KeyTrigger(KeyInput.KEY_LSHIFT));
    //inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
                                      //new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    }
// ============= Implemented Methods ============== //
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
