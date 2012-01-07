/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.st.actions;

import com.jme3.input.controls.ActionListener;
import com.monkygames.st.objects.Ship;

/**
 * Handles all user digital inputs for a ship like button presses.
 * @version 1.0
 */
public class ShipActionListener extends BaseInput implements ActionListener{

// ============= Class variables ============== //
    public ShipActionListener(Ship ship){
	super(ship);
    }
// ============= Constructors ============== //
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    public void onAction(String name, boolean keyPressed, float tpf) {
	if (name.equals("Pause") && !keyPressed) {
	    togglePaused();
	} 
	// the following should only be checked if the game is not paused
	else if (name.equals("Left") && !isPaused ) {
	    if(keyPressed){
		ship.rotateLeft();
	    } else{
		ship.rotateStop();
	    }
	} else if (name.equals("Right") && !isPaused ) {
	    if(keyPressed){
		ship.rotateRight();
	    } else{
		ship.rotateStop();
	    }
	} else if (name.equals("Thrust") && !isPaused ) {
	    if(keyPressed){
		ship.shipControl.startThrust();
	    } else{
		ship.shipControl.stopThrust();
	    }
	} else if (name.equals("Turbo") && !isPaused ) {
	    if(keyPressed){
		ship.shipControl.startTurbo();
	    } else{
		ship.shipControl.stopTurbo();
	    }
	} else if (name.equals("Attack") && keyPressed && !isPaused) {
	} else if (name.equals("Defend") && keyPressed && !isPaused) {
	} else if (name.equals("Hook") && keyPressed && !isPaused) {
	}
	// the following can be checked at any time (paused or unpaused)
    }
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
